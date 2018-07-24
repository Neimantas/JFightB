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

//        if (username != null && password != null) {
//            request.getRequestDispatcher("/news.jsp").forward(request, response);
//        }
        LoginDTO login = loginService.find(emailLogin, password);
        if(login.isSuccess()) {
            String name = login.getUser().getName();
            request.setAttribute("username", name);
            request.getRequestDispatcher("/news.jsp").forward(request, response);
        }
        // TODO if the email || pass is invalid send an error to Front end



//        if (user != null) {
//            request.getSession().setAttribute("user", user);
//            response.sendRedirect("news");
//        }
//        else {
//            request.setAttribute("error", "Unknown user, please try again");
//            request.getRequestDispatcher("/login.jsp").forward(request, response);
//        }
//        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int paramSize = request.getParameterMap().size();
//        System.out.println(paramSize);
//        if (paramSize > 0) {
//        }
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
