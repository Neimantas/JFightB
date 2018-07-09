package main.Controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String regName = request.getParameter("regname");
        String regPass = request.getParameter("regpass");
        String confPass= request.getParameter("confpass");
        String regEmail = request.getParameter("regemail");

        if (username.equals("test")) {
            request.getRequestDispatcher("/user.jsp").forward(request, response);
        }
//        User user = userService.find(username, password);
//
//        if (user != null) {
//            request.getSession().setAttribute("user", user);
//            response.sendRedirect("home");
//        }
//        else {
//            request.setAttribute("error", "Unknown user, please try again");
//            request.getRequestDispatcher("/login.jsp").forward(request, response);
//        }
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int paramSize = request.getParameterMap().size();
        System.out.println(paramSize);
        if (paramSize > 0) {
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
