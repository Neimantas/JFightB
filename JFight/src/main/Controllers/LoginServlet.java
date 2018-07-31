package main.Controllers;

import main.Models.DTO.LoginDTO;
import main.Services.ILoginService;
import main.Services.Impl.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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


        if (regName == null || regEmail.isEmpty()) {
            ILoginService loginService = new LoginService();
            LoginDTO login = loginService.find(emailLogin, password);

            if (login.success) {
                System.out.println("******************************");
                System.out.println("LOGIN SUCCESS");
                response.addCookie(new Cookie("token", login.user.uuid));
//                request.getRequestDispatcher("/news").forward(request, response);
                response.sendRedirect("/news");
            } else {
                // TODO print an ALERT that Login info is incorrect
            RegisterDTO register = registerService.find(regName, regEmail);
            if (register.success) {
                out.print("Sorry username or email used");
                response.sendRedirect("/login.jsp");
            } else {
                IRegisterService registerService1 = new RegisterService();
                RegisterDTO registerDTO = registerService1.register(regName, regPass, regEmail);
                if (registerDTO.success) {
                    response.sendRedirect("/login.jsp");
                }else {
                    response.sendRedirect("/login.jsp");
                }
            }
//        } else {
//            IRegisterService registerService = new RegisterService();
//            RegisterDTO register = registerService.find(regName,regEmail);
//            if (register.success){
//                out.print("Sorry username or email used");
//                response.sendRedirect("/login.jsp");
//            } else {
//                //TODO cia dirbam
//                String name = login.user.name;
//                request.setAttribute("username", name);
//                request.getRequestDispatcher("/news.jsp").forward(request, response);
//            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
}
