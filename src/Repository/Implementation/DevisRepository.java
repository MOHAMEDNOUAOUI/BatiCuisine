package Repository.Implementation;

import Entity.Devis;
import Repository.Interface.DevisRepositoryInterface;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class DevisRepository implements DevisRepositoryInterface {

    private  final Connection connection;

    public DevisRepository(Connection connection) {
        this.connection = connection;
    }



    @Override
    public Devis save(Devis devis) {
        String sql = "insert into devis values(?,?,?,?)";
        return null;
    }




    @Override
    public Optional<Devis> findById(int id) {

        return Optional.empty();
    }

    @Override
    public List<Devis> findAll() {
        return List.of();
    }



    @Override
    public void deleteById(int id) {

    }

    @Override
    public void update(Devis devis, String column, String value) {

    }
}
