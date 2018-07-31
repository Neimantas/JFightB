import main.Models.CONS.FighterStatus;
import main.Services.IDataBase;
import main.Services.Impl.Crud;
import main.Services.Impl.DataBase;

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
    public void ReadyStatus() {
        assertTrue(FighterStatus.SUCCESS!=null);
        System.out.println(FighterStatus.SUCCESS);
        System.out.println(FighterStatus.FAILURE);
    }

//    @org.junit.Test
//    public void getReadyToFightUsersExceptPrimaryUser() {
//        FighterService fighterService = new FighterService();
//        assertTrue(fighterService.getReadyToFightUsersExceptPrimaryUser()!=null);
//    }

//    @org.junit.Test
//    public void getReadyToFightUserById() {
//    }

//    @org.junit.Test
//    public void moveUserToReadyTable() {
//    }

}
