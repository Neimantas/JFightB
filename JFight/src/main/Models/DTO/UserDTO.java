package main.Models.DTO;

import main.Models.DAL.UserDAL;

public class UserDTO {
    private boolean Success;
    private String Message;
    private UserDAL User;

    public UserDTO(){

    }
    public UserDTO(boolean success, String message, UserDAL user) {
        Success = success;
        Message = message;
        User = user;
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

    public UserDAL getUser() {
        return User;
    }

    public void setUser(UserDAL user) {
        User = user;
    }
}
