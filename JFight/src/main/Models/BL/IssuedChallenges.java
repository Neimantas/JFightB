package main.Models.BL;

import java.util.List;

public class IssuedChallenges {
    public long userId;
    public String userName;
    public List<Long> userChallenges;
    public List<Long> oppChallenges;

    public IssuedChallenges(long UserId, String UserName, List<Long> UserChallenges, List<Long> OppLong) {
        userId = UserId;
        userName = UserName;
        userChallenges = UserChallenges;
        oppChallenges = OppLong;
    }
}
