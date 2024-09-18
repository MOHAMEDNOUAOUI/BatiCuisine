package Repository.Interface;

import Entity.Clients;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientsRepositoryInterface {


    Clients save(Clients client);
    Optional<Clients> findById(UUID id) throws SQLException;
    List<Clients> findAll();
    void deleteById(UUID id);
    void update(Clients client , String column , String value);


}
