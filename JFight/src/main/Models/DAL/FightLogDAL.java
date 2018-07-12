package main.Models.DAL;

public class FightLogDAL {

    private long FightId;
    private long UserId;
    private String Attack1;
    private String Attack2;
    private String Defence1;
    private String Defence2;
    private int Hp;
    private long Round;

    public long getFightId() {
        return FightId;
    }

    public void setFightId(long fightId) {
        FightId = fightId;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getAttack1() {
        return Attack1;
    }

    public void setAttack1(String attack1) {
        Attack1 = attack1;
    }

    public String getAttack2() {
        return Attack2;
    }

    public void setAttack2(String attack2) {
        Attack2 = attack2;
    }

    public String getDefence1() {
        return Defence1;
    }

    public void setDefence1(String defence1) {
        Defence1 = defence1;
    }

    public String getDefence2() {
        return Defence2;
    }

    public void setDefence2(String defence2) {
        Defence2 = defence2;
    }

    public int getHp() {
        return Hp;
    }

    public void setHp(int hp) {
        Hp = hp;
    }

    public long getRound() {
        return Round;
    }

    public void setRound(long round) {
        Round = round;
    }
}
