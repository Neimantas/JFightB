import main.Models.CONS.FighterStatus;
import main.Models.DTO.DBqueryDTO;
import main.Models.DTO.RegisterDTO;
import main.Services.IDataBase;
import main.Services.Impl.Crud;
import main.Services.Impl.DataBase;
import main.Services.Impl.HigherService;
import main.Services.Impl.RegisterService;

import static org.junit.Assert.assertTrue;

public class Test {

    @org.junit.Test
    public void DbgetConnectionTest() throws Exception {
        IDataBase dataBase = new DataBase();
        assertTrue(dataBase.getConnection() != null);
    }

    @org.junit.Test
    public void ReadDbTest() throws Exception {

        String query = "select Email from [User]";
        Crud crud = new Crud();
        assertTrue(crud.read(query) != null);
    }

    @org.junit.Test
    public void ReadyStatus() {
        assertTrue(FighterStatus.SUCCESS != null);
        System.out.println(FighterStatus.SUCCESS);
        System.out.println(FighterStatus.FAILURE);
    }

    @org.junit.Test
    public void validate() {
        String query = "select Email, [Password] from [User] where Email= 'test@test.lt' and Password='test'";

        DBqueryDTO dBqueryDTO = new Crud().read(query);
        System.out.println( dBqueryDTO.list);
        System.out.println( dBqueryDTO.list.isEmpty());

    }

//    @org.junit.Test
//    public void find() {
//        HigherService hs = new HigherService();
//
//     DBqueryDTO dto = new
//    }
}

