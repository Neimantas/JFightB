import Services.IDataBase;
import Services.Impl.Crud;
import Services.Impl.DataBase;
import static org.junit.Assert.assertTrue;

public class Test {

    @org.junit.Test
    public void DbgetConnectionTest() throws Exception {
        IDataBase dataBase = new DataBase();
        assertTrue(dataBase.getConnection()!=null);

    }
//    @org.junit.Test
//    public void create() {
//    }

    @org.junit.Test
    public void readDbTest() throws Exception {

        String query = "select Email from [User]";
        Crud crud = new Crud();
        assertTrue(crud.read(query)!=null);
    }

//    @org.junit.Test
//    public void update() {
//    }

//    @org.junit.Test
//    public void delete() {
//    }



}
