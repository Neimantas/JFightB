package main.Services;

import main.Models.BL.TurnOutcomeModel;
import main.Models.BL.TurnStatsModel;

public interface IFightService {
    TurnOutcomeModel calculateTurnOutcome(TurnStatsModel model);
}