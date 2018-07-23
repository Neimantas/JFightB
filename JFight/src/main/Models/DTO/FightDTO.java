package main.Models.DTO;

import main.Models.DAL.FightDAL;

public class FightDTO {
    private boolean Success;
    private String Message;
    private FightDAL dal;

    public FightDTO(boolean success, String message, FightDAL dal) {
        Success = success;
        Message = message;
        this.dal = dal;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public FightDAL getDal() {
        return dal;
    }

    public void setDal(FightDAL dal) {
        this.dal = dal;
    }
}
