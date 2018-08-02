package main.Controllers;

import main.Models.BL.UserExtendedModel;
import main.Models.BL.UserModel;
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

        ILoginService loginService = new LoginService();
        Cookie token = loginService.findTokenCookie(request.getCookies());

        if (loginService.validate(token)) {

            ICache cache = Cache.getInstance();
            UserModel user = (UserModel) cache.get(token.getValue());
            UserInfoService userInfoService = new UserInfoService();
            UserExtendedModel userExtendedModel = userInfoService.getUserExtendedById(user.id);

            if (userExtendedModel != null) {

                request.setAttribute("userId", userExtendedModel.userId);
                request.setAttribute("userName", userExtendedModel.userName);
                request.setAttribute("image", userExtendedModel.image);
                request.setAttribute("win", userExtendedModel.win);
                request.setAttribute("lose", userExtendedModel.lose);
                request.setAttribute("draw", userExtendedModel.draw);
                request.setAttribute("totalFights", userExtendedModel.totalFights);
                request.getRequestDispatcher("/user.jsp").forward(request, response);
            } else {
                // TODO an error has occurred SHOW ERROR message to Front
            }

        }

        response.sendRedirect("/login");

    }
}
