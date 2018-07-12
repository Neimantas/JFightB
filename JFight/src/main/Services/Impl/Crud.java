package Services.Impl;

import Models.DTO.DBqueryDTO;
import Services.ICrud;
import Services.IDataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Crud implements ICrud {

    IDataBase dataBase = new DataBase();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @Override
    public DBqueryDTO create(String query) {
        DBqueryDTO dBqueryDTO;
        try {
            connection = dataBase.getConnection();
            statement = connection.createStatement();
            statement.executeQuery(query);
            dBqueryDTO = new DBqueryDTO(true, null, null);
            statement.close();
            return dBqueryDTO;
        } catch (Exception e) {
            e.printStackTrace();
            dBqueryDTO = new DBqueryDTO(false, e.getMessage(), null);
            return dBqueryDTO;
        } finally {
            dataBase.closeConnection();
        }
    }

    @Override
    public DBqueryDTO read(String query) {
        DBqueryDTO dBqueryDTO;
        try {
            connection = dataBase.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            int cols = resultSet.getMetaData().getColumnCount();
            List<List<Object>> rows = new ArrayList<>();
            List<Object> columns;
            while (resultSet.next()) {
                columns = new ArrayList<>();
                for (int i = 1; i <= cols; i++) {
                    columns.add(resultSet.getObject(i));
                }
                rows.add(columns);
            }
            statement.close();
            dBqueryDTO = new DBqueryDTO(true, null, rows);
            return dBqueryDTO;
        } catch (Exception e) {
            e.printStackTrace();
            dBqueryDTO = new DBqueryDTO(false, e.getMessage(), null);
            return dBqueryDTO;
        } finally {
            dataBase.closeConnection();
        }
    }


    @Override
    public DBqueryDTO update(String query) {
        DBqueryDTO dBqueryDTO;
        try {
            connection = dataBase.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.commit();
            statement.close();
            dBqueryDTO = new DBqueryDTO(true, null, null);
            return dBqueryDTO;
        } catch (Exception e) {
            e.printStackTrace();
            dBqueryDTO = new DBqueryDTO(false, e.getMessage(), null);
            return dBqueryDTO;
        } finally {
            dataBase.closeConnection();
        }
    }

    @Override
    public DBqueryDTO delete(String query) {
        DBqueryDTO dBqueryDTO;
        try {
            connection = dataBase.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.commit();
            statement.close();
            dBqueryDTO = new DBqueryDTO(true, null, null);
            return dBqueryDTO;
        } catch (Exception e) {
            e.printStackTrace();
            dBqueryDTO = new DBqueryDTO(false, null, null);
            return dBqueryDTO;
        } finally {
            dataBase.closeConnection();
        }
    }

}


