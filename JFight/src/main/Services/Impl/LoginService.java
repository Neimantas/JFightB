package main.Services.Impl;

import main.Models.BL.User;
import main.Models.DAL.UserDAL;
import main.Models.DTO.LoginDTO;
import main.Models.DTO.UserDTO;
import main.Services.IHigherService;
import main.Services.ILoginService;
import org.modelmapper.ModelMapper;

public class LoginService implements ILoginService {

    @Override
    public LoginDTO find(String email, String password) {
        IHigherService hs = new HigherService();
        UserDAL userDAL = new UserDAL();
        userDAL.email = email;
        userDAL.password = password;
        UserDTO userDTO = hs.getUserByEmailAndPass(userDAL);

        if (!userDTO.success) {
            return new LoginDTO(false, userDTO.message, null);
        }

        ModelMapper mod = new ModelMapper();
        mod.getConfiguration().setFieldMatchingEnabled(true);
        return new LoginDTO(true, null, mod.map(userDTO.user, User.class));
    }
}
