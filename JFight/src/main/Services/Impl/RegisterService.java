package main.Services.Impl;



import main.Models.BL.UserRegister;
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
//        mod.getConfiguration().setFieldMatchingEnabled(true);
            return new RegisterDTO(false, userDTO.message, mod.map(userDTO.user, UserRegister.class));
        }
    }
}