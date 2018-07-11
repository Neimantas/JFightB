package Models.DTO;

import Models.DAL.ReadyToFightDAL;

import java.util.List;

public class ReadyToFightDTO {
    private boolean Success;
    private String Message;
    private List<ReadyToFightDAL> List;

    public ReadyToFightDTO(boolean success, String message, java.util.List<ReadyToFightDAL> list) {
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

    public java.util.List<ReadyToFightDAL> getList() {
        return List;
    }

    public void setList(java.util.List<ReadyToFightDAL> list) {
        List = list;
    }
}
