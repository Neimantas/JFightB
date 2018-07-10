package Services;



import Models.DTO.DBqueryDTO;

import java.sql.SQLException;


public interface ICrud {


    DBqueryDTO create(String create);

    DBqueryDTO read(String read) throws SQLException;

    DBqueryDTO update(String update);

    DBqueryDTO delete(String delete);


}
