package Repository.Interface;

import Entity.Projets;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjetsRepositoryInterface {

    Projets save(Projets projet) throws SQLException;

    Optional<Projets> findById(UUID id) throws SQLException;

    List<Projets> findAll() throws SQLException;

    void update(Projets projet, String column, String value) throws SQLException;

    void deleteById(UUID id);


}
