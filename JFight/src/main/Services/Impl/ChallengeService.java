package main.Services.Impl;

import main.Models.BL.IssuedChallenges;
import main.Models.DAL.ChallengeDAL;
import main.Models.DAL.FightLogDAL;
import main.Models.DAL.ReadyToFightDAL;
import main.Models.DTO.*;
import main.Services.IChallenge;
import main.Services.IHigherService;

import java.util.ArrayList;
import java.util.List;

public class ChallengeService implements IChallenge {

    private IHigherService hs = new HigherService();
    private DBqueryDTO dto;

    // Adds Player to ReadyToFight table
    @Override
    public boolean addPlayerToReadyToFight(long userId, String username) {
        boolean success = checkIfUserIsInReadyToFight(userId);
        if (success)
            dto = hs.insertUserToReadyToFightTable(new ReadyToFightDAL(userId, username));
            return true;
        }

        return false;
    }

    private boolean checkIfUserIsInReadyToFight(long userId) {
        return hs.getUserFromReadyToFightByUserId(userId).success;
    }

    @Override
    public boolean checkIfUsersChallengedEachOther(long userId){
        ChallengeDTO challengeDTO = hs.checkIfTwoUsersChallengedEachOther(userId);
        return challengeDTO.success && challengeDTO.list.size() > 0;
    }

    @Override
    public boolean checkIfUserIsFighting(long userId) {
        return hs.getFightByUserId(userId).success;
    }


//    @Override
//    public FightDTO checkIfUserIsFighting(long userId) {
//        FightDTO fightDTO = hs.getFightByUserId(userId);
//
//        if (fightDTO.success) {
//            long oppId = fightDTO.dal.userId1 != userId ? fightDTO.dal.userId1 : fightDTO.dal.userId2;
//            DBqueryDTO dto = hs.deleteMatchedPlayersFromChallenge(userId, oppId);
//
//            if (dto.success) {
//                return fightDTO;
//            } else {
//                return new FightDTO(false, dto.message, null);
//            }
//        }
//
//        return fightDTO;
//    }

    @Override
    public FightDTO createFightForMatchedPlayers(ChallengeDAL dal) {
        FightDTO fightDTO = wasNewFightAlreadyCreated(dal.userId);

        // TODO use enums here
        if (!fightDTO.success && fightDTO.message.equals("No fight has been found.")) {
            // Fight has not been created so we move create new Fight and remove users from Challenge, ReadyTOFight tables
            DBqueryDTO dto = hs.deleteMatchedPlayersFromChallenge(dal.userId, dal.opponentId);
            if (!dto.success) {
                // TODO log error message here
                return new FightDTO(false, dto.message, null);
            }

            dto = hs.deleteUserAndOpponentFromReadyToFight(dal.userId, dal.opponentId);
            if (!dto.success) {
                // TODO log error message here
                return new FightDTO(false, dto.message, null);
            }

            dto = hs.insertNewFight(dal);

            if (dto.success) {
                if (insertZeroRoundStatsBeforeFight(fightDTO)) {
                    return fightDTO;
                }
            }

        }
        // DB has crashed or some other error
        return new FightDTO(false, fightDTO.message, null);
    }

    @Override
    public IssuedChallengesDTO getIssuedChallenges(long userId) {
        ChallengeDTO challengeDTO = hs.getAllIssuedChallengesByUserId(userId);
        if (!dto.success) {
            return new IssuedChallengesDTO(false, "", null);
        }

        List<Long> userChallenges = new ArrayList<>();
        List<Long> oppChallenges = new ArrayList<>();
        challengeDTO.list.forEach(challenge -> {
            if (challenge.userId == userId) {
                userChallenges.add(challenge.opponentId);
            } else {
                oppChallenges.add(challenge.userId);
            }
        });

        return new IssuedChallengesDTO(true, "", new IssuedChallenges(userId, null, userChallenges, oppChallenges));
    }

    @Override
    public ReadyToFightDTO getReadyToFightUsersExceptPrimaryUser(long userId) {
        ReadyToFightDTO readyToFightDTO = hs.getAllReadyToFightUsers();

        if (readyToFightDTO.success) {
            for (int i = 0; i < readyToFightDTO.list.size(); i++) {
                if (readyToFightDTO.list.get(i).userId == userId) {
                    readyToFightDTO.list.remove(i);
                    break;
                }
            }
        }

        return readyToFightDTO;
    }

    @Override
    public boolean submitChallenges(List<ChallengeDAL> dalList) {
        for (ChallengeDAL dal : dalList) {
            dto = hs.insertChallengedPlayers(dal);
            if (!dto.success) {
                // TODO we should use a logger here to log any errors
                return false;
            }
        }
        return true;
    }

    private boolean insertZeroRoundStatsBeforeFight(FightDTO fightDTO) {
        // TODO we need to get stats from user Character table (not implemented)
        int hp = 10;
        int round = 0; //stats before fight

        FightLogDAL fightLog = new FightLogDAL();
        fightLog.userId = fightDTO.dal.userId1;
        fightLog.fightId = fightDTO.dal.fightId;
        fightLog.round = round;
        fightLog.hp = hp;

        if (!hs.insertTurnStats(fightLog).success) {
            // TODO log error
            return false;
        }

        fightLog.userId = fightDTO.dal.userId2;

        if (!hs.insertTurnStats(fightLog).success) {
            // TODO log error
            return false;
        }

        return true;
    }

    private FightDTO wasNewFightAlreadyCreated(long userId) {
        FightDTO fightDTO = hs.getFightByUserId(userId);

        if (fightDTO.success && fightDTO.dal != null) {
            return new FightDTO(true, "Fight has been already created.", fightDTO.dal);
        } else if (fightDTO.success){
            return new FightDTO(false, "No fight has been found.", null);
        }

        // DB has crashed
        return new FightDTO(false, dto.message, null);
    }
}
