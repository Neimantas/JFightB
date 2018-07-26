package main.Models.DTO;

import main.Models.BL.IssuedChallenges;

public class IssuedChallengesDTO {
    public boolean success;
    public String message;
    public IssuedChallenges issuedChallenge;

    public IssuedChallengesDTO(boolean Success, String Message, IssuedChallenges IssuedChallenge) {
        success = Success;
        message = Message;
        issuedChallenge = IssuedChallenge;
    }
}
