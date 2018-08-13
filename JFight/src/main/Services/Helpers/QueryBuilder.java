package main.Services.Helpers;

import main.Models.BL.DBQueryModel;

public class QueryBuilder {
    //Review. Think concurency. Now 1 method can reset builder and other add.
    private StringBuilder sb = new StringBuilder(); //Review. In most cases we use stringbuilder for totally new string. Chech this.
    private String tableName;

    public QueryBuilder(String theTableName) {
        tableName = theTableName;
    }

    public QueryBuilder buildQuery(DBQueryModel queryModel, String readOrDelete) {
    	
        if (tableName == null || tableName.isEmpty()) {
        	//Review. How about no strings as errors?
            throw new IllegalArgumentException("Missing table name!");
        }

        //Review. I think we have method for is null or default value?
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
            whereClause(queryModel);
        }
        //Review. This is spaaaaaaaaaarrrtttttaaaaaaaaa. 
        return this;
    }

    private void whereClause(DBQueryModel queryModel) {
        String[][] values = queryModel.whereValue;
        String[] where = queryModel.where;
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
//Review. Please comment why?
            if (i != where.length -1) sb.append(queryModel.logicalOperator).append(" ");
        }

    }

    public String getQuery(){
        return sb.toString();
    }

}

