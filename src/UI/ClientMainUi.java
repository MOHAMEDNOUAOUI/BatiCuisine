package UI;

import java.sql.SQLException;
import java.util.Scanner;

import static UI.ClientUi.*;
import static UI.ProjectUI.CreateProject;

public class ClientMainUi {


    private static Scanner scanner = new Scanner(System.in);

    public static void MainClient() throws SQLException, InterruptedException {
        while(true) {
            System.out.println("+=========================================+");
            System.out.println("|              MENU PRINCIPAL             |");
            System.out.println("+=========================================+");
            System.out.println("|  1. Créer un nouveau client             |");
            System.out.println("|  2. find client                         |");
            System.out.println("|  3. Afficher les clients existants      |");
            System.out.println("|  4. Quitter                             |");
            System.out.println("+=========================================+");
            System.out.println();
            System.out.println("Please enter a number : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    CreateClient();
                    break;

                case 2:
                    FindClient();
                    break;

                case 3:
                    ShowAllClients();
                    break;

                case 4:
                    System.out.println("Quitting");
                    Thread.sleep(3000);
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }


}
