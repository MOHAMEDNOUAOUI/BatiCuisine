package Repository.Interface;

import Entity.Devis;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DevisRepositoryInterface {


    Optional<Devis> findById(UUID id) throws SQLException;
    List<Devis> findAll() throws SQLException;
    Devis save(Devis devis) throws SQLException;
    void deleteById(UUID id);
    void update(Devis devis, String column, String value);


}
