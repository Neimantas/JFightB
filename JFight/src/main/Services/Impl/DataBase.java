package main.Services.Impl;

import main.Services.IDataBase;

import java.sql.*;

public class DataBase implements IDataBase {

    // Create a variable for the connection string.
    String connectionUrl = "jdbc:sqlserver://WIN-NA9LPC408MM\\SQLEXPRESS;" +
            "databaseName=FightDBB;user=sa;password=Qwerty11";

    // Declare the JDBC objects.
    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
            return connection;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void closeConnection() throws SQLException {

        if (connection != null) {
            connection.close();
        }
    }
}
