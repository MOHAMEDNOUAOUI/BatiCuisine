package Repository.Interface;

import Entity.Devis;

import java.util.List;
import java.util.Optional;

public interface DevisRepositoryInterface {


    Optional<Devis> findById(int id);
    List<Devis> findAll();
    Devis save(Devis devis);
    void deleteById(int id);
    void update(Devis devis, String column, String value);


}
