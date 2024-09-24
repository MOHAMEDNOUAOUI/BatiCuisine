package Service.Interface;

import Entity.Clients;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientsServiceInterface {


    public Optional<Clients> checkifClientsExist(UUID id) throws SQLException;
    public Clients CreateCLient(String nom , String address , String telephone , String statut_user , boolean estprofessionel) throws SQLException;
    public List<Clients> getAllClients() throws SQLException;
}
