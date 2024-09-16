package Repository.Interface;

import Entity.Composants;

import java.util.List;
import java.util.Optional;

public interface ComposantsRepositoryInterface {


    Optional<Composants> findById(int id);
    List<Composants> findAll();
    Composants save(Composants composant);
    void deleteById(int id);
    void update(Composants composant , String column , String value);

}
