package main.Models.DTO;

import main.Models.BL.User;

public class LoginDTO {
    public boolean success;
    public String message;
    public User user;

    public LoginDTO(boolean Success, String Message, User User) {
        success = Success;
        message = Message;
        user = User;
    }
}
