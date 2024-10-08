package UI;

import java.sql.SQLException;
import java.util.Scanner;

import static UI.ProjectUI.*;

public class ProjectMainUI {

    private static Scanner scanner = new Scanner(System.in);

    public static void MainProject() throws SQLException, InterruptedException {
        while(true) {
            System.out.println("+=========================================+");
            System.out.println("|              MENU PRINCIPAL             |");
            System.out.println("+=========================================+");
            System.out.println("|  1. Créer un nouveau projet             |");
            System.out.println("|  2. Afficher les projets existants      |");
            System.out.println("|  3. Calculer le coût d'un projet        |");
            System.out.println("|  4. Quitter                             |");
            System.out.println("+=========================================+");
            System.out.println();
            System.out.println("Please enter a number : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    CreateProject();
                    break;

                case 2:
                    ShowAllProject();
                    break;

                case 3:
                    calculateprojectcout();
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
