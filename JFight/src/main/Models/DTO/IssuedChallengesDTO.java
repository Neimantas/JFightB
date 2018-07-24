package main.Models.DTO;

import main.Models.BL.IssuedChallenges;

public class IssuedChallengesDTO {
    public boolean success;
    public String message;
    public IssuedChallenges issuedChallenge;

    public IssuedChallengesDTO(boolean success, String message, IssuedChallenges issuedChallenge) {
        this.success = success;
        this.message = message;
        this.issuedChallenge = issuedChallenge;
    }
}
