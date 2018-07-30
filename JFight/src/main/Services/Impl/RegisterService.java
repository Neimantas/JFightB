package main.Services.Impl;

import main.Models.BL.UserRegisterModel;
import main.Models.DTO.RegisterDTO;
import main.Models.DTO.UserDTO;
import main.Services.IHigherService;
import main.Services.IRegisterService;
import org.modelmapper.ModelMapper;

public class RegisterService implements IRegisterService {

    @Override
    public RegisterDTO find(String userName, String email) {
        IHigherService hs = new HigherService();
        UserDTO userDTO = hs.getUserByUserNameAndEmail(userName, email);
        if (userDTO.success) {
            return new RegisterDTO(true, null, null);
        } else {
            ModelMapper mod = new ModelMapper();
        mod.getConfiguration().setFieldMatchingEnabled(true);
            return new RegisterDTO(false, null, null);
        }
    }

    @Override
    public RegisterDTO register(String userName, String password, String email) {
        IHigherService hs = new HigherService();
        String hash = null;
        try {
            hash = HashService.getSaltedHash(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UserDTO userDTO = hs.registerUser(userName, hash, email);
        if (userDTO.success){
            return new RegisterDTO(false,null, null);
        }else {
            ModelMapper mod = new ModelMapper();
            mod.getConfiguration().setFieldMatchingEnabled(true);
            return new RegisterDTO(true, null, null);
        }
    }
}