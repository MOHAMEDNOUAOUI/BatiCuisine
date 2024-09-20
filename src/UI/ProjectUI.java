package UI;

import Entity.*;
import Service.Implementation.ClientsService;
import Service.Implementation.MainDoeuvreService;
import Service.Implementation.MateriauxService;
import Service.Implementation.ProjetsService;
import com.sun.tools.javac.Main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static UI.ClientUi.CreateClient;
import static UI.ClientUi.FindClient;
import static UI.MainDoeuvreUI.CreateMaindoeuvre;
import static UI.MaterialUI.CreateMaterial;

import Enum.*;

public class ProjectUI {

    private static ProjetsService projetsService = new ProjetsService();
    private static ClientsService clientsService = new ClientsService();
    private static MainDoeuvreService mainDoeuvreService = new MainDoeuvreService();
    private static MateriauxService materiauxService = new MateriauxService();
    private  static Scanner scanner = new Scanner(System.in);



    public static void CreateProject() throws SQLException, InterruptedException {
        System.out.println("Hey welcome to Project manager system");
        System.out.println("First we should check for the the user if it exist or not");
        System.out.println("1. Find existing Client");
        System.out.println("2. Create new Client");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                Optional<Clients> client = FindClient();
                if(client.isPresent()){
                    ProjectSave(client.get());
                }
                break;

            case 2:
                Clients newclient = CreateClient();
                if(newclient != null) {
                    ProjectSave(newclient);
                }
                break;

            default:
                System.out.println("Wrong choice");
        }

    }




    public static void ProjectSave(Clients client) throws SQLException, InterruptedException {


        List<Materiaux> materiauxList = new ArrayList<>();
        List<MainDœuvre> mainDœuvreList = new ArrayList<>();

        Clients mainclient = client;



        System.out.println("Would you like to use this client ? (yes/no)");
        String yesorno = scanner.nextLine();
        if(yesorno.toLowerCase().equals("yes")) {
            System.out.println("--- Création d'un Nouveau Projet ---");
            System.out.println();
            System.out.println("Entrez le nom du projet : ");
            String nom = scanner.nextLine();


            //add Material
            boolean matt = false;
            do {

                Materiaux material = CreateMaterial();
                materiauxList.add(material);


                System.out.println("Voulez-vous ajouter un autre matériau ? (y/n) ");
                String yn = scanner.nextLine();
                if(yn.equals("n")){
                    matt = true;
                }
            } while (!matt);

            //add main doevre
            boolean main = false;

            do{

                MainDœuvre mainDœuvre = CreateMaindoeuvre();
                mainDœuvreList.add(mainDœuvre);

                System.out.println("Voulez-vous ajouter un autre Main doeuvre ? (y/n) ");
                String yn = scanner.nextLine();
                if(yn.equals("n")){
                    main = true;
                }
            }while(!main);





            System.out.println("--- Calcul du coût total ---");
            System.out.println("Souhaitez-vous appliquer une TVA au projet ? (y/n) :");
            String tvachoice = scanner.nextLine();


            Double tauxTVA;
            if(tvachoice.toLowerCase().equals("y")){
                System.out.println("Entrez le pourcentage de TVA (%) : ");
                tauxTVA = scanner.nextDouble();
                scanner.nextLine();
            } else {
                tauxTVA = null;
            }



            System.out.println("Souhaitez-vous appliquer une marge bénéficiaire au projet ? (y/n) :");
            String margechoice = scanner.nextLine();


            Double marge_benificiare;
            if(margechoice.toLowerCase().equals("y")){
                System.out.println("Entrez le pourcentage de marge bénéficiaire (%) : ");
                marge_benificiare = scanner.nextDouble();
                scanner.nextLine();
            }
            else{
                marge_benificiare = null;
            }


            System.out.println(tauxTVA);
            System.out.println(marge_benificiare);
            Thread.sleep(4000);

            Projets project = projetsService.createProject(nom , tauxTVA , marge_benificiare , materiauxList , mainDœuvreList , client);

            System.out.println("Wait a bit ...");
            Thread.sleep(3000);

            System.out.println("Project has been created succefully");

            CalculateLeCout(project);


        }
        else{
            return;
        }


        System.out.println();
    }



    public static void CalculateLeCout(Projets project) throws SQLException, InterruptedException {
                System.out.println("Calcul du coût en cours...");
                Thread.sleep(2000);

                if(project != null) {
                    System.out.println("--- Résultat du Calcul ---");
                    System.out.println("Nom du projet : " + project.getProjects_name());
                    System.out.println("Client : " + project.getClient().getNom());
                    System.out.println("Adresse du chantier : "+project.getClient().getAddress());
                    System.out.println("---------------------------");

                    System.out.println();

                    System.out.println("--- Détail des Coûts ---");
                    if(!project.getMateriauxList().isEmpty()){
                        System.out.println("1. Matériaux :");
                        System.out.println();

                        Double initprice = project.getMateriauxList().stream()
                                .map(e -> {
                                    Double firstprice = Optional.ofNullable(e.getQuantite()).orElse(0.0) * Optional.ofNullable(e.getCoutUnitaire()).orElse(0.0);
                                    Double priceXquality = firstprice * Optional.ofNullable(e.getCoefficientQualite()).orElse(0.0);
                                    Double PricePlusTransport = priceXquality + Optional.ofNullable(e.getCoutTransport()).orElse(0.0);
                                    System.out.println("- "+ e.getNom() +": "+ PricePlusTransport +"$ (quantité : "+e.getQuantite()+" , coût unitaire : "+e.getCoutUnitaire()+" , qualité : "+e.getCoefficientQualite()+" , transport : "+e.getCoutTransport()+")");
                                    return PricePlusTransport;
                                })
                                .reduce(0.0 , Double::sum);


                        System.out.println("**Coût total des matériaux avant TVA : "+ initprice);
                        Double lastpricewithtva = (initprice * project.getMateriauxList().getFirst().getTauxTVA() ) / 100;
                        System.out.println("**Coût total des matériaux avec TVA ("+project.getMateriauxList().getFirst().getTauxTVA()+"%) : "+lastpricewithtva);
                    }

                    if(!project.getMainDœuvreList().isEmpty()){
                        System.out.println("2. Main-d'œuvre :");
                        System.out.println();

                        Double initprice = project.getMainDœuvreList().stream()
                                .map(e -> {
                                    Double pricefirst = Optional.ofNullable(e.getTauxHoraire()).orElse(0.0) * Optional.ofNullable(e.getHeuresTravai()).orElse(0.0);
                                    Double LastPrice = pricefirst * Optional.ofNullable(e.getProductiviteOuvrier()).orElse(0.0);
                                    System.out.println("- "+ e.getNom() +": "+ LastPrice +"$ (taux horaire : "+e.getTauxHoraire()+" , Heures travaillées : "+e.getHeuresTravai()+" , roductivité : "+e.getProductiviteOuvrier());
                                    return LastPrice;
                                }).reduce(0.0 , Double::sum);

                        System.out.println("**Coût total de la main-d'œuvre avant TVA : "+ initprice);
                        Double lastpricewithtva = (initprice * project.getMainDœuvreList().getFirst().getTauxTVA() ) / 100;
                        System.out.println("**Coût total des matériaux avec TVA ("+project.getMainDœuvreList().getFirst().getTauxTVA()+"%) : "+lastpricewithtva);

                    }

                    System.out.println();

                    Double totalmaterials = materiauxService.CalculateTotalMaterials(project.getMateriauxList());
                    Double totalMainDouvre = mainDoeuvreService.CalculateTotalMaindouvre(project.getMainDœuvreList());

                    Double totalwithoutmarge = totalmaterials+totalMainDouvre;

                    System.out.println("3. Coût total avant marge : "+totalwithoutmarge+" €");
                    Double marge = totalwithoutmarge * project.getMarge_benificiare();
                    Double Lastmarge =  marge / 100;
                    System.out.println("4. Marge bénéficiaire (15%) : "+Lastmarge+" €");

                    System.out.println("**Coût total final du projet : "+project.getCout_total()+" €**");

                }
                else {
                    System.out.println("Sorry something wrong happend");
                }
    }


}
