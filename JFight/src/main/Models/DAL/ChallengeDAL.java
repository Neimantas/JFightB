package main.Models.DAL;

public class ChallengeDAL {
    public long id;
    public long userId;
    public long opponentId;

    public ChallengeDAL(long id, long userId, long opponentId) {
        this.id = id;
        this.userId = userId;
        this.opponentId = opponentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
