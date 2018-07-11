package Models.DTO;
import Models.DAL.UserDAL;
import Models.DAL.UserDAL;

import java.util.List;

public class UserDTO {
    private boolean Success;
    private String Message;
    private List<UserDAL> List;

    public UserDTO(boolean success, String message, List<UserDAL> list) {
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

    public List<UserDAL> getList() {
        return List;
    }

    public void setList(List<UserDAL> list) {
        List = list;
    }
}
