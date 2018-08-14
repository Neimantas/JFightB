package main.Services;

import main.Models.DTO.LoginDTO;

import javax.servlet.http.Cookie;

public interface ILoginService {
    LoginDTO find(String email, String password);

    boolean isValid(Cookie cookie);

    Cookie findTokenCookie(Cookie[] cookies);
}
