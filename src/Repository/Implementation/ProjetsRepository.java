package Repository.Implementation;

import Entity.Clients;
import Entity.Devis;
import Entity.Projets;
import Repository.Interface.ProjetsRepositoryInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import Enum.*;

public class ProjetsRepository implements ProjetsRepositoryInterface {

    private final Connection connection;

    public ProjetsRepository(Connection connection) {
        this.connection = connection;
    }





    @Override
    public Projets save(Projets projet) throws SQLException {
        String sql = "INSERT INTO projets (projects_name,marge_benificiare,cout_total,etat_projet,client_id_reference) VALUES(?,?,?,?,?)";
        try(PreparedStatement ps = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, projet.getProjects_name());
            ps.setDouble(2, projet.getMarge_benificiare());
            ps.setDouble(3, projet.getCout_total());
            ps.setString(4, projet.getEtat_projet().toString());
            ps.setInt(5,projet.getClient().getId());
            int affectedrows = ps.executeUpdate();
            if (affectedrows == 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        projet.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Optional<Projets> findById(int id) throws SQLException {
        String sql = "SELECT * FROM projets JOIN clients ON clients.id_clients = projets.clients_id_reference LEFT JOIN Devis on Devis.projet_id = projets.id_projets WHERE projets.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Projets projet = new Projets();
                    projet.setId(resultSet.getInt(1));
                    projet.setProjects_name(resultSet.getString(2));
                    projet.setMarge_benificiare(resultSet.getDouble(3));
                    projet.setCout_total(resultSet.getDouble(4));
                    projet.setEtat_projet(Etat_Projet.valueOf(resultSet.getString(5)));

                    int clientid = resultSet.getInt(6);
                    if (clientid > 0) {
                        Clients clients = new Clients();
                        clients.setId(clientid);
                        clients.setNom(resultSet.getString(7));
                        clients.setAddress(resultSet.getString(8));
                        clients.setTelephone(resultSet.getString(9));
                        clients.setEstProfessionel(resultSet.getBoolean(10));
                        clients.setStatut_user(ClientsStatut.valueOf(resultSet.getString(11)));
                        projet.setClient(clients);
                    }
                    while (resultSet.next()) {
                        int devisId = resultSet.getInt("id_devis");
                        if (devisId > 0) {
                            Devis devis = new Devis();
                            devis.setMontantEstime(resultSet.getDouble("montant_estime"));
                            devis.setDateEmission(resultSet.getDate("dateemission").toLocalDate());
                            devis.setDateValidite(resultSet.getDate("datevalidite").toLocalDate());
                            devis.setAccepte(resultSet.getBoolean("accepte"));
                            devis.setProjet(projet);

                            projet.setDevisList(devis);
                        }
                    }
                    return Optional.of(projet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Projets> findAll() throws SQLException {
        String sql = "SELECT * FROM projets JOIN clients ON clients.id_clients = projets.clients_id_reference LEFT JOIN Devis on Devis.projet_id = projets.id_projets";
        Map<Integer, Clients> clientMap = new HashMap<>();
        Map<Integer, Devis> devisMap = new HashMap<>();
        List<Projets> projetsList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){

            while (resultSet.next()) {
                int clientId = resultSet.getInt(6);
                Clients client;
                Devis devis;


                if (!clientMap.containsKey(clientId)) {
                    client = new Clients();
                    client.setId(clientId);
                    client.setNom(resultSet.getString(7));
                    client.setAddress(resultSet.getString(8));
                    client.setTelephone(resultSet.getString(9));
                    client.setEstProfessionel(resultSet.getBoolean(10));
                    client.setStatut_user(ClientsStatut.valueOf(resultSet.getString(11)));
                    clientMap.put(clientId, client);
                } else {
                    client = clientMap.get(clientId);
                }

                int devisId = resultSet.getInt("id_devis");
                if (!devisMap.containsKey(devisId)) {
                    devis = new Devis();
                    devis.setId_Devis(devisId);
                    devis.setMontantEstime(resultSet.getDouble("montant_estime"));
                    devis.setDateEmission(resultSet.getDate("dateemission").toLocalDate());
                    devis.setDateValidite(resultSet.getDate("datevalidite").toLocalDate());
                    devis.setAccepte(resultSet.getBoolean("accepte"));
                    devisMap.put(devisId, devis);
                }else {
                    devis = devisMap.get(devisId);
                }


                Projets projet = new Projets();
                projet.setId(resultSet.getInt(1));
                projet.setProjects_name(resultSet.getString(2));
                projet.setMarge_benificiare(resultSet.getDouble(3));
                projet.setCout_total(resultSet.getDouble(4));
                projet.setEtat_projet(Etat_Projet.valueOf(resultSet.getString(5)));
                projet.setClient(client);
                projet.setDevisList(devis);
                client.setProjetsList(projet);
                projetsList.add(projet);

            }


        }catch(SQLException e){
            e.printStackTrace();
        }



        return projetsList;
    }

    @Override
    public void update(Projets projet, String column, String value) throws SQLException {
        String sql = "UPDATE projets SET " + column + " = ? WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            if (column.equals("marge_benificiare") || column.equals("cout_total")) {
                preparedStatement.setDouble(1, Double.parseDouble(value));
            } else if (column.equals("etat_projet")) {
                preparedStatement.setString(1, value);
            } else{
                preparedStatement.setString(1, value);
            }


            preparedStatement.setInt(2, projet.getId());
            int affectedrows = preparedStatement.executeUpdate();

            if (affectedrows > 0){
                System.out.println("Projet with ID " + projet.getId() + " was updated successfully.");
            }
        }
    }

    @Override
    public void deleteById(int id) {
            String sql = "UPDATE projets set etat_projet = 'ANNULE' WHERE id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                int AffectedRows = preparedStatement.executeUpdate();
                if (AffectedRows > 0) {
                    System.out.println("Projet with ID " + id + " was updated successfully.");
                }else {
                    System.out.println("projet with ID " + id + " was not found.");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }
}
