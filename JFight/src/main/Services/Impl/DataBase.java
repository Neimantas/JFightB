package main.Services.Impl;


import main.Services.IDataBase;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase implements IDataBase {

    String connectionUrl_LOCAL = "jdbc:sqlserver://localhost;" +
            "databaseName=FightDBB;user=sa;password=P@55w0rd";

    String connectionUrl_LOCAL2 = "jdbc:sqlserver://localhost:52753;" +
            "databaseName=FightDBB;user=test;password=Asdasd123";

    // Create a variable for the connection string.
    String connectionUrl = "jdbc:sqlserver://192.168.1.101\\SQLEXPRESS;" +
            "databaseName=FightDBB;user=sa;password=Qwerty11";

    @Override
    public Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(connectionUrl_LOCAL2);
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
