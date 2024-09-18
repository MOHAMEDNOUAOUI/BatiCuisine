package Repository.Implementation;

import Entity.MainDœuvre;
import Entity.Materiaux;
import Repository.Interface.MainDoeuvreRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MainDoeuvreRepository implements MainDoeuvreRepositoryInterface {

    private final Connection connection;

    public MainDoeuvreRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public MainDœuvre save(MainDœuvre mainDœuvre) {
        String sql = "INSERT INTO maindœuvre (nom, tauxTVA, typeComposant ,tauxhoraire , heurestravail ,productiviteouvrier , projet_id ) VALUES (?, ?, ? ,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, mainDœuvre.getNom());
            preparedStatement.setDouble(2, mainDœuvre.getTauxTVA());
            preparedStatement.setString(3, mainDœuvre.getTypeComposant());
            preparedStatement.setDouble(4,mainDœuvre.getTauxHoraire());
            preparedStatement.setDouble(5,mainDœuvre.getHeuresTravai());
            preparedStatement.setDouble(6,mainDœuvre.getProductiviteOuvrier());
            preparedStatement.setObject(7 , mainDœuvre.getProjet().getId());
            preparedStatement.executeUpdate();


            return mainDœuvre;

        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<MainDœuvre> findById(UUID id) throws SQLException {
       MainDœuvre mainDœuvre = new MainDœuvre();

        String sql= "SELECT * from maindœuvre where id_composants = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                mainDœuvre.setId(resultSet.getObject("id_composants" , UUID.class));
                mainDœuvre.setNom(resultSet.getString("nom"));
                mainDœuvre.setTauxTVA(resultSet.getDouble("tauxTVA"));
                mainDœuvre.setTypeComposant(resultSet.getString("typeComposant"));
                mainDœuvre.setTauxHoraire(resultSet.getDouble("tauxHoraire"));
                mainDœuvre.setHeuresTravail(resultSet.getDouble("heurestravail"));
                mainDœuvre.setProductiviteOuvrier(resultSet.getDouble("productiviteouvrier"));
                //project
            }
        }

        return mainDœuvre != null ? Optional.of(mainDœuvre) : Optional.empty();
    }

    @Override
    public List<MainDœuvre> findAll() throws SQLException {
        List<MainDœuvre> list = new ArrayList<>();
        String sql= "SELECT * from maindœuvre";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                MainDœuvre mainDœuvre = new MainDœuvre();
                mainDœuvre.setId(resultSet.getObject("id_composants" , UUID.class));
                mainDœuvre.setNom(resultSet.getString("nom"));
                mainDœuvre.setTauxTVA(resultSet.getDouble("tauxTVA"));
                mainDœuvre.setTypeComposant(resultSet.getString("typeComposant"));
                mainDœuvre.setTauxHoraire(resultSet.getDouble("tauxHoraire"));
                mainDœuvre.setHeuresTravail(resultSet.getDouble("heurestravail"));
                mainDœuvre.setProductiviteOuvrier(resultSet.getDouble("productiviteouvrier"));
                //project

                list.add(mainDœuvre);
            }
        }

        return list;
    }



    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM maindœuvre where id_composants = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setObject(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Main Doeuvre with ID " + id + " was deleted successfully.");
            } else {
                System.out.println("Main Doeuvre with ID " + id + " was not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(MainDœuvre mainDœuvre, String column, String value) {
        String sql = "UPDATE maindœuvre SET " + column + " = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if (column.equals("tauxtva") || column.equals("tauxhoraire") || column.equals("heurestravail") || column.equals("productiviteouvrier")) {
                preparedStatement.setBoolean(1, Boolean.parseBoolean(value));
            }
            else {
                preparedStatement.setString(1, value);
            }

            preparedStatement.setObject(2 , mainDœuvre.getId());
            int affectedrows = preparedStatement.executeUpdate();

            if (affectedrows > 0){
                System.out.println("Main Doeuvre with ID " + mainDœuvre.getId() + " was updated successfully.");
            }

        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
