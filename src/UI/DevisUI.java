package UI;

import Entity.Projets;
import Service.Implementation.DevisService;

import java.sql.SQLException;
import java.util.Scanner;

public class DevisUI {

    private static Scanner scanner = new Scanner(System.in);

    private static DevisService devisService = new DevisService();

    public static void CreateDevis(Projets projets) throws SQLException{
        System.out.println("Entrez la date d'émission du devis (format : jj-mm-aaaa) : ");
        String dateemission = scanner.nextLine();
        System.out.println("Entrez la date de validité du devis (format : jj-mm-aaaa) : ");
        String datevalidite = scanner.nextLine();

        System.out.println("Souhaitez-vous enregistrer le devis ? (y/n) : ");
        String choice = scanner.nextLine();

        if(choice.toLowerCase().trim().equals("y")){
            devisService.createdevis(dateemission , datevalidite , projets);
        }
        else{
                System.out.println("Sorry no devis for you this timee");
        }
    }

}
