package main.Controllers;

import main.Models.DAL.ChallengeDAL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ChallengeServlet", urlPatterns = {"/challenge"})
public class ChallengeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] split;
        List<ChallengeDAL> dalList = new ArrayList<>();
        long userId = request.getParameter("userId") != null ?  Long.parseLong(request.getParameter("userId")) : 0;
        if (request.getParameter("challengedPlayers") != null) {
            split = request.getParameter("challengedPlayers").split("#");
            for (String s : split) {
                dalList.add(new ChallengeDAL(0, userId, Long.parseLong(s)));
            }
        }





        request.getRequestDispatcher("/challenge.jsp").forward(request, response);
    }
}
