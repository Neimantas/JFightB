package main.Models.DTO;

import main.Models.DAL.FightResultDAL;

public class FightResultDTO {
    public boolean success;
    public String message;
    public FightResultDAL dal;

    public FightResultDTO(boolean Success, String Message, FightResultDAL Dal) {
        success = Success;
        message = Message;
        dal = Dal;
    }
}
