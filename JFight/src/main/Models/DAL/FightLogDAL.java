package main.Models.DAL;

public class FightLogDAL {
    
    public String fightId;
    public long userId;
    //Review. DAL are mirrored DB objects. If attack1 is null, then it is null
    public String attack1 = "NONE";
    public String attack2 = "NONE";
    public String defence1 = "NONE";
    public String defence2 = "NONE";
    public int hp;
    public int round;
}
