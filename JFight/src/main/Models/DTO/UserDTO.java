package Models.DTO;

import java.util.List;

public class UserDTO {
    private boolean Success;
    private String Message;
    private List<UserDAL> List;

    public UserDTO(boolean success, String message, java.util.List<UserDAL> list) {
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

    public java.util.List<UserDAL> getList() {
        return List;
    }

    public void setList(java.util.List<UserDAL> list) {
        List = list;
    }
}
