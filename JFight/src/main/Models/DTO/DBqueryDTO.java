package Models.DTO;

import java.util.List;

public class DBqueryDTO {
    private boolean Success;
    private String Message;
    private List<List<Object>> List;

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

    public java.util.List<java.util.List<Object>> getList() {
        return List;
    }

    public void setList(java.util.List<java.util.List<Object>> list) {
        List = list;
    }
}
