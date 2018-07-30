package main.Services.Impl;

import main.Models.BL.User;
import main.Models.CONS.Settings;
import main.Models.DAL.UserDAL;
import main.Models.DTO.LoginDTO;
import main.Models.DTO.UserDTO;
import main.Services.ICache;
import main.Services.IHigherService;
import main.Services.ILoginService;

import javax.servlet.http.Cookie;
import java.util.UUID;

public class LoginService implements ILoginService {

    private ICache cache = Cache.getInstance();

    @Override
    public LoginDTO find(String email, String password) {
        IHigherService hs = new HigherService();
        UserDTO userDTO = hs.getUserByEmailAndPass(email, password);

        if (!userDTO.success) {
            return new LoginDTO(false, userDTO.message, null);
        }

        return new LoginDTO(true, "", addUserToCache(userDTO.user));
    }

    private User addUserToCache(UserDAL userDAL) {
        String uuid = UUID.randomUUID().toString();
        User user = new User(userDAL.userName, userDAL.userId, uuid);
        cache.put(uuid, user);
        return user;
    }

    @Override
    public boolean validate(Cookie[] cookies) {
        if (cookies == null || cookies.length == 0){
            return false;
        }

        Cookie cookie = null;

        for (Cookie ck : cookies) {
            if (ck.getName().equals(Settings.COOKIE_NAME)) {
                cookie = ck;
                break;
            }
        }

        if (cookie == null || cache.get(cookie.getValue()) == null) {
            return false;
        }

        return true;
    }


}
