package main.Models.DTO;

import java.util.List;

public class DBqueryDTO {
    private boolean Success;
    private String Message;
    private List<List<Object>> List;

    public DBqueryDTO(boolean success, String message, List<List<Object>> list) {
        Success = success;
        Message = message;
        List = list;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<List<Object>> getList() {
        return List;
    }

    public void setList(List<List<Object>> list) {
        List = list;
    }
}
