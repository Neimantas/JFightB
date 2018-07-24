package main.Models.DAL;

public class FightDAL {

    private String FightId;
    private long UserId1;
    private long UserId2;

    public String getFightId() {
        return FightId;
    }

    public void setFightId(String fightId) {
        FightId = fightId;
    }

    public long getUserId1() {
        return UserId1;
    }

    public void setUserId1(long userId1) {
        UserId1 = userId1;
    }

    public long getUserId2() {
        return UserId2;
    }

    public void setUserId2(long userId2) {
        UserId2 = userId2;
    }
}
