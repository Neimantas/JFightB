package main.Controllers;

import main.Models.DTO.LoginDTO;
import main.Services.ILoginService;
import main.Services.Impl.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ILoginService loginService = new LoginService();
        String emailLogin = request.getParameter("emailLogin");
        String password = request.getParameter("password");
        String regName = request.getParameter("regName");
        String regPass = request.getParameter("regPass");
        String confPass= request.getParameter("confPass");
        String regEmail = request.getParameter("regEmail");

        LoginDTO login = loginService.find(emailLogin, password);
        if(login.success) {
            String name = login.user.name;
            request.setAttribute("username", name);
            request.getRequestDispatcher("/news.jsp").forward(request, response);
        }
        // TODO if the email || pass is invalid send an error to Front end
        response.sendRedirect("/login");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
