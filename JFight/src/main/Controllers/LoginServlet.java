package main.Controllers;

import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.LoginDTO;
import main.Services.ILoginService;
import main.Services.Impl.Crud;
import main.Services.Impl.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String emailLogin = request.getParameter("email");
        String password = request.getParameter("password");
        String regName = request.getParameter("regName");
        String regPass = request.getParameter("regPass");
        String confPass = request.getParameter("confPass");
        String regEmail = request.getParameter("regEmail");


        if (regName == null || regEmail == "") {
            ILoginService loginService = new LoginService();
            LoginDTO login = loginService.find(emailLogin, password);
            if (login.success) {
                String name = login.user.name;
                request.setAttribute("username", name);
                request.getRequestDispatcher("/news.jsp").forward(request, response);
            } else {
                out.print("Sorry username or password error");
                response.sendRedirect("/login.jsp");
            }
            out.close();
        } else {
            Crud crud = new Crud();
            String query = "select * from [User] where UserName = '" + regName + "' or Email = '" + regEmail + "';";
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
