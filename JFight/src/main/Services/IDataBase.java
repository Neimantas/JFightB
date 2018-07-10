package Services;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDataBase {
    Connection getConnection() throws SQLException;

    void closeConnection() throws SQLException;
}
