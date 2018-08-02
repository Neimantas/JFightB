package main.Services.Impl;

import main.Models.BL.UserModel;
import main.Models.DAL.UserDAL;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.RegisterDTO;
import main.Models.DTO.UserDTO;
import main.Services.ICache;
import main.Services.IHigherService;
import main.Services.IRegisterService;
import org.modelmapper.ModelMapper;

import java.util.UUID;

public class RegisterService implements IRegisterService {

    IHigherService hs = new HigherService();
    ICache cache = Cache.getInstance();

    @Override
    public RegisterDTO find(String userName, String email) {
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

        String hash = null;
        try {
            hash = HashService.getSaltedHash(password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserDAL user = new UserDAL();
        user.userName = userName;
        user.password = hash;
        user.email = email;
        DBqueryDTO dto = hs.registerUser(user);

        if (!dto.success){
            return new RegisterDTO(false, dto.message, null);
        }

        ModelMapper mod = new ModelMapper();
        mod.getConfiguration().setFieldMatchingEnabled(true);
        return new RegisterDTO(true, "", null);
    }




    public UserModel addUserToCache(String email) {
        UserDTO userDTO = hs.getUserByEmail(email);
        if (!userDTO.success) {
            return null;
        }
        String uuid = UUID.randomUUID().toString();
        UserModel user = new UserModel(userDTO.user.userName, userDTO.user.userId, uuid);
        cache.put(uuid, user);
        return user;
    }

}