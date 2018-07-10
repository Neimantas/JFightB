

import Services.IDataBase;
import Services.Impl.DataBase;

import static org.junit.Assert.assertTrue;

public class Test {

    @org.junit.Test
    public void DbgetConnection() throws Exception {
        IDataBase dataBase = new DataBase();
        assertTrue(dataBase.getConnection()!=null);
    }

}
