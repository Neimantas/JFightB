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
}
