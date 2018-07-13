package main.Services.Impl;

import main.Models.BL.User;
import main.Models.DTO.LoginDTO;
import main.Models.DTO.UserDTO;
import main.Services.IHigherService;
import main.Services.ILoginService;
import org.modelmapper.ModelMapper;

public class LoginService implements ILoginService {

    @Override
    public LoginDTO find(String email, String password) {
        IHigherService hs = new HigherService();
        UserDTO userDTO = hs.getUserByEmail(email, password);
        if (!userDTO.isSuccess()) {
            return new LoginDTO(false, userDTO.getMessage(), null);
        }
        ModelMapper mod = new ModelMapper();
        return new LoginDTO(true, null, mod.map(userDTO.getUser(), User.class));
    }
}
