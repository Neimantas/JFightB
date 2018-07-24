package main.Models.DAL;

public class ChallengeDAL {
    public long userId;
    public long opponentId;

    public ChallengeDAL(long userId, long opponentId) {
        this.userId = userId;
        this.opponentId = opponentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(long opponentId) {
        this.opponentId = opponentId;
    }
}
