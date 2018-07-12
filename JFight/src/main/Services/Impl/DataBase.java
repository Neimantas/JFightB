package Services.Impl;

import Services.IDataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase implements IDataBase {

    // Create a variable for the connection string.
    String connectionUrl = "jdbc:sqlserver://192.168.1.101\\SQLEXPRESS;" +
            "databaseName=FightDBB;user=sa;password=Qwerty11";

    // Declare the JDBC objects.
    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;

    @Override
    public Connection getConnection() {
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
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
