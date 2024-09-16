package Repository.Interface;

import Entity.Clients;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ClientsRepositoryInterface {


    Clients save(Clients client);
    Optional<Clients> findById(int id) throws SQLException;
    List<Clients> findAll();
    void deleteById(int id);
    void update(Clients client , String column , String value);


}
