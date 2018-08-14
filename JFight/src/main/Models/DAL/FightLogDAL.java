package main.Models.DAL;

public class FightLogDAL {
    
    public String fightId;
    public long userId;
    //Review. DAL are mirrored DB objects. If attack1 is null, then it is null
    public String attack1;
    public String attack2;
    public String defence1;
    public String defence2;
    public int hp;
    public int round;
}
