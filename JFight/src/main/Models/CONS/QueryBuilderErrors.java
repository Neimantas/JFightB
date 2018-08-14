package main.Models.CONS;

public final class QueryBuilderErrors {
    public static String MISSING_TABLE_NAME
            = "QueryBuilder cannot Build query without a valid Table name (not null or empty).";
    public static String MISSING_READ_DELETE_PARAM
            = "QueryBuilder cannot Build query without a valid 'readOrDelete' argument (not null or empty).";
    public static String WRONG_READ_DELETE_PARAM
            = "QueryBuilder cannot Builde query without a valide 'readOrDelete' argument (must be either 'read' or 'delete').";
}
