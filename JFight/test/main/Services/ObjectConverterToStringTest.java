package main.Services;

import main.Models.BL.TurnOutcomeModel;
import main.Models.CONS.FightStatus;
import main.Services.Helpers.ObjectConverterToString;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ObjectConverterToStringTest {

    @Test
    public void listOfObjectsToStringList() {
//        TurnOutcomeModel model1 = new TurnOutcomeModel();
//        model1.userId = 1;
//        model1.round = 2;
//        model1.fightStatus = FightStatus.FIGHTING;
//        TurnOutcomeModel model2 = new TurnOutcomeModel();
//        model2.userId = 1;
//        model2.round = 3;
//        model2.fightStatus = FightStatus.LOSER;
//        List<Map<String, String>> list = ObjectConverterToString.convertList(Arrays.asList(model1, model2));
//        for (int i = 0; i < list.size(); i++) {
//            for (Map.Entry<String, String> item: list.get(i).entrySet()) {
//                System.out.println(String.valueOf(item));
//            }
//            System.out.println("====================");
//        }

        List<Long> userChallenges = Arrays.asList(1L,2L,3L,4L,5L);
        List<Map<String, String>> list2 = ObjectConverterToString.convertList(Arrays.asList(1L,2L,3L,4L,5L));
        for (int i = 0; i < list2.size(); i++) {
            for (Map.Entry<String, String> item: list2.get(i).entrySet()) {
                System.out.println(String.valueOf(item));
            }
            System.out.println("====================");
        }
    }

    @Test
    public void objectToStringMap() {
        TurnOutcomeModel model = new TurnOutcomeModel();
        model.userId = 1;
        model.round = 2;
        model.fightStatus = FightStatus.FIGHTING;
        Map<String, String> map = ObjectConverterToString.convertObject(model);
        for (Map.Entry<String, String> item: map.entrySet()) {
            System.out.println(String.valueOf(item));
        }
    }
}