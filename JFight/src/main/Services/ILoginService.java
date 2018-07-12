package main.Services;

import main.Models.DTO.UserDTO;

public interface ILoginService {
    UserDTO find(String username, String password);
}
