package UI;

import Database.Database;
import Entity.Clients;
import Service.Implementation.ClientsService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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

        if(!foundClient.getProjetsList().isEmpty()){
            System.out.println("Projects : "+
                    foundClient.getProjetsList().stream().map(e-> {
                        return e.getProjects_name();
                    }).toList() +"");
        }
        else{
            System.out.println("Projects : 0");
        }
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




    public static void ShowAllClients() throws SQLException {

        List<Clients> clientsList = clientsService.getAllClients();
        // Table width constants
        final int ID_WIDTH = 36;  // Standard UUID length
        final int NAME_WIDTH = 20;
        final int ADDRESS_WIDTH = 30;
        final int PHONE_WIDTH = 15;
        final int STATUS_WIDTH = 10;
        final int PROJECTS_WIDTH = 30;

        if(clientsList.isEmpty()){
            System.out.println("null");
            return;
        }

        // Print table header
        printLine(ID_WIDTH, NAME_WIDTH, ADDRESS_WIDTH, PHONE_WIDTH, STATUS_WIDTH, PROJECTS_WIDTH);
        printHeader("ID", "Name", "Address", "Phone", "Status", "Projects");
        printLine(ID_WIDTH, NAME_WIDTH, ADDRESS_WIDTH, PHONE_WIDTH, STATUS_WIDTH, PROJECTS_WIDTH);

        for (Clients client : clientsList) {
            String projects = client.getProjetsList().isEmpty() ?
                    "0" :
                    client.getProjetsList().stream()
                            .map(e -> e.getProjects_name())
                            .collect(Collectors.joining(", "));

            System.out.printf("| %-" + ID_WIDTH + "s " +
                            "| %-" + NAME_WIDTH + "s " +
                            "| %-" + ADDRESS_WIDTH + "s " +
                            "| %-" + PHONE_WIDTH + "s " +
                            "| %-" + STATUS_WIDTH + "s " +
                            "| %-" + PROJECTS_WIDTH + "s |\n",
                    client.getId().toString(),
                    truncateString(client.getNom(), NAME_WIDTH),
                    truncateString(client.getAddress(), ADDRESS_WIDTH),
                    truncateString(client.getTelephone(), PHONE_WIDTH),
                    client.getStatut_user(),
                    truncateString(projects, PROJECTS_WIDTH));
        }

        // Print table footer
        printLine(ID_WIDTH, NAME_WIDTH, ADDRESS_WIDTH, PHONE_WIDTH, STATUS_WIDTH, PROJECTS_WIDTH);
    }

    private static void printLine(int... widths) {
        System.out.print("+");
        for (int width : widths) {
            System.out.print("-".repeat(width + 2) + "+");
        }
        System.out.println();
    }

    private static void printHeader(String... headers) {
        System.out.print("| ");
        for (String header : headers) {
            System.out.printf("%-" + header.length() + "s | ", header);
        }
        System.out.println();
    }

    private static String truncateString(String str, int maxLength) {
        if (str == null) {
            return "";
        }
        return str.length() <= maxLength ? str : str.substring(0, maxLength - 3) + "...";
    }





}
