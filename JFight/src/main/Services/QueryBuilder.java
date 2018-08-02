package main.Services;

import main.Models.BL.DBQueryModel;
import main.Models.BL.ProcedureModel;

public class QueryBuilder {
    // TODO fix this part -> merge QueryBuilder with DbQueryModel -> both ar not needed
    public String[] where;
    public String[][] whereValue;
    public ProcedureModel procedure;
    public String tableName;

    private StringBuilder sb = new StringBuilder();


    public QueryBuilder(String tn) {
        tableName = tn;
    }

    public QueryBuilder buildQuery(DBQueryModel queryModel, String readOrDelete) {
        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalArgumentException("Missing table name!");
        }

        if (readOrDelete == null || readOrDelete.isEmpty()) {
            throw new IllegalArgumentException("Cannot build a query without specifying Read or Delete operation.");
        }

        sb.setLength(0);

        switch (readOrDelete.toUpperCase()) {
            case "READ":
                sb.append("SELECT * FROM ").append(tableName).append(" WHERE 1 = 1");
                break;
            case "DELETE":
                sb.append("DELETE FROM ").append(tableName).append(" WHERE 1 = 1");
                break;
            default:
                throw new IllegalArgumentException("Please specify either Read or Delete as third argument.");
        }

        if (queryModel.where != null && queryModel.whereValue != null) {
            return whereClause(queryModel);
        }

        return this;
    }

    private QueryBuilder whereClause(DBQueryModel queryModel) {
        String[][] values = queryModel.whereValue;
        String[] where = queryModel.where;

        for (int i = 0; i < where.length; i++) {
            sb.append(" AND ").append(where[i]).append(" IN (");

            for (int j = 0; j < values[i].length; j++) {
                sb.append("'").append(values[i][j]).append("'");
                // Check if it's the last value if it is omit the ','
                if (j != values[i].length - 1) {
                    sb.append(",");
                }
            }
            sb.append(")");
        }

        return this;
    }

    public String getQuery(){
        return sb.toString();
    }

}

