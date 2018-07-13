import main.Models.CONS.FighterStatus;
import main.Services.IDataBase;
import main.Services.Impl.Crud;
import main.Services.Impl.DataBase;
import main.Services.Impl.FighterService;

import static org.junit.Assert.assertTrue;

public class Test {

    @org.junit.Test
    public void DbgetConnectionTest() throws Exception {
        IDataBase dataBase = new DataBase();
        assertTrue(dataBase.getConnection()!=null);
    }

    @org.junit.Test
    public void ReadDbTest() throws Exception {

        String query = "select Email from [User]";
        Crud crud = new Crud();
        assertTrue(crud.read(query)!=null);
    }

    @org.junit.Test
    public void EnumReadyStatus() {
        assertTrue(FighterStatus.SUCCESS.Status()!=null);
        System.out.println(FighterStatus.SUCCESS.Status());
        System.out.println(FighterStatus.FAILURE.Status());
    }

    @org.junit.Test
    public void getAllReadyToFightUsersId() {
        FighterService fighterService = new FighterService();
        assertTrue(fighterService.getAllReadyToFightUsersId()!=null);
    }

//    @org.junit.Test
//    public void getReadyToFightUserById() {
//    }

//    @org.junit.Test
//    public void moveUserToReadyTable() {
//    }

}
