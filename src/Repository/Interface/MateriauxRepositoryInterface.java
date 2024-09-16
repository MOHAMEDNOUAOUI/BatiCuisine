package Repository.Interface;

import Entity.Materiaux;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MateriauxRepositoryInterface {


    Optional<Materiaux> findById(int id) throws SQLException;
    List<Materiaux> findAll();
    Materiaux save(Materiaux materiaux);
    void deleteById(int id) throws SQLException;
    void update(Materiaux materiaux ,String column,String value);

}
