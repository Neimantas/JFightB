package main.Services.Impl;

import main.Models.DTO.UserDTO;
import main.Services.ILoginService;

public class LoginService implements ILoginService {

    @Override
    public UserDTO find(String username, String password) {
        
        UserDTO userDTO = new UserDTO();
        return null;
    }
}
