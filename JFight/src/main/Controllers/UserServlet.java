package main.Controllers;

import main.Models.BL.UserExtendedModel;
import main.Models.BL.UserModel;
import main.Services.Helpers.ObjectConverterToString;
import main.Services.ICache;
import main.Services.ILoginService;
import main.Services.Impl.Cache;
import main.Services.Impl.LoginService;
import main.Services.Impl.UserInfoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet", urlPatterns = {"/user"})
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserInfoService userInfoService = new UserInfoService();
        ILoginService loginService = new LoginService();
        Cookie token = loginService.findTokenCookie(request.getCookies());
        if (loginService.isValid(token)) {
        ICache cache = Cache.getInstance();
        UserModel user = (UserModel) cache.get(token.getValue());
        UserExtendedModel userExtendedModel = userInfoService.getUserExtendedById(user.id);

        if (loginService.validate(token) && userExtendedModel != null) {

            request.setAttribute("userExtended", ObjectConverterToString.convertObject(userExtendedModel));
            String imgString = javax.xml.bind.DatatypeConverter.printBase64Binary(userExtendedModel.image);
            request.setAttribute("image", imgString);
            request.getRequestDispatcher("/user.jsp").forward(request, response);

        } else {
            response.sendRedirect("/login");
        }
    }
}

