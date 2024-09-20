package UI;

import Entity.Clients;
import Entity.Materiaux;
import Entity.Projets;
import Service.Implementation.MateriauxService;

import java.sql.SQLException;
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

}
