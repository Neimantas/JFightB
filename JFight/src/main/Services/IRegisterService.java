package main.Services;

import main.Models.DTO.RegisterDTO;

public interface IRegisterService {
    RegisterDTO find(String userName, String email);
}
