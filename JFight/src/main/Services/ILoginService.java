package main.Services;

import main.Models.DTO.LoginDTO;

public interface ILoginService {
    LoginDTO find(String email, String password);
}
