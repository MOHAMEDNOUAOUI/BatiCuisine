package Repository.Implementation;

import Entity.Materiaux;
import Repository.Interface.MateriauxRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MateriauxRepository implements MateriauxRepositoryInterface {


    private final Connection connection;

    public MateriauxRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Materiaux save(Materiaux materiaux) {
        String sql = "INSERT INTO matériaux (nom, tauxTVA, typeComposant ,coutunitaire,quantity,couttransport,coefficientqualite, projet_id ) VALUES (?, ?, ? ,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, materiaux.getNom());
            preparedStatement.setDouble(2, materiaux.getTauxTVA());
            preparedStatement.setString(3, materiaux.getTypeComposant());
            preparedStatement.setDouble(4,materiaux.getCoutUnitaire());
            preparedStatement.setDouble(5,materiaux.getQuantite());
            preparedStatement.setDouble(6,materiaux.getCoutTransport());
            preparedStatement.setDouble(7,materiaux.getCoefficientQualite());
            preparedStatement.setObject(8 , materiaux.getProjet().getId());
            preparedStatement.executeUpdate();

            return materiaux;

        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }




    @Override
    public Optional<Materiaux> findById(UUID id) throws SQLException {
        Materiaux materiaux = new Materiaux();

        String sql= "SELECT * from matériaux where id_composants = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                materiaux.setId(resultSet.getObject(1 , UUID.class));
                materiaux.setNom(resultSet.getString(2));
                materiaux.setTauxTVA(resultSet.getDouble(3));
                materiaux.setTypeComposant(resultSet.getString(4));
                //project here
                materiaux.setCoutUnitaire(resultSet.getDouble(6));
                materiaux.setQuantite(resultSet.getDouble(7));
                materiaux.setCoutTransport(resultSet.getDouble(8));
                materiaux.setCoefficientQualite(resultSet.getDouble(9));
            }
        }
        return materiaux != null ? Optional.of(materiaux) : Optional.empty();
    }

    @Override
    public List<Materiaux> findAll() {
        List<Materiaux> materiauxs = new ArrayList<>();

        String sql= "SELECT * from matériaux";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()){
                Materiaux materiaux = new Materiaux();
                materiaux.setId(resultSet.getObject(1 , UUID.class));
                materiaux.setNom(resultSet.getString(2));
                materiaux.setTauxTVA(resultSet.getDouble(3));
                materiaux.setTypeComposant(resultSet.getString(4));
                materiaux.setCoutUnitaire(resultSet.getDouble(6));
                materiaux.setQuantite(resultSet.getDouble(7));
                materiaux.setCoutTransport(resultSet.getDouble(8));
                materiaux.setCoefficientQualite(resultSet.getDouble(9));

                materiauxs.add(materiaux);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return materiauxs;
    }


    @Override
    public void deleteById(UUID id) throws SQLException {
                String sql = "DELETE FROM matériaux where id_composants = ?";
                try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                    preparedStatement.setObject(1, id);
                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows > 0) {
                        System.out.println("Material with ID " + id + " was deleted successfully.");
                    } else {
                        System.out.println("Material with ID " + id + " was not found.");
                    }
                }
    }

    @Override
    public void update(Materiaux materiaux, String column, String value) {
        String sql = "UPDATE matériaux SET " + column + " = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if (column.equals("tauxtva") || column.equals("countunitaire") || column.equals("quantity") || column.equals("couttransport") || column.equals("coefficientqualite")) {
                preparedStatement.setBoolean(1, Boolean.parseBoolean(value));
            }
            else {
                preparedStatement.setString(1, value);
            }

            preparedStatement.setObject(2 , materiaux.getId());
            int affectedrows = preparedStatement.executeUpdate();

            if (affectedrows > 0){
                System.out.println("material with ID " + materiaux.getId() + " was updated successfully.");
            }

        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
