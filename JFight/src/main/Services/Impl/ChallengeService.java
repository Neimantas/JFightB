package main.Services.Impl;

import main.Models.BL.IssuedChallenges;
import main.Models.DAL.ChallengeDAL;
import main.Models.DAL.FightDAL;
import main.Models.DTO.*;
import main.Services.IChallenge;
import main.Services.IHigherService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChallengeService implements IChallenge {

    private IHigherService hs = new HigherService();
    private DBqueryDTO dto;

    public DBqueryDTO submitChallenges(List<ChallengeDAL> dalList) {
        dto = new DBqueryDTO(false, null, null);
        for (ChallengeDAL dal : dalList) {
            dto = hs.insertIntoChallenge(dal);
        }
        return dto;
    }

    public ChallengeDTO checkForMatches(long userId){
        dto = hs.checkIfTwoUsersChallengedEachOther(userId);
        if (dto.isSuccess()) {
            if (dto.getList().size() > 0) {
                List<Object> list = dto.getList().get(0);
                List<ChallengeDAL> dalList = Arrays.asList(new ChallengeDAL(Long.parseLong(list.get(0).toString()),
                        Long.parseLong(list.get(1).toString()),
                        Long.parseLong(list.get(2).toString())));
                return new ChallengeDTO(true, "", dalList);
            }
            return new ChallengeDTO(false, "No matches found.", null);
        }
        return new ChallengeDTO(false, dto.getMessage(), null);
    }

    private FightDTO wasNewFightAlreadyCreated(long userId) {
        dto = hs.checkIfFightIsAlreadyCreated(userId);
        if (dto.isSuccess()) {
            if (dto.getList().size() > 0) {
                FightDAL dal = new FightDAL();
                dal.setFightId(Long.parseLong(dto.getList().get(0).toString()));
                dal.setUserId1(Long.parseLong(dto.getList().get(1).toString()));
                dal.setUserId2(Long.parseLong(dto.getList().get(2).toString()));
                return new FightDTO(true, "Fight has been already created.", dal);
            } else {
                return new FightDTO(true, "No fight found", null);
            }
        }
        // DB has crashed
        return new FightDTO(false, dto.getMessage(), null);
    }

    public FightDTO createFightForMatchedPlayers(ChallengeDAL dal) {
        FightDTO fightDTO = wasNewFightAlreadyCreated(dal.userId);
        if (fightDTO.isSuccess()) {
            if (fightDTO.getMessage().equals("No fight found")) {
                dto = hs.moveUsersToFight(dal);
                if (dto.isSuccess()) {
                    fightDTO = hs.getFightByUserId(dal.userId);
                    if (fightDTO.isSuccess()) {
                        return fightDTO;
                    }
                }
            } else {
                // There is already a Fight created for this user, since there already is a record that is not older than 10s
                return fightDTO;
            }
        }
        // DB crash
        return new FightDTO(false, dto.getMessage(), null);
    }

    public IssuedChallengesDTO getIssuedChallenges(long userId) {
        dto = hs.getAllIssuedChallengesByUserId(userId);
        if (!dto.isSuccess()) {
            return null;
        }
        List<List<Object>> rows = dto.getList();
        List<Long> userChallenges = new ArrayList<>();
        List<Long> oppChallenges = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            List<Object> columns = rows.get(i);
            if (Long.parseLong(columns.get(1).toString()) == userId) {
                userChallenges.add(Long.parseLong(columns.get(2).toString()));
            } else {
                oppChallenges.add(Long.parseLong(columns.get(1).toString()));
            }
        }
        return new IssuedChallengesDTO(true, "", new IssuedChallenges(userId, null, userChallenges, oppChallenges));
    }

    public ReadyToFightDTO getAllReadyToFightUsersId(long userId) {
        return hs.getAllReadyToFightUsersId(userId);
    }
}
