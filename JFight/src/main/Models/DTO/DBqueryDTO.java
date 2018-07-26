package main.Models.DTO;

import java.util.List;

public class DBqueryDTO {
    public boolean success;
    public String message;
    public List<List<Object>> list;

    public DBqueryDTO(boolean Success, String Message, List<List<Object>> List) {
        success = Success;
        message = Message;
        list = List;
    }
}
