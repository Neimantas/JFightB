package main.Models.DTO;

import main.Models.DAL.ChallengeDAL;

import java.util.List;

public class ChallengeDTO {
    private boolean Success;
    private String Message;
    private List<ChallengeDAL> List;

    public ChallengeDTO(boolean success, String message, java.util.List<ChallengeDAL> list) {
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

    public java.util.List<ChallengeDAL> getList() {
        return List;
    }

    public void setList(java.util.List<ChallengeDAL> list) {
        List = list;
    }
}