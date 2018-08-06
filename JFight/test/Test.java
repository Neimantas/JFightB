//import main.Models.CONS.FighterStatus;
//import main.Services.IDataBase;
//import main.Services.Impl.DataBase;
//
//import static org.junit.Assert.assertTrue;
//
//public class Test {
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.FightLogDTO;
import main.Services.ICrud;
import main.Services.IHigherService;
import main.Services.Impl.Crud;
import main.Services.Impl.HigherService;

import java.util.ArrayList;
import java.util.List;

    @org.junit.Test
    public void HigherProcedure(){
        IHigherService hs = new HigherService();
        FightLogDAL fightLog = new FightLogDAL();
        fightLog.userId = 19;
        fightLog.fightId = "386f1bdb-cab8-40f1-a351-708ca8b8ce22";
        fightLog.round = 0;
        FightLogDTO dto = hs.getFightLogByIdAndRound(fightLog);

        if (dto.success) {
            System.out.println("SUCCESS");
            System.out.println(dto.list.get(0).hp);
        } else {
            System.out.println(dto.message);
            System.out.println(dto.list.get(0).hp);
        }
    }

    @org.junit.Test
    public void TestTHis(){
        IHigherService hs = new HigherService();
        int hp = 10;
        int round = 0; //stats before fight

        FightLogDAL fightLog = new FightLogDAL();
        fightLog.userId = 19;
        fightLog.fightId = "386f1bdb-cab8-40f1-a351-708ca8b8ce22";
        fightLog.round = round;
        fightLog.hp = hp;
        DBqueryDTO dto = hs.insertTurnStats(fightLog);
        if (dto.success) {
            System.out.println("SUCCESS");
        } else {
            System.out.println(dto.message);
        }
    }

    @org.junit.Test
    public void TestProcedure() {
        ICrud crud = new Crud();
        DBQueryModel dbQueryModel = new DBQueryModel();
        ProcedureModel procedure = new ProcedureModel();
        procedure.name = Procedures.checkIfTwoUsersChallengedEachOther;
        List<Pair<String, Object>> params = new ArrayList<>();
        params.add(new Pair<>("userId", 19));
        procedure.params = params;
        dbQueryModel.procedure = procedure;

        DBqueryDTO<ChallengeDAL> dto = crud.read(dbQueryModel, ChallengeDAL.class);
        System.out.println(dto.list.size());
        if (dto.success && !dto.list.isEmpty()) {
            for (ChallengeDAL dal : dto.list) {
                System.out.print(dal.userId + " -- OppName: " + dal.opponentId);
                System.out.println();
            }
        }
        assertTrue(dto.success);
    }

//
//    @org.junit.Test
//    public void DbgetConnectionTest() throws Exception {
//        IDataBase dataBase = new DataBase();
//        assertTrue(dataBase.getConnection() != null);
//    }
//
////    @org.junit.Test
////    public void ReadDbTest() throws Exception {
////
////        String query = "select Email from [User]";
////        Crud crud = new Crud();
////        assertTrue(crud.read(query)!=null);
////    }
//
//    @org.junit.Test
//    public void ReadyStatus() {
//        assertTrue(FighterStatus.SUCCESS != null);
//        System.out.println(FighterStatus.SUCCESS);
//        System.out.println(FighterStatus.FAILURE);
//    }
//
////    @org.junit.Test
////    public void getReadyToFightUsersExceptPrimaryUser() {
////        FighterService fighterService = new FighterService();
////        assertTrue(fighterService.getReadyToFightUsersExceptPrimaryUser()!=null);
////        DBqueryDTO dBqueryDTO = new Crud().read(query);
////        System.out.println( dBqueryDTO.list);
////        System.out.println( dBqueryDTO.list.isEmpty());
////    }
//
////    @org.junit.Test
////    public void find() {
////        HigherService hs = new HigherService();
////
////     DBqueryDTO dto = new
////    }
//}
//
