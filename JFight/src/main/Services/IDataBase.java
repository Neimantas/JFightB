package Services;

import java.sql.Connection;

public interface IDataBase {

    Connection getConnection();

    void closeConnection();
}
