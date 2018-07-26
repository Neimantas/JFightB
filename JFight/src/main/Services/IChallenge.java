package main.Services;

import main.Models.DAL.ChallengeDAL;
import main.Models.DTO.*;

import java.util.List;

public interface IChallenge {
    DBqueryDTO submitChallenges(List<ChallengeDAL> dalList);
    ChallengeDTO checkForMatches(long userId);
    FightDTO createFightForMatchedPlayers(ChallengeDAL dal);
    IssuedChallengesDTO getIssuedChallenges(long userId);
    ReadyToFightDTO getAllReadyToFightUsersId(long userId);
    DBqueryDTO addPlayerToReadyToFight(long userId);
    FightDTO checkIfUserGotMatched(long userId);
}
