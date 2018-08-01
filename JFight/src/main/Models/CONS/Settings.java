package main.Models.CONS;

public final class Settings {
    //Its the amount of time items will be saved in Cache.class, now its set to 120 seconds
    public final static long LIVE_TIME = 120000;
    // Our cookie name which will be used to parse Cookie[]
    public final static String COOKIE_NAME = "token";
    //Checks needed parameters in Challenge servlet
    public final static String[] CHALLENGE_PARAMETERS = new String[]{"challengedPlayers"};
    //Checks initial fight parameters when players get matched
    public final static String[] INITIAL_FIGHT_PARAMETERS = new String[]{"round", "fightId", "userId", "initial"};
    //Checks parameters for each fight round
    public final static String[] FIGHT_PARAMETERS = new String[]{"userName", "oppName", "att1", "att2", "def1", "def2", "userHp"};
}
