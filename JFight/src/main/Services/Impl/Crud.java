package Services.Impl;

import Models.DTO.DBqueryDTO;
import Services.ICrud;
import Services.IDataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        return null;
    }

    @Override
    public DBqueryDTO read(String query) {
        DBqueryDTO dBqueryDTO = new DBqueryDTO();
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
            dataBase.closeConnection();
            dBqueryDTO.setList(rows);
            dBqueryDTO.isSuccess();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                dataBase.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return dBqueryDTO;
        }
    }


        @Override
        public DBqueryDTO update (String query){
            return null;
        }

        @Override
        public DBqueryDTO delete (String query){
            return null;
        }
    }


