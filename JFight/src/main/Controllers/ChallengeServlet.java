package main.Controllers;

import main.Models.BL.User;
import main.Models.DAL.ChallengeDAL;
import main.Models.DTO.*;
import main.Services.ICache;
import main.Services.IChallenge;
import main.Services.Impl.Cache;
import main.Services.Impl.ChallengeService;
import main.Services.Impl.LoginService;
import main.Services.ObjectConverterToString;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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

        LoginService loginService = new LoginService();
        Cookie token = loginService.findTokenCookie(request.getCookies());

        if (token != null && loginService.validate(token)) {
            ICache cache = Cache.getInstance();
            User user = (User) cache.get(token.getValue());
            request.setAttribute("userName", user.name);

            List<ChallengeDAL> dalList = new ArrayList<>();
            IChallenge cs = new ChallengeService();
            cs.addPlayerToReadyToFight(user.id);

            // TODO this should be in POST
            if (request.getParameter("challengedPlayers") != null) {

                String[] split = request.getParameter("challengedPlayers").split("#");
                for (String s : split) {
                    dalList.add(new ChallengeDAL(user.id, Long.parseLong(s)));
                }

                DBqueryDTO dbDTO = cs.submitChallenges(dalList);

                if (dbDTO.success) {

                    // WHILE Should be here - loop for 30s or until challenged player accepts the challenge
                    ChallengeDTO challengeDTO = cs.checkForMatches(user.id);
                    int count = 0;
                    while(!challengeDTO.success && count < 30) {
                        challengeDTO = cs.checkForMatches(user.id);
                        System.out.println("USER - " + user.id + " dto message -> " + challengeDTO.message);
                        try {
                            Thread.sleep(1000);
                            count++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (challengeDTO.success) {

                        FightDTO fightDTO = cs.checkIfUserGotMatched(user.id);

                        if (fightDTO.success) {
                            request.getRequestDispatcher("/fight?fightId=" + fightDTO.dal.fightId +
                                    "&userId=" + user.id + "&round=0").forward(request, response);
                            return;
                        }

                        fightDTO = cs.createFightForMatchedPlayers(challengeDTO.list.get(0));

                        if (fightDTO.success) {
                            request.getRequestDispatcher("/fight?fightId=" + fightDTO.dal.fightId +
                                    "&userId=" + user.id + "&round=0").forward(request, response);
                            return;
                        }

                    } else {
                        IssuedChallengesDTO issuedChallengesDTO = cs.getIssuedChallenges(user.id);

                        if (issuedChallengesDTO.success) {
                            request.setAttribute("userChallenges", ObjectConverterToString.convertList(issuedChallengesDTO.issuedChallenge.userChallenges));
                            request.setAttribute("oppChallenges", ObjectConverterToString.convertList(issuedChallengesDTO.issuedChallenge.oppChallenges));
                        }
                    }
                }
            }
            // User has entered the challenge page for the first time or no matches found, return him all players Ready to Fight
            ReadyToFightDTO readyDTO = cs.getAllReadyToFightUsersId(user.id);

            if (readyDTO.list.size() > 0) {
                request.setAttribute("readyToFightList", ObjectConverterToString.convertList(readyDTO.list));
                readyDTO.list.forEach(el -> System.out.println(el.userName));
            }
            request.getRequestDispatcher("/challenge.jsp").forward(request, response);
        }

        response.sendRedirect("/login");
    }
}
