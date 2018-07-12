package main.Services.Impl;

import main.Models.DTO.UserDTO;
import main.Services.ICrud;
import main.Services.ILoginService;

public class LoginService implements ILoginService {

    @Override
    public UserDTO find(String username, String password) {
        ICrud Crud = new Crud();
        //UserDTO userDTO = new UserDTO();
        return null;
    }
}
