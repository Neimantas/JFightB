package main.Services.Impl;


import main.Services.IDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase implements IDataBase {

    String connectionUrl_LOCAL = "jdbc:sqlserver://localhost;" +
            "databaseName=FightDBB;user=sa;password=P@55w0rd";

    // Create a variable for the connection string.
    String connectionUrl = "jdbc:sqlserver://192.168.1.101\\SQLEXPRESS;" +
            "databaseName=FightDBB;user=sa;password=Qwerty11";

    @Override
    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(connectionUrl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void closeConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
