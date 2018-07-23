package main.Models.DTO;

import main.Models.BL.IssuedChallenges;

public class IssuedChallengesDTO {
    boolean success;
    String message;
    IssuedChallenges issuedChallenge;

    public IssuedChallengesDTO(boolean success, String message, IssuedChallenges issuedChallenge) {
        this.success = success;
        this.message = message;
        this.issuedChallenge = issuedChallenge;
    }
}
