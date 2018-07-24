package main.Models.BL;

public class DBQueryModel {

    private String Table;
    private String Where;
    private String[] WhereValue;
    private String UpdateWhat;
    private String UpdateValue;
    private String CreateValues;

    public String getTable() {
        return Table;
    }

    public void setTable(String table) {
        Table = table;
    }

    public String getWhere() {
        return Where;
    }

    public void setWhere(String where) {
        Where = where;
    }

    public String[] getWhereValue() {
        return WhereValue;
    }

    public void setWhereValue(String[] whereValue) {
        WhereValue = whereValue;
    }

    public String getUpdateWhat() {
        return UpdateWhat;
    }

    public void setUpdateWhat(String updateWhat) {
        UpdateWhat = updateWhat;
    }

    public String getUpdateValue() {
        return UpdateValue;
    }

    public void setUpdateValue(String updateValue) {
        UpdateValue = updateValue;
    }

    public String getCreateValues() {
        return CreateValues;
    }

    public void setCreateValues(String createValues) {
        CreateValues = createValues;
    }

}
