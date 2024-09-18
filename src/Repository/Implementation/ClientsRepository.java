package Repository.Implementation;

import Entity.Clients;
import Entity.Projets;
import Repository.Interface.ClientsRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import Enum.*;

public class ClientsRepository implements ClientsRepositoryInterface {

    private final Connection connection;

    public ClientsRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Clients save(Clients client) {

        try{
            String sql = "INSERT INTO Clients (nom, address, telephone, estProfessionel , statut_client) VALUES (?, ?, ?, ? , 'ACTIVE')";
            PreparedStatement preparedStatement = connection.prepareStatement(sql , PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,client.getNom());
            preparedStatement.setString(2 , client.getAddress());
            preparedStatement.setString(3 , client.getTelephone());
            preparedStatement.setBoolean(4,client.isEstProfessionel());
             preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Optional<Clients> findById(UUID id) throws SQLException {


        Map<UUID, Clients> Clientchecker = new HashMap<>();
        Clients client = new Clients();

        String sql  = "select * from clients left join projets on projets.clients_id_reference = clients.id_clients WHERE clients.id_clients = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {


                while (resultSet.next()) {
                    if(!Clientchecker.containsKey(resultSet.getObject("id_clients" , UUID.class))) {

                        client.setId(resultSet.getObject("id_clients" , UUID.class));
                        client.setNom(resultSet.getString("nom"));
                        client.setAddress(resultSet.getString("adresse"));
                        client.setTelephone(resultSet.getString("telephone"));
                        client.setEstProfessionel(resultSet.getBoolean("estProfessionel"));
                        Clientchecker.put(resultSet.getObject("id_clients" , UUID.class), client);
                    }

                    UUID projetsid = resultSet.getObject("id_projets" , UUID.class);
                    if(projetsid != null) {
                        Projets projets = new Projets();
                        projets.setId(projetsid);
                        projets.setCout_total(resultSet.getDouble("cout_total"));
                        projets.setMarge_benificiare(resultSet.getDouble("marge_benificiare"));
                        projets.setProjects_name(resultSet.getString("projects_name"));
                        projets.setEtat_projet(Etat_Projet.valueOf(resultSet.getString("etat_projet")));

                        client.setProjetsList(projets);
                    }

                }
            }
        }

        return client != null ? Optional.of(client) : Optional.empty();
    }

    @Override
    public List<Clients> findAll() {
        String sql = "SELECT * FROM clients LEFT JOIN projets ON projets.clients_id_reference = clients.id_clients";

        Map<UUID, Clients> clientChecker = new HashMap<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                UUID clientId = resultSet.getObject("id_clients" , UUID.class);


                Clients client = clientChecker.get(clientId);
                if (client == null) {
                    client = new Clients();
                    client.setId(clientId);
                    client.setNom(resultSet.getString("nom"));
                    client.setAddress(resultSet.getString("adresse"));
                    client.setTelephone(resultSet.getString("telephone"));
                    client.setEstProfessionel(resultSet.getBoolean("estProfessionel"));
                    clientChecker.put(clientId, client);
                }

                UUID projetsId = resultSet.getObject("id_projets" , UUID.class);
                if (projetsId != null) {
                    Projets projets = new Projets();
                    projets.setId(projetsId);
                    projets.setCout_total(resultSet.getDouble("cout_total"));
                    projets.setMarge_benificiare(resultSet.getDouble("marge_benificiare"));
                    projets.setProjects_name(resultSet.getString("projects_name"));
                    projets.setEtat_projet(Etat_Projet.valueOf(resultSet.getString("etat_projet")));

                    client.setProjetsList(projets);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new ArrayList<>(clientChecker.values());
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "UPDATE clients set statut_client = 'DELETED' WHERE id_clients = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Client with ID " + id + " was deleted successfully.");
            } else {
                System.out.println("Client with ID " + id + " was not found.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting client with ID " + id, e);
        }
    }

    @Override
    public void update(Clients client , String column , String value) {
        String sql = "UPDATE clients SET " + column + " = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if (column.equals("estProfessionel")) {
                preparedStatement.setBoolean(1, Boolean.parseBoolean(value));
            }
            else {
                preparedStatement.setString(1, value);
            }

            preparedStatement.setObject(2 , client.getId());
            int affectedrows = preparedStatement.executeUpdate();

            if (affectedrows > 0){
                System.out.println("Client with ID " + client.getId() + " was updated successfully.");
            }

        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
