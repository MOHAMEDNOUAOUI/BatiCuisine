package Repository.Interface;

import Entity.Materiaux;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MateriauxRepositoryInterface {


    Optional<Materiaux> findById(UUID id) throws SQLException;
    List<Materiaux> findAll();
    Materiaux save(Materiaux materiaux);
    void deleteById(UUID id) throws SQLException;
    void update(Materiaux materiaux ,String column,String value);

}
