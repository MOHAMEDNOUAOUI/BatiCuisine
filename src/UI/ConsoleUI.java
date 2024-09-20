package UI;

import java.sql.SQLException;
import java.util.Scanner;

import static UI.ClientMainUi.MainClient;
import static UI.ProjectMainUI.MainProject;
import static UI.ProjectUI.CreateProject;




public class ConsoleUI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void mainUI() throws InterruptedException, SQLException {
                while (true){
                    System.out.println("+=========================================+");
                    System.out.println("|              MENU PRINCIPAL             |");
                    System.out.println("+=========================================+");
                    System.out.println("|  1. Gestion Du Projects                 |");
                    System.out.println("|  2. Gestion Du Clients                  |");
                    System.out.println("|  3. Quitter                             |");
                    System.out.println("+=========================================+");
                    System.out.println();
                    System.out.println("Please enter a number : ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();


                    switch (choice) {
                        case 1:
                            MainProject();
                            break;

                        case 2:
                            MainClient();
                            break;

                        case 3:
                            System.out.println("Quitting");
                            Thread.sleep(3000);
                            return;
                        default:
                            System.out.println("Sorry invalid");
                    }

                }
    }




}
