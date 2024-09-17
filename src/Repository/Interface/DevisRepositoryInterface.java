package Repository.Interface;

import Entity.Devis;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DevisRepositoryInterface {


    Optional<Devis> findById(int id) throws SQLException;
    List<Devis> findAll() throws SQLException;
    Devis save(Devis devis) throws SQLException;
    void deleteById(int id);
    void update(Devis devis, String column, String value);


}
