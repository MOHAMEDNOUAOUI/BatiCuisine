package Repository.Interface;

import Entity.Composants;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComposantsRepositoryInterface {


    Optional<Composants> findById(UUID id);
    List<Composants> findAll();
    Composants save(Composants composant);
    void deleteById(UUID id);
    void update(Composants composant , String column , String value);

}
