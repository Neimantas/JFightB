package main.Models.DTO;

import main.Models.DAL.FightResultDAL;

public class FightResultDTO {
    public boolean success;
    public String message;
    public FightResultDAL dal;

    public FightResultDTO(boolean success, String message, FightResultDAL dal) {
    	//Review. THIS IS SPARTAAAAA
        this.success = success;
        this.message = message;
        this.dal = dal;
    }
}
