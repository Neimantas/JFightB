package main.Services.Impl;

import main.Services.IDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase implements IDataBase {
    private Connection connection;


    @Override
    public Connection getConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:sqlserver://192.168.1.101/SQLEXPRESS/FightDBB", "sa", "Qwerty11");
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void closeConnection() throws SQLException {

        if (connection != null) {
            connection.close();
        }

    }
}
