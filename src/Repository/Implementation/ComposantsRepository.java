package Repository.Implementation;

import Entity.Composants;
import Entity.MainDœuvre;
import Repository.Interface.ComposantsRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ComposantsRepository implements ComposantsRepositoryInterface {


    private final Connection connection;

    public ComposantsRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Composants> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Composants> findAll() {
        return List.of();
    }

    @Override
    public Composants save(Composants composant) {
        return null;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void update(Composants composant, String column, String value) {

    }
}
