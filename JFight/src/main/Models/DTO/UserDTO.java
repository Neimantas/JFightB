package main.Models.DTO;

import main.Models.BL.User;

public class UserDTO {
    private boolean Success;
    private String Message;
    private User User;

    public UserDTO(boolean success, String message, User user) {
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

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }
}
