package Repository.Implementation;

import Entity.Clients;
import Entity.Devis;
import Entity.Projets;
import Repository.Interface.DevisRepositoryInterface;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import Enum.*;
import jdk.jshell.spi.SPIResolutionException;

public class DevisRepository implements DevisRepositoryInterface {

    private  final Connection connection;

    public DevisRepository(Connection connection) {
        this.connection = connection;
    }



    @Override
    public Devis save(Devis devis) throws SQLException {
        String sql = "insert into devis (montantestime , dateemission , datevalidite , accepte , projet_id) values(?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql , PreparedStatement.RETURN_GENERATED_KEYS)){
            preparedStatement.setDouble(1,devis.getMontantEstime());
            preparedStatement.setDate(2, Date.valueOf(devis.getDateEmission()));
            preparedStatement.setDate(3, Date.valueOf(devis.getDateValidite()));
            preparedStatement.setBoolean(4,devis.isAccepte());
            preparedStatement.setInt(5,devis.getProjet().getId());

            int affectedrows = preparedStatement.executeUpdate();
            if(affectedrows>0){
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        devis.setId_Devis(generatedKeys.getInt(1));
                    }
                }
            }
        }

        return null;
    }




    @Override
    public Optional<Devis> findById(int id) throws SQLException {
        String sql = "select * from devis join projets on projets.id_projets = devis.projet_id\n" +
                "JOIN Clients on Clients.id_clients = projets.clients_id_reference where devis.id_Devis = ?";

        Devis devis = new Devis();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){

                    devis.setId_Devis(resultSet.getInt(1));
                    devis.setMontantEstime(resultSet.getDouble(2));
                    devis.setDateEmission(resultSet.getDate(3).toLocalDate());
                    devis.setDateValidite(resultSet.getDate(4).toLocalDate());
                    devis.setAccepte(resultSet.getBoolean(5));

                    Projets projets = new Projets();
                    projets.setId(resultSet.getInt(6));
                    projets.setProjects_name(resultSet.getString(8));
                    projets.setMarge_benificiare(resultSet.getDouble(9));
                    projets.setCout_total(resultSet.getDouble(10));
                    projets.setEtat_projet(Etat_Projet.valueOf(resultSet.getString(11)));

                    Clients clients = new Clients();

                    clients.setId(resultSet.getInt(12));
                    clients.setNom(resultSet.getString(14));
                    clients.setAddress(resultSet.getString(15));
                    clients.setTelephone(resultSet.getString(16));
                    clients.setEstProfessionel(resultSet.getBoolean(17));
                    clients.setStatut_user(ClientsStatut.valueOf(resultSet.getString(18)));

                    projets.setClient(clients);
                    devis.setProjet(projets);
                }
            }
        }

        return devis != null ? Optional.of(devis) : Optional.empty();
    }

    @Override
    public List<Devis> findAll() throws SQLException {
        String sql = "select * from devis join projets on projets.id_projets = devis.projet_id\n" +
                "JOIN Clients on Clients.id_clients = projets.clients_id_reference";
        List<Devis> devisList = new ArrayList<>();
        Map<Integer , Projets> projetsMap = new HashMap<>();



        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery())
        {

            while (resultSet.next()) {
                Devis devis = new Devis();
                devis.setId_Devis(resultSet.getInt(1));
                devis.setMontantEstime(resultSet.getDouble(2));
                devis.setDateEmission(resultSet.getDate(3).toLocalDate());
                devis.setDateValidite(resultSet.getDate(4).toLocalDate());
                devis.setAccepte(resultSet.getBoolean(5));

                int projectId = resultSet.getInt(6);

                Projets projets = projetsMap.get(projectId);
                if(projets == null){
                    projets = new Projets();
                    projets.setId(projectId);
                    projets.setProjects_name(resultSet.getString(8));
                    projets.setMarge_benificiare(resultSet.getDouble(9));
                    projets.setCout_total(resultSet.getDouble(10));
                    projets.setEtat_projet(Etat_Projet.valueOf(resultSet.getString(11)));

                    Clients clients = new Clients();
                    clients.setId(resultSet.getInt(12));
                    clients.setNom(resultSet.getString(14));
                    clients.setAddress(resultSet.getString(15));
                    clients.setTelephone(resultSet.getString(16));
                    clients.setEstProfessionel(resultSet.getBoolean(17));
                    clients.setStatut_user(ClientsStatut.valueOf(resultSet.getString(18)));

                    projets.setClient(clients);

                    projetsMap.put(projectId, projets);
                }

                devis.setProjet(projets);
                devisList.add(devis);
            }


        }



        return devisList;
    }



    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM devis where id_devis = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Devis with ID " + id + " was deleted successfully.");
            } else {
                System.out.println("Devis with ID " + id + " was not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Devis devis, String column, String value) {
        String sql = "UPDATE devis SET " + column + " = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if (column.equals("montantestime")) {
                preparedStatement.setDouble(1, Double.parseDouble(value));
            } else if (column.equals("dateemission") || column.equals("datevalidite")) {
                preparedStatement.setDate(1, Date.valueOf(value));
            } else if (column.equals("accepte")) {
                preparedStatement.setBoolean(1, Boolean.parseBoolean(value));
            } else {
                preparedStatement.setString(1, value);
            }

            preparedStatement.setInt(2 , devis.getId_Devis());
            int affectedrows = preparedStatement.executeUpdate();

            if (affectedrows > 0){
                System.out.println("Main Doeuvre with ID " + devis.getId_Devis() + " was updated successfully.");
            }

        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
