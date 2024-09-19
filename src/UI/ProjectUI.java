package UI;

import Entity.Clients;
import Service.Implementation.ClientsService;
import Service.Implementation.ProjetsService;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

import static UI.ClientUi.CreateClient;
import static UI.ClientUi.FindClient;

public class ProjectUI {

    private static ProjetsService projetsService = new ProjetsService();
    private static ClientsService clientsService = new ClientsService();
    private  static Scanner scanner = new Scanner(System.in);
    public static void CreateProject() throws SQLException {
        System.out.println("Hey welcome to Project manager system");
        System.out.println("First we should check for the the user if it exist or not");
        System.out.println("1. Find existing Client");
        System.out.println("2. Create new Client");
        int choice = scanner.nextInt();

        switch (choice){
            case 1:
                Optional<Clients> client = FindClient();
                break;

            case 2:
                Clients newclient = CreateClient();
                break;

            default:
                System.out.println("Wrong choice");
        }

    }



}
