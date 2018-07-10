package Services.Impl;

import Services.IDataBase;


import java.sql.*;

public class DataBase implements IDataBase {

    // Create a variable for the connection string.
    String connectionUrl = "jdbc:sqlserver://192.168.1.101\\SQLEXPRESS;" +
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
//        finally {
//            if (rs != null) try { rs.close(); } catch(Exception e) {}
//            if (stmt != null) try { stmt.close(); } catch(Exception e) {}
//            if (connection != null) try { connection.close(); } catch(Exception e) {}
//        }
    }

    @Override
    public void closeConnection() throws SQLException {

        if (connection != null) {
            connection.close();
        }
    }
}
