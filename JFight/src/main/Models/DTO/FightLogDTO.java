package Models.DTO;

import java.util.List;

public class FightLogDTO {
    private boolean Success;
    private String Message;
    private List<FightLogDAL> List;

    public FightLogDTO(boolean success, String message, java.util.List<FightLogDAL> list) {
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

    public java.util.List<FightLogDAL> getList() {
        return List;
    }

    public void setList(java.util.List<FightLogDAL> list) {
        List = list;
    }
}
