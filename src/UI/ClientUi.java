package UI;

import Database.Database;
import Entity.Clients;
import Service.Implementation.ClientsService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;
import Enum.*;

public class ClientUi {
    Connection connection = Database.getConnection();
    private static ClientsService clientsService = new ClientsService();
    private static Scanner scanner = new Scanner(System.in);


    public static Optional<Clients> FindClient() throws SQLException {
        System.out.println("Please can you give me the id of this Client");
        String id = scanner.nextLine();

        UUID iduser = UUID.fromString(id);
        Optional<Clients> client = clientsService.checkifClientsExist(iduser);
        if (client.isPresent()){
            Clients foundClient = client.get();
            showClient(foundClient);
        } else {
            System.out.println("Client not found.");
        }


        return client;
    }

    public static void showClient(Clients foundClient){
        System.out.println("Client found:");
        System.out.println("ID: " + foundClient.getId());
        System.out.println("Name: " + foundClient.getNom());
        System.out.println("Adresse: " + foundClient.getAddress());
        System.out.println("Phone: " + foundClient.getTelephone());
        System.out.println("Statut: " + foundClient.getStatut_user());
    }


    public static Clients CreateClient() throws  SQLException {
        System.out.println("Welcome to Client System manager");
        System.out.println("Please give me the Client Name");
        String nom = scanner.nextLine();
        System.out.println("Please give me the Adress");
        String address = scanner.nextLine();
        System.out.println("This client phone number");
        String telephone = scanner.nextLine();
        System.out.println("Is this Client a Professionel or not ? (TRUE/FALSE) ");
        boolean estprofessionel = scanner.nextBoolean();
        scanner.nextLine();

        System.out.println("Client Statut ? ( " + Arrays.toString(ClientsStatut.values()) + " )");
        String statut_user = scanner.nextLine();

        Clients newclient = clientsService.CreateCLient(nom,address,telephone,statut_user,estprofessionel);

        showClient(newclient);
        return newclient;
    }



}
