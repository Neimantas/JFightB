package main.Services;

import main.Models.DTO.DBqueryDTO;

public interface ICrud {
    DBqueryDTO create(String create);
    DBqueryDTO read(String read);
    DBqueryDTO update(String update);
    DBqueryDTO delete(String delete);
}
