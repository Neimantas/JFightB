package Models.DTO;

import java.util.List;

public class FightDTO {
    private boolean Success;
    private String Message;
    private List<FightDAL> List;

    public FightDTO(boolean success, String message, java.util.List<FightDAL> list) {
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

    public java.util.List<FightDAL> getList() {
        return List;
    }

    public void setList(java.util.List<FightDAL> list) {
        List = list;
    }
}
