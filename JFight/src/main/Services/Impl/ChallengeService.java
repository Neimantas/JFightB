package main.Services.Impl;

import main.Models.BL.IssuedChallenges;
import main.Models.DAL.ChallengeDAL;
import main.Models.DTO.ChallengeDTO;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.FightDTO;
import main.Models.DTO.IssuedChallengesDTO;
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
        if (!dto.isSuccess()) {
            return new ChallengeDTO(false, "No matches found", null);
        }
        List<Object> list = dto.getList().get(0);
        List<ChallengeDAL> dalList = Arrays.asList(new ChallengeDAL(Long.parseLong(list.get(0).toString()),
                                Long.parseLong(list.get(1).toString()),
                                Long.parseLong(list.get(2).toString())));
        return new ChallengeDTO(true, "", dalList);
    }

    public FightDTO createFightForMatchedPlayers(ChallengeDAL dal) {
        dto = hs.moveUsersToFight(dal);
        FightDTO fightDTO;
        if (dto.isSuccess()) {
            fightDTO = hs.getFightByUserId(dal.userId);
            if (fightDTO.isSuccess()) {
                return fightDTO;
            }
        }
        return new FightDTO(false, "", null);
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
}
