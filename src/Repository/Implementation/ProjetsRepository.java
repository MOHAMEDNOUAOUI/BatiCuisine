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
        String sql = "INSERT INTO projets (id_projets , projects_name,marge_benificiare,cout_total,etat_projet,clients_id_reference) VALUES(?,?,?,?,?::etat_projet,?)";
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setObject(1 , projet.getId());
            ps.setString(2, projet.getProjects_name());
            ps.setDouble(3, projet.getMarge_benificiare());
            ps.setDouble(4, projet.getCout_total());
            ps.setString(5, projet.getEtat_projet().toString());
            ps.setObject(6,projet.getClient().getId());
            ps.executeUpdate();
        }
        return null;
    }

    @Override
    public Optional<Projets> findById(UUID id) throws SQLException {
        String sql = "SELECT * FROM projets JOIN clients ON clients.id_clients = projets.clients_id_reference LEFT JOIN Devis on Devis.projet_id = projets.id_projets WHERE projets.id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Projets projet = new Projets();
                    projet.setId(resultSet.getObject(1 , UUID.class));
                    projet.setProjects_name(resultSet.getString(2));
                    projet.setMarge_benificiare(resultSet.getDouble(3));
                    projet.setCout_total(resultSet.getDouble(4));
                    projet.setEtat_projet(Etat_Projet.valueOf(resultSet.getString(5)));

                    UUID clientid = resultSet.getObject(6 , UUID.class);
                    if (clientid != null) {
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
                        UUID devisId = resultSet.getObject("id_devis" , UUID.class);
                        if (devisId != null) {
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
        Map<UUID, Clients> clientMap = new HashMap<>();
        Map<UUID, Devis> devisMap = new HashMap<>();
        List<Projets> projetsList = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()){

            while (resultSet.next()) {
                UUID clientId = resultSet.getObject(6 , UUID.class);
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

                UUID devisId = resultSet.getObject("id_devis" , UUID.class);
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
                projet.setId(resultSet.getObject(1 , UUID.class));
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


            preparedStatement.setObject(2, projet.getId());
            int affectedrows = preparedStatement.executeUpdate();

            if (affectedrows > 0){
                System.out.println("Projet with ID " + projet.getId() + " was updated successfully.");
            }
        }
    }

    @Override
    public void deleteById(UUID id) {
            String sql = "UPDATE projets set etat_projet = 'ANNULE' WHERE id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setObject(1, id);
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
