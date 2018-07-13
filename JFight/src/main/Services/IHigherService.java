package main.Services;

import main.Models.DTO.ReadyToFightDTO;
import main.Models.DTO.UserDTO;

public interface IHigherService {
    ReadyToFightDTO getAllReadyToFightUsersId();
    ReadyToFightDTO getReadyToFightUserById(String query);
    UserDTO getUserByEmailAndPass(String email, String password);
}
