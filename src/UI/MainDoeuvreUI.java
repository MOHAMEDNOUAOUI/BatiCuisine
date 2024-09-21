package UI;

import Entity.MainDœuvre;
import Entity.Materiaux;
import Service.Implementation.MainDoeuvreService;
import Service.Implementation.MateriauxService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainDoeuvreUI {

    private  static Scanner scanner = new Scanner(System.in);

    private static MainDoeuvreService mainDoeuvreService = new MainDoeuvreService();

    public static MainDœuvre CreateMaindoeuvre() throws SQLException {
        System.out.println("--- Ajout de la main-d'œuvre ---");
        System.out.println();
        System.out.println("Entrez le type de main-d'œuvre e.g., Ouvrier de base, Spécialiste) : ");
        String nom = scanner.nextLine();

        System.out.println("Entrez le taux horaire de cette main-d'œuvre (€/h) : ");
        Double tauxHoraire = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Entrez le nombre d'heures travaillées : ");
        Double heuresTravai = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Entrez le facteur de productivité (1.0 = standard, > 1.0 = haute productivité) : ");
        Double productiviteOuvrier = scanner.nextDouble();
        scanner.nextLine();


        MainDœuvre mainDœuvre = mainDoeuvreService.createmaindoeuvre(nom,tauxHoraire,heuresTravai,productiviteOuvrier);

        if(mainDœuvre != null) {
            System.out.println("Main-d'œuvre ajoutée avec succès !");
        }

        return mainDœuvre;
    }


    static void displayLabor(List<MainDœuvre> laborList) {
        System.out.println("=== Labor (Main d'œuvre) ===");
        System.out.printf("%-20s %-15s %-15s %-20s%n",
                "Nom", "Taux Horaire", "Heures Travail", "Productivité Ouvrier");
        System.out.println("-".repeat(70));
        for (MainDœuvre labor : laborList) {
            System.out.printf("%-20s %-15.2f %-15.2f %-20.2f%n",
                    labor.getNom(),
                    labor.getTauxHoraire(),
                    labor.getHeuresTravai(),
                    labor.getProductiviteOuvrier());
        }
        System.out.println();
    }

}
