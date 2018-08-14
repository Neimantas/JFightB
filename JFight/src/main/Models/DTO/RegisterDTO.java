package main.Models.DTO;

import main.Models.BL.UserRegisterModel;

public class RegisterDTO {
    public boolean success;
    public String message;
    public UserRegisterModel userRegisterModel;

    public RegisterDTO(boolean Success, String Message, UserRegisterModel UserRegisterModel) {
        success = Success;
        message = Message;
        userRegisterModel = UserRegisterModel;
    }
}
