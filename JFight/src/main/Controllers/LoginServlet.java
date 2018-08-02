package main.Controllers;

import main.Models.BL.User;
import main.Models.DTO.LoginDTO;
import main.Models.DTO.RegisterDTO;
import main.Services.ILoginService;
import main.Services.IRegisterService;
import main.Services.Impl.LoginService;
import main.Services.Impl.RegisterService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailLogin = request.getParameter("email");
        String password = request.getParameter("password");
        String regName = request.getParameter("regName");
        String regPass = request.getParameter("regPass");
        String confPass = request.getParameter("confPass");
        String regEmail = request.getParameter("regEmail");


        if (isAllRegParamsAreCorrect(emailLogin, password)) {
            ILoginService loginService = new LoginService();
            LoginDTO login = loginService.find(emailLogin, password);
        response.sendRedirect("/login");
            if (login.success) {
                response.addCookie(new Cookie("token", login.user.uuid));
//                response.sendRedirect("/news");
                response.sendRedirect(request.getContextPath() + "/news");
            }
            if (!login.success) {
                response.sendRedirect("/login.jsp");
            }
        }
        if (isAllRegParamsAreCorrect(regName, regEmail, regPass, confPass)) {
            IRegisterService registerService = new RegisterService();
            RegisterDTO isRegistered = registerService.find(regName, regEmail);
            if (isRegistered.success) {
                //TODO send some parameter and display in js that this user is already registered
                response.sendRedirect("/login.jsp");
            }
            if (!isRegistered.success) {
                RegisterDTO registerDTO = registerService.register(regName, regPass, regEmail);
                User user = registerService.addUserToCache(regEmail);
                response.addCookie(new Cookie("token", user.uuid));
                response.sendRedirect("/news");
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private boolean isAllRegParamsAreCorrect(String... params) {
        boolean success = true;
        for (int i = 0; i < params.length; i++) {
            if (params[i] == null || params[i].isEmpty()) {
                success = false;
            }
        }
        return success;
    }
}

