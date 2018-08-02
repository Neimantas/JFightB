package main.Services;

import main.Models.BL.UserModel;
import main.Models.DTO.RegisterDTO;

public interface IRegisterService {
    RegisterDTO find(String userName, String email);
    RegisterDTO register(String userName, String password, String email);
    UserModel addUserToCache(String email);

}
