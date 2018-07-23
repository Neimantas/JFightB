package main.Models.BL;

import java.util.List;

public class IssuedChallenges {
    public long userId;
    public String userName;
    public List<Long> userChallenges;
    public List<Long> oppLong;

    public IssuedChallenges(long userId, String userName, List<Long> userChallenges, List<Long> oppLong) {
        this.userId = userId;
        this.userName = userName;
        this.userChallenges = userChallenges;
        this.oppLong = oppLong;
    }
}
