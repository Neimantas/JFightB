package main.Models.BL;

import java.sql.Connection;

public class UpdatePreparedStmtModel {
    public Connection connection;
    public Object objectWithUpdatedValues;
    public String tableName;
    public String[] primaryKeys;
}
