package main.Models.DTO;

import main.Models.DAL.FightDAL;
import java.util.List;

public class FightDTO {
    private boolean Success;
    private String Message;
    private List<FightDAL> List;

    public FightDTO(boolean success, String message, List<FightDAL> list) {
        Success = success;
        Message = message;
        List = list;
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

    public List<FightDAL> getList() {
        return List;
    }

    public void setList(List<FightDAL> list) {
        List = list;
    }
}
