package main.Models.DTO;


import main.Models.BL.UserRegister;

public class RegisterDTO {
    public boolean success;
    public String message;
    public UserRegister user;

    public RegisterDTO(boolean Success, String Message, UserRegister User) {
        success = Success;
        message = Message;
        user = User;
    }


}
