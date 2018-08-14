package main.Services.Helpers;

import main.Models.BL.DBQueryModel;
import main.Models.CONS.QueryBuilderErrors;

public final class QueryBuilder {

    private QueryBuilder(){}

    public static String buildQuery(String tableName, DBQueryModel queryModel, String readOrDelete) {
    	
        if (tableName == null || tableName.isEmpty()) {
            throw new IllegalArgumentException(QueryBuilderErrors.MISSING_TABLE_NAME);
        }

        if (readOrDelete == null || readOrDelete.isEmpty()) {
            throw new IllegalArgumentException(QueryBuilderErrors.MISSING_READ_DELETE_PARAM);
        }

        String query;

        switch (readOrDelete.toUpperCase()) {
            case "READ":
                query = "SELECT * FROM " + tableName + " WHERE 1 = 1";
                break;
            case "DELETE":
                query = "DELETE FROM " + tableName + " WHERE 1 = 1";
                break;
            default:
                throw new IllegalArgumentException(QueryBuilderErrors.WRONG_READ_DELETE_PARAM);
        }

        if (queryModel.where != null && queryModel.whereValue != null) {
            query = whereClause(queryModel, query);
        }

        return query;
    }

    private static String whereClause(DBQueryModel queryModel, String query) {
        String[][] values = queryModel.whereValue;
        String[] where = queryModel.where;
        StringBuilder sb = new StringBuilder(query);
        sb.append(" AND ");
        for (int i = 0; i < where.length; i++) {
            sb.append(where[i]).append(" IN (");

            for (int j = 0; j < values[i].length; j++) {
                sb.append("'").append(values[i][j]).append("'");
                // Check if it's the last value if it is omit the ','
                if (j != values[i].length - 1) {
                    sb.append(",");
                } else {
                    sb.append(") ");
                }

            }
            // If this is not the last 'WHERE' clause add the logical operator and a space
            // For example: "SELECT * ... WHERE 'userId' = ... AND/OR 'round' = ..."
            if (i != where.length -1) sb.append(queryModel.logicalOperator).append(" ");
        }

        return sb.toString();
    }

}

