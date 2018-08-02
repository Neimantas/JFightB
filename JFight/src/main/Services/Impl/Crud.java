package main.Services.Impl;

import main.Models.BL.DBQueryModel;
import main.Models.DTO.DBqueryDTO;
import main.Services.ICrud;
import main.Services.IDataBase;
import main.Services.Helpers.QueryBuilder;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Crud implements ICrud {

    private IDataBase dataBase = new DataBase();
    private Connection connection;
    private Statement statement;

    @Override
    public DBqueryDTO create(Object object) {
        try {
            connection = dataBase.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(createInsertQuery(object));
            statement.close();
            return new DBqueryDTO(true, "", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new DBqueryDTO(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection();
        }
    }

    @Override
    public <T> DBqueryDTO<T> read(DBQueryModel dbQueryModel, Class<T> dalType) {
        try {
            connection = dataBase.getConnection();
            statement = connection.createStatement();
            // TODO make a check for a procedure != null
            ResultSet rs = statement
                            .executeQuery(new QueryBuilder(getClassNameWithoutDAL(dalType))
                            .buildQuery(dbQueryModel, "read")
                            .getQuery());
            List<T> rows = new ArrayList<>();
            while (rs.next()) {
                T dal = dalType.newInstance();
                loadResultSetIntoObject(rs, dal);
                rows.add(dal);
            }
            statement.close();
            return new DBqueryDTO<>(true, "", rows);
        } catch (Exception e) {
            e.printStackTrace();
            return new DBqueryDTO(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection();
        }
    }


    @Override
    public DBqueryDTO update(Object dal, String primaryKey) {
        try {
            connection = dataBase.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement stmt = createUpdatePreparedStatement(dal,
                    getClassNameWithoutDAL(dal.getClass()), primaryKey);
            stmt.executeUpdate();
            stmt.close();
            return new DBqueryDTO(true, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new DBqueryDTO(false, e.getMessage(), null);
        } finally {
            dataBase.closeConnection();
        }
    }

    @Override
    public DBqueryDTO delete(DBQueryModel deleteModel, Class dal) {
        try {
            connection = dataBase.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(new QueryBuilder(getClassNameWithoutDAL(dal))
                    .buildQuery(deleteModel, "delete")
                    .getQuery());
            connection.commit();
            statement.close();
            return new DBqueryDTO(true, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new DBqueryDTO(false, null, null);
        } finally {
            dataBase.closeConnection();
        }
    }


    private void loadResultSetIntoObject(ResultSet rst, Object object)
            throws IllegalArgumentException, IllegalAccessException, SQLException {
        Class<?> zclass = object.getClass();
        for(Field field : zclass.getDeclaredFields()) {
            String name = field.getName();
            field.setAccessible(true);
            Object value = rst.getObject(name);
            Class<?> type = field.getType();
            if (isPrimitive(type)) {
                Class<?> boxed = boxPrimitiveClass(type);

                if (type == boolean.class) {
                    value = (int) value == 1;
                } else if (type == long.class){
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
        for(int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            sb.append(quoteIdentifier(fields[i].get(object).toString()));

            if (i != fields.length - 1) {
                sb.append(",");
            } else {
                sb.append(")");
            }

        }
        return sb.toString();
    }

    private PreparedStatement createUpdatePreparedStatement(Object object,
                                                            String tableName, String primaryKey) {
        PreparedStatement stmt;
        try {
            Class<?> zclass = object.getClass();
            String Sql = createUpdateQuery(zclass, tableName, primaryKey);
            stmt = connection.prepareStatement(Sql);
            Field[] fields = zclass.getDeclaredFields();
            int pkSequence = fields.length;
            for(int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);
                Object value = field.get(object);
                String name = field.getName();
                if(name.equals(primaryKey)) {
                    stmt.setObject(pkSequence, value);
                } else {
                    stmt.setObject(i, value);
                }
            }
        }
        catch(Exception e){
            throw new RuntimeException("Unable to create PreparedStatement: " + e.getMessage(), e);
        }
        return stmt;
    }

    private String createUpdateQuery(Class<?> zclass, String tableName, String primaryKey) {
        StringBuilder sets = new StringBuilder();
        String where = null;
        for (Field field : zclass.getDeclaredFields()) {
            String name = field.getName();
            String pair = quoteIdentifier(name) + " = ?";
            if (name.equals(primaryKey)) {
                where = " WHERE " + pair;
            } else {
                if (sets.length()>1) {
                    sets.append(", ");
                }
                sets.append(pair);
            }
        }
        if (where == null) {
            throw new IllegalArgumentException("Primary key not found in '" + zclass.getName() + "'");
        }
        return "UPDATE " + tableName + " SET " + sets.toString() + where;
    }

    private String quoteIdentifier(String value) {
        return "'" + value + "'";
    }

    private boolean isPrimitive(Class<?> type)
    {
        return (type == int.class || type == long.class ||
                type == double.class  || type == float.class
                || type == boolean.class || type == byte.class
                || type == char.class || type == short.class);
    }

    private Class<?> boxPrimitiveClass(Class<?> type)
    {
        if (type == int.class){return Integer.class;}
        else if (type == long.class){return Long.class;}
        else if (type == double.class){return Double.class;}
        else if (type == float.class){return Float.class;}
        else if (type == boolean.class){return Boolean.class;}
        else if (type == byte.class){return Byte.class;}
        else if (type == char.class){return Character.class;}
        else if (type == short.class){return Short.class;}
        else
        {
            throw new IllegalArgumentException("Class '" + type.getName() + "' is not a primitive");
        }
    }

    private String getClassNameWithoutDAL(Class c) {
        if (c.getSimpleName().equals("UserDAL")) {
            return "[User]";
        }
        return c.getSimpleName().replace("DAL", "");
    }
}


