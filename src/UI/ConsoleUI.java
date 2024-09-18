package UI;

import java.util.Scanner;

import static UI.ProjectUI.CreateProject;




public class ConsoleUI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void mainUI() throws InterruptedException {
                while (true){
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
                            break;

                        case 3:
                            break;


                        case 4:
                            System.out.println("Quitting");
                            Thread.sleep(3000);
                            return;
                        default:
                            System.out.println("Sorry invalid");
                    }

                }
    }




}
