package Services;

import Models.DTO.ReadyToFightDTO;

public interface IHigherService {
    ReadyToFightDTO getAllReadyToFightUsersId();
    ReadyToFightDTO getReadyToFightUserById(String query);
}
