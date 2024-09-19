package Service.Implementation;

import Database.Database;
import Entity.Clients;
import Repository.Implementation.ClientsRepository;
import Service.Interface.ClientsServiceInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import Enum.*;

import javax.xml.crypto.Data;

public class ClientsService implements ClientsServiceInterface {
    Connection connection = Database.getConnection();
    ClientsRepository clientsRepository = new ClientsRepository(connection);


    @Override
    public Optional<Clients> checkifClientsExist(UUID id) throws SQLException {
        if (id == null || id.toString().isEmpty()) {
            System.out.println("Invalid client ID provided.");
            return Optional.empty();
        }

        Optional<Clients> client = clientsRepository.findById(id);

        return client;
    }

    @Override
    public Clients CreateCLient(String nom, String address, String telephone, String statut_user , boolean estprofessionel) throws SQLException {

        //nom validation
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be null or empty.");
        }
        if (nom.length() < 3 || nom.length() > 50) {
            throw new IllegalArgumentException("Client name must be between 3 and 50 characters.");
        }
        if (!nom.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Client name can only contain letters and spaces.");
        }

        //address validation
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Client address cannot be null or empty.");
        }
        if (address.length() < 10) {
            throw new IllegalArgumentException("Client address must be at least 10 characters long.");
        }


        //telephon validation
        if (telephone == null || !telephone.matches("\\d{10}")) {
            throw new IllegalArgumentException("Invalid telephone number. It must be exactly 10 digits.");
        }

        if (statut_user == null || statut_user.trim().isEmpty()) {
            throw new IllegalArgumentException("Client status cannot be null or empty.");
        }

        if(!ClientsStatut.values().equals(statut_user)){
            statut_user = ClientsStatut.ACTIVE.toString();
        }


        Clients client = new Clients();
        client.setNom(nom);
        client.setAddress(address);
        client.setTelephone(telephone);
        client.setEstProfessionel(estprofessionel);
        client.setStatut_user(ClientsStatut.valueOf(statut_user));

        clientsRepository.save(client);



        return client;
    }



}
