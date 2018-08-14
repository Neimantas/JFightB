package main.Services.Impl;

import javafx.util.Pair;
import main.Models.BL.DBQueryModel;
import main.Models.BL.ProcedureModel;
import main.Models.BL.UpdateModel;
import main.Models.BL.UpdatePreparedStmtModel;
import main.Models.DAL.UserExtendedDAL;
import main.Models.DTO.DBqueryDTO;
import main.Services.Helpers.Logger;
import main.Services.Helpers.QueryBuilder;
import main.Services.ICrud;
import main.Services.IDataBase;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Crud implements ICrud {

    @Override
    public DBqueryDTO create(Object object) {
        IDataBase dataBase = new DataBase();
        Connection connection = dataBase.getConnection();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(createInsertQuery(object));

            // Exception for inserting Images (byteArray) into DB from 'UserExtendedDAL'
            if (object.getClass().getSimpleName().equals("UserExtendedDAL")) {
                UserExtendedDAL userExtendedDAL = (UserExtendedDAL) object;
                preparedStatement.setBytes(1, userExtendedDAL.profileImg);
            }

            int rowsCreated = preparedStatement.executeUpdate();
            preparedStatement.close();
            return new DBqueryDTO<>(true, "Rows created -> " + rowsCreated, null);
        } catch (Exception e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
            return new DBqueryDTO<>(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection(connection);
        }
    }

    @Override
    public <T> DBqueryDTO<T> read(DBQueryModel dbQueryModel, Class<T> dalType) {
        IDataBase dataBase = new DataBase();
        Connection connection = dataBase.getConnection();
        try {
            List<T> rows = getDALList(connection, dbQueryModel, dalType);
            return new DBqueryDTO<>(true, "Rows selected from DB -> " + rows.size(), rows);
        } catch (Exception e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
            return new DBqueryDTO<>(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection(connection);
        }
    }

    @Override
    public DBqueryDTO update(Object dal, String[] primaryKey) {
        IDataBase dataBase = new DataBase();
        Connection connection = dataBase.getConnection();
        try {
            UpdatePreparedStmtModel preparedStmtModel = new UpdatePreparedStmtModel();
            preparedStmtModel.connection = connection;
            preparedStmtModel.objectWithUpdatedValues = dal;
            preparedStmtModel.primaryKeys = primaryKey;
            preparedStmtModel.tableName = getClassNameWithoutDAL(dal.getClass());

            PreparedStatement preparedStatement = createPreparedStatementForUpdate(preparedStmtModel);
            int rowsUpdated = preparedStatement.executeUpdate();
            preparedStatement.close();
            return new DBqueryDTO<>(true, "Rows updated -> " + rowsUpdated, null);
        } catch (Exception e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
            return new DBqueryDTO<>(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection(connection);
        }
    }

    @Override
    public DBqueryDTO delete(DBQueryModel deleteModel, Class dal) {
        IDataBase dataBase = new DataBase();
        Connection connection = dataBase.getConnection();
        try {
            Statement statement = connection.createStatement();
            int rowsDeleted = statement.executeUpdate(new QueryBuilder(getClassNameWithoutDAL(dal))
                                        .buildQuery(deleteModel, "delete")
                                        .getQuery());
            statement.close();
            return new DBqueryDTO<>(true, "Rows deleted -> " + rowsDeleted, null);
        } catch (Exception e) {
            Logger.error(e.getMessage());
            e.printStackTrace();
            return new DBqueryDTO<>(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection(connection);
        }
    }

    private <T> List<T> getDALList(Connection connection, DBQueryModel queryModel, Class<T> dalType) throws Exception {
        ResultSet rs;
        CallableStatement callableStatement;
        Statement statement;
        List<T> rows;

        if (queryModel.procedure != null) {
            callableStatement = getCallableStatementForGivenProcedure(connection, queryModel.procedure);
            callableStatement.execute();
            rs = callableStatement.getResultSet();
            rows = getDALListFromResultSet(rs, dalType);
            callableStatement.close();
        } else {
            statement = connection.createStatement();
            rs = statement
                    .executeQuery(new QueryBuilder(getClassNameWithoutDAL(dalType))
                            .buildQuery(queryModel, "read")
                            .getQuery());
            rows = getDALListFromResultSet(rs, dalType);
            statement.close();
        }

        return rows;
    }

    private <T> List<T> getDALListFromResultSet(ResultSet rs, Class<T> dalType) throws Exception {
        List<T> rows = new ArrayList<>();
        while (rs.next()) {
            T dal = dalType.newInstance();
            loadResultSetIntoObject(rs, dal);
            rows.add(dal);
        }
        rs.close();
        return rows;
    }

    private CallableStatement getCallableStatementForGivenProcedure(Connection connection, ProcedureModel procedure) throws Exception {
        CallableStatement callableStatement = connection.prepareCall(procedure.name);
        List<Pair<String, Object>> params = procedure.params;
        for (Pair<String, Object> pair : params) {
            switch (pair.getValue().getClass().getSimpleName()) {
                case "Integer":
                    callableStatement.setInt(pair.getKey(), (int) pair.getValue());
                    break;
                case "String":
                    callableStatement.setString(pair.getKey(), (String) pair.getValue());
                    break;
                default:
                    throw new IllegalArgumentException("'getCallableStatementForGivenProcedure' method can only handle " +
                            "'Integer' and 'String' types for procedure arguments.");
            }
        }
        return callableStatement;
    }


    private void loadResultSetIntoObject(ResultSet rst, Object object)
            throws IllegalArgumentException, IllegalAccessException, SQLException {
        Class<?> zclass = object.getClass();
        for (Field field : zclass.getDeclaredFields()) {
            String name = field.getName();
            field.setAccessible(true);
            Object value = rst.getObject(name);
            Class<?> type = field.getType();
            if (isPrimitive(type)) {
                Class<?> boxed = boxPrimitiveClass(type);

                if (type == boolean.class) {
                    value = (int) value == 1;
                } else if (type == long.class) {
                    value = Long.parseLong(value.toString());
                } else {
                    value = boxed.cast(value);
                }

            }
            field.set(object, value);
        }
    }

    private String createInsertQuery(Object object)
            throws IllegalArgumentException, IllegalAccessException {
        Class<?> zclass = object.getClass();
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ")
                .append(getClassNameWithoutDAL(zclass))
                .append(" VALUES (");
        Field[] fields = zclass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);

            if (fields[i].getName().equals("userId") && object.getClass().getSimpleName().equals("UserDAL")) {
                continue;
            } else if (fields[i].getName().equals("profileImg")) {
                sb.append("?");
            } else if (fields[i].get(object) == null) {
                sb.append("null");
            } else {
                sb.append(quoteIdentifier(fields[i].get(object).toString()));
            }

            if (i != fields.length - 1) {
                sb.append(",");
            } else {
                sb.append(")");
            }

        }
        return sb.toString();
    }

    private PreparedStatement createPreparedStatementForUpdate(UpdatePreparedStmtModel preparedStmtModel) {
        try {
            Class<?> zclass = preparedStmtModel.objectWithUpdatedValues.getClass();
            UpdateModel updateModel = createUpdateQuery(zclass, preparedStmtModel.tableName, preparedStmtModel.primaryKeys);
            PreparedStatement stmt = preparedStmtModel.connection.prepareStatement(updateModel.query);
            int fieldPosition = 1;
            for (Field field : zclass.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(preparedStmtModel.objectWithUpdatedValues);
                String name = field.getName();

                if (checkForPrimaryKey(name, preparedStmtModel.primaryKeys)) {
                    int pkPosition = updateModel.pkLocation.get(updateModel.primaryKeys.indexOf(name));
                    stmt.setObject(pkPosition, value);
                } else {
                    stmt.setObject(fieldPosition, value);
                    fieldPosition++;
                }

            }
            return stmt;
        } catch (Exception e) {
            throw new RuntimeException("Unable to create PreparedStatement: " + e.getMessage(), e);
        }
    }

    private UpdateModel createUpdateQuery(Class<?> zclass, String tableName, String[] primaryKey) {
        StringBuilder sets = new StringBuilder();
        UpdateModel updateModel = new UpdateModel();
        updateModel.pkLocation = new ArrayList<>();
        updateModel.primaryKeys = new ArrayList<>();

        String where = null;
        Field[] fields = zclass.getDeclaredFields();
        int pkPosition = fields.length - primaryKey.length + 1;
        for (Field field : fields) {
            String name = field.getName();
            String pair = name + " = ?";
            if (checkForPrimaryKey(name, primaryKey)) {

                if (updateModel.primaryKeys.isEmpty()) {
                    where = " WHERE " + pair;
                } else {
                    where += " AND " + pair;
                }

                updateModel.pkLocation.add(pkPosition);
                updateModel.primaryKeys.add(name);
                pkPosition++;

            } else {

                if (sets.length() > 1) {
                    sets.append(", ");
                }
                sets.append(pair);

            }
        }

        if (where == null) {
            throw new IllegalArgumentException("Primary key not found in '" + zclass.getName() + "'");
        }

        updateModel.query = "UPDATE " + tableName + " SET " + sets.toString() + where;
        return updateModel;
    }

    private boolean checkForPrimaryKey(String fieldName, String[] primaryKeyArr){
        for (String pk : primaryKeyArr) {
            if (pk.equals(fieldName)) return true;
        }
        return false;
    }

    private String quoteIdentifier(String value) {
        return "'" + value + "'";
    }

    private boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class ||
                type == double.class || type == float.class
                || type == boolean.class || type == byte.class
                || type == char.class || type == short.class);
    }

    private Class<?> boxPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else {
            throw new IllegalArgumentException("Class '" + type.getName() + "' is not a primitive");
        }
    }

    private String getClassNameWithoutDAL(Class c) {
        // Exception: 'User' is a keyword in SQL because of that we must use []
        if (c.getSimpleName().equals("UserDAL")) {
            return "[User]";
        }
        return c.getSimpleName().replace("DAL", "");
    }
}


