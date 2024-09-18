package UI;

import Service.Implementation.ProjetsService;

import java.util.Scanner;

public class ProjectUI {

    private ProjetsService projetsService = new ProjetsService();
    private  static Scanner scanner = new Scanner(System.in);
    public static void CreateProject() {
        System.out.println("Hey welcome to Project manager system");
        System.out.println("First we should check for the the user if it exist or not");
        System.out.println("1. Find existing Client");
        System.out.println("2. Create new Client");
        int choice = scanner.nextInt();

        switch (choice){
            case 1:

                break;

            case 2:
                break;

            default:
                System.out.println("Wrong choice");
        }

    }

}
