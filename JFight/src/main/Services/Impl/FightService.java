package main.Services.Impl;

import main.Models.BL.TurnOutcomeModel;
import main.Models.BL.TurnStatsModel;
import main.Models.CONS.BodyParts;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.FightDTO;
import main.Services.IFightService;
import main.Services.IHigherService;

import java.util.List;

public class FightService implements IFightService {

    IHigherService hs = new HigherService();

    private DBqueryDTO sendFightStats(TurnStatsModel model) {
        return hs.insertTurnStats(model);
    }

    private DBqueryDTO checkForOpponent(TurnStatsModel model) {
        DBqueryDTO dto = hs.checkForFightRecordByIdAndRound(model);
        // TODO we will need a timeout counter if we cannot get result or opponent leaves
        while (!dto.isSuccess()) {
            dto = hs.checkForFightRecordByIdAndRound(model);
        }
        return dto;
    }

    public TurnOutcomeModel calculateTurnOutcome(TurnStatsModel userModel) {
        FightDTO fightDTO = hs.getFightByUserId(userModel.userId);
        // TODO fix the null part once we can
        if (!fightDTO.isSuccess()) {
            return  null;
        }
        userModel.fightId = fightDTO.getDal().getFightId();
        DBqueryDTO dto = sendFightStats(userModel);
        // TODO fix the null part once we can
        if (!dto.isSuccess()) {
            return null;
        }
        // TODO handle timeout
        dto = checkForOpponent(userModel);
        TurnStatsModel opponent = new TurnStatsModel();
        List<List<Object>> list = dto.getList();
        for (int i = 0; i < list.size(); i++) {
            List<Object> columns = list.get(i);
            if (Integer.parseInt(columns.get(1).toString()) != userModel.userId) {
                opponent.fightId = Integer.parseInt(columns.get(0).toString());
                opponent.userId = Integer.parseInt(columns.get(1).toString());
                opponent.att1 = BodyParts.valueOf(columns.get(2).toString());
                opponent.att2 = BodyParts.valueOf(columns.get(3).toString());
                opponent.def1 = BodyParts.valueOf(columns.get(4).toString());
                opponent.def2 = BodyParts.valueOf(columns.get(5).toString());
                opponent.hp = Integer.parseInt(columns.get(6).toString());
                opponent.round = Integer.parseInt(columns.get(7).toString());
                break;
            }
        }
        return calculateOutcome(userModel, opponent);
    }

    private TurnOutcomeModel calculateOutcome(TurnStatsModel user, TurnStatsModel opponent) {
        TurnOutcomeModel model = new TurnOutcomeModel();
        // TODO in the future damage variable will probably change
        int damage = 1;
        // User outcome
        int attacksReceivedUser = 0;
        if (user.def1 != opponent.att1 && user.def2 != opponent.att1 )
            attacksReceivedUser++;
        else if (user.def1 != opponent.att2 && user.def2 != opponent.att2)
            attacksReceivedUser++;
        model.userHp = user.hp - (attacksReceivedUser * damage);
        // Opponent outcome
        int attacksReceivedOpp = 0;
        if (opponent.def1 != user.att1 && opponent.def2 != user.att1)
            attacksReceivedOpp++;
        else if (opponent.def1 != user.att2 && opponent.def2 != user.att2)
            attacksReceivedOpp++;
        model.oppHp = opponent.hp - (attacksReceivedOpp * damage);
        model.round = user.round + 1;
        // TODO create FIGHT LOG
        return model;
    }
}
