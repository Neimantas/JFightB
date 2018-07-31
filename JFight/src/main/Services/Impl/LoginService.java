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
        boolean testHash = false;
        UserDTO userDTO = hs.getUserByEmail(email);
        String hash = userDTO.user.password;
        try {
            testHash = HashService.check(password, hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (testHash == true && userDTO.success) {
            System.out.println("TESTHASH " + testHash);
            System.out.println(userDTO.success);
            ModelMapper mod = new ModelMapper();
            return new LoginDTO(true, null, mod.map(userDTO.user, User.class));
        } else {
            System.out.println("TESTHASH" + testHash);
            System.out.println(userDTO.success);
            return new LoginDTO(false, userDTO.message, null);
        }
    }
}
