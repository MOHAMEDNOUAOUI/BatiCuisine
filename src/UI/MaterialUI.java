package UI;

import Entity.Clients;
import Entity.Materiaux;
import Entity.Projets;
import Service.Implementation.MateriauxService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MaterialUI {

    private  static Scanner scanner = new Scanner(System.in);

    private static MateriauxService materiauxService = new MateriauxService();

    public static Materiaux CreateMaterial() throws SQLException {
        System.out.println("--- Ajout des matériaux ---");
        System.out.println();
        System.out.println("Entrez le nom du matériau : ");
        String nom = scanner.nextLine();
        System.out.println("Entrez la quantité de ce matériau : ");
        Double quantite = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Entrez le coût unitaire de ce matériau : ");
        Double coutUnitaire = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Entrez le coût de transport de ce matériau : ");
        Double coutTransport = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Entrez le coefficient de qualité du matériau (1.0 = standard, > 1.0 = haute qualité) : ");
        Double coefficientQualite = scanner.nextDouble();
        scanner.nextLine();

        Materiaux material  = materiauxService.creatematerial( nom , quantite , coutUnitaire , coutTransport , coefficientQualite);

        if(material != null) {
            System.out.println("Matériau ajouté avec succès !");
        }


        return material;
    }


    public static void displayMaterials(List<Materiaux> materials) {
        System.out.println("=== Materials ===");
        System.out.printf("%-20s %-15s %-10s %-15s %-20s%n",
                "Nom", "Coût Unitaire", "Quantité", "Coût Transport", "Coefficient Qualité");
        System.out.println("-".repeat(80));
        for (Materiaux material : materials) {
            System.out.printf("%-20s %-15.2f %-10.2f %-15.2f %-20.2f%n",
                    material.getNom(),
                    material.getCoutUnitaire(),
                    material.getQuantite(),
                    material.getCoutTransport(),
                    material.getCoefficientQualite());
        }
        System.out.println();
    }

}
