package main.Services;

import main.Models.DAL.ChallengeDAL;
import main.Models.DTO.*;

import java.util.List;

public interface IChallenge {
    boolean addPlayerToReadyToFight(long userId, String username);

    boolean checkIfUsersChallengedEachOther(long userId);

    boolean checkIfUserIsFighting(long userId);

    FightDTO createFightForMatchedPlayers(ChallengeDAL dal);

    IssuedChallengesDTO getIssuedChallenges(long userId);

    ReadyToFightDTO getReadyToFightUsersExceptPrimaryUser(long userId);

    boolean submitChallenges(List<ChallengeDAL> dalList);
}
