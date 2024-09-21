package UI;

import Entity.*;
import Service.Implementation.ClientsService;
import Service.Implementation.MainDoeuvreService;
import Service.Implementation.MateriauxService;
import Service.Implementation.ProjetsService;
import com.sun.tools.javac.Main;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static UI.ClientUi.CreateClient;
import static UI.ClientUi.FindClient;
import static UI.DevisUI.CreateDevis;
import static UI.MainDoeuvreUI.CreateMaindoeuvre;
import static UI.MainDoeuvreUI.displayLabor;
import static UI.MaterialUI.CreateMaterial;
import static UI.MaterialUI.displayMaterials;

import Enum.*;

public class ProjectUI {

    private static ProjetsService projetsService = new ProjetsService();
    private static ClientsService clientsService = new ClientsService();
    private static MainDoeuvreService mainDoeuvreService = new MainDoeuvreService();
    private static MateriauxService materiauxService = new MateriauxService();
    private  static Scanner scanner = new Scanner(System.in);



    public static void CreateProject() throws SQLException, InterruptedException {
        System.out.println("🌟 Welcome to the Project Manager System! Let's get started by checking if your client profile exists.");
        System.out.println("1. Search for an existing client");
        System.out.println("2. Create a new client profile");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                Optional<Clients> client = FindClient();
                Clients newclient;
                if(client.isPresent()){
                    System.out.println("🎉 Great! We found your client. Let's save this project.");
                    newclient = client.get();
                }else{
                    System.out.println("😞 Sorry, we couldn't find that client. Let's create a new one!");
                    newclient = CreateClient();
                }
                ProjectSave(newclient);
                break;

            case 2:
                Clients oldclient = CreateClient();
                if(oldclient != null) {
                    System.out.println("🎉 New client created! Now, let’s move on to the project.");
                    ProjectSave(oldclient);
                }else {
                    System.out.println("😞 It seems we couldn’t create the client. Please try again.");
                }
                break;

            default:
                System.out.println("⚠️ Oops! That's not a valid choice. Please try again.");
        }

    }




    public static void ProjectSave(Clients client) throws SQLException, InterruptedException {


        List<Materiaux> materiauxList = new ArrayList<>();
        List<MainDœuvre> mainDœuvreList = new ArrayList<>();

        Clients mainclient = client;



        System.out.println("🤔 Are you ready to proceed with this client? (yes/no)");
        String yesorno = scanner.nextLine();
        if(yesorno.toLowerCase().equals("yes")) {
            System.out.println("--- Let's create a new project! ---");
            System.out.println("Please enter the project name:");
            String nom = scanner.nextLine();


            //add Material
            boolean matt = false;
            do {

                Materiaux material = CreateMaterial();
                materiauxList.add(material);


                System.out.println("🌿 Would you like to add another material? (y/n)");
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

                System.out.println("👷 Would you like to add another laborer? (y/n)");
                String yn = scanner.nextLine();
                if(yn.equals("n")){
                    main = true;
                }
            }while(!main);





            System.out.println("--- Time to calculate the total cost! ---");
            System.out.println("Would you like to apply VAT to the project? (y/n):");
            String tvachoice = scanner.nextLine();


            Double tauxTVA;
            if(tvachoice.toLowerCase().equals("y")){
                System.out.println("Please enter the VAT percentage (%):");
                tauxTVA = scanner.nextDouble();
                scanner.nextLine();
            } else {
                tauxTVA = null;
            }



            System.out.println("Would you like to apply a profit margin to the project? (y/n):");
            String margechoice = scanner.nextLine();


            Double marge_benificiare;
            if(margechoice.toLowerCase().equals("y")){
                System.out.println("Please enter the profit margin percentage (%):");
                marge_benificiare = scanner.nextDouble();
                scanner.nextLine();
            }
            else{
                marge_benificiare = null;
            }

            Projets project = projetsService.createProject(nom , tauxTVA , marge_benificiare , materiauxList , mainDœuvreList , client);


            System.out.println("⏳ Please wait while we finalize everything...");
            Thread.sleep(4000);


            System.out.println("🎉 Your project has been created successfully!");
            CalculateLeCout(project);

        }
        else{
            System.out.println("❌ No worries! Let’s take a step back.");
            return;
        }


        System.out.println();
    }



    public static void CalculateLeCout(Projets project) throws SQLException, InterruptedException {
                System.out.println("💡 Calculating costs...");
                Thread.sleep(2000);

                if(project != null) {
                    System.out.println("--- Here are the details of the cost calculation ---");
                    System.out.println("Project Name: " + project.getProjects_name());
                    System.out.println("Client: " + project.getClient().getNom());
                    System.out.println("Project Address: " + project.getClient().getAddress());
                    System.out.println("---------------------------");

                    System.out.println("--- Cost Breakdown ---");
                    if(!project.getMateriauxList().isEmpty()){
                        System.out.println("1. Materials:");
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
                        Double tauxtvaprice = (initprice * project.getMateriauxList().getFirst().getTauxTVA() ) / 100;
                        System.out.println("**Coût total des matériaux avec TVA ("+project.getMateriauxList().getFirst().getTauxTVA()+"%) : "+(initprice+tauxtvaprice));
                    }

                    if(!project.getMainDœuvreList().isEmpty()){
                        System.out.println("2. Labor:");

                        Double initprice = project.getMainDœuvreList().stream()
                                .map(e -> {
                                    Double pricefirst = Optional.ofNullable(e.getTauxHoraire()).orElse(0.0) * Optional.ofNullable(e.getHeuresTravai()).orElse(0.0);
                                    Double LastPrice = pricefirst * Optional.ofNullable(e.getProductiviteOuvrier()).orElse(0.0);
                                    System.out.println("- "+ e.getNom() +": "+ LastPrice +"$ (taux horaire : "+e.getTauxHoraire()+" , Heures travaillées : "+e.getHeuresTravai()+" , roductivité : "+e.getProductiviteOuvrier());
                                    return LastPrice;
                                }).reduce(0.0 , Double::sum);

                        System.out.println("**Coût total de la main-d'œuvre avant TVA : "+ initprice);
                        Double tauxtvaprice = (initprice * project.getMainDœuvreList().getFirst().getTauxTVA() ) / 100;
                        System.out.println("**Coût total des matériaux avec TVA ("+project.getMainDœuvreList().getFirst().getTauxTVA()+"%) : "+(initprice+tauxtvaprice));

                    }

                    System.out.println();

                    Double totalmaterials = materiauxService.CalculateTotalMaterials(project.getMateriauxList());
                    Double totalMainDouvre = mainDoeuvreService.CalculateTotalMaindouvre(project.getMainDœuvreList());

                    Double totalwithoutmarge = totalmaterials+totalMainDouvre;

                    System.out.println("3. Coût total avant marge : "+totalwithoutmarge+" €");
                    Double marge = totalwithoutmarge * project.getMarge_benificiare();
                    Double Lastmarge =  marge / 100;
                    System.out.println("4. Marge bénéficiaire (15%) : "+(Lastmarge)+" €");

                    System.out.println("**Coût total final du projet : "+project.getCout_total()+" €");

                    System.out.println();
                    System.out.println("Would you like to create a Devis ? (y/n)");
                    String choice = scanner.nextLine();

                    if(choice.trim().toLowerCase().equals("y")){
                        CreateDevis(project);
                    }

                }
                else {
                    System.out.println("🚫 Oops! Something went wrong. Please try again.");
                }
    }



    public static void ShowAllProject() throws SQLException, InterruptedException {
        List<Projets> projetsList = projetsService.FindALlProject();
        projetsList.forEach(ProjectUI::displayProjectDetails);

        while(true){
            System.out.println("Would you like to calculate a project final estime (yes/no) : ");
            String choice = scanner.nextLine();

            if(choice.trim().toLowerCase().equals("yes")){
                    System.out.println("Pick the id of the project");
                    String projectid = scanner.nextLine();

                    UUID id = UUID.fromString(projectid);
                    Projets projet = projetsList.stream()
                                    .filter(e->e.getId().equals(id))
                                            .findFirst()
                                                    .orElse(null);
                    if(projet !=null){
                        CalculateLeCout(projet);
                        break;
                    }
                    else{
                        System.out.println("No project found with the given ID.");
                    }
            } else if (choice.equals("no")) {
                System.out.println("Exiting...");
                break;
            }
            else{
                System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
            }
        }
    }


    public static void displayProjectDetails(Projets project) {
        System.out.println("📊 Project Overview:");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
        System.out.printf("Project: %s (ID: %s) | Etat: %s | Client: %s | Marge: %.2f | TVA: %.2f | Total Cost: %.2f%n",
                project.getProjects_name(), project.getId(), project.getEtat_projet(), project.getClient().getNom(),project.getMarge_benificiare(),project.getMainDœuvreList().getFirst().getTauxTVA() ,project.getCout_total());
        System.out.printf("Materials: %s | Main d'œuvre: %s | Devis: %d%n",
                project.getMateriauxList().stream().map(Materiaux::getNom).collect(Collectors.joining(", ")),
                project.getMainDœuvreList().stream().map(MainDœuvre::getNom).collect(Collectors.joining(", ")),
                project.getDevisList().size());
        System.out.println();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
    }



    public static void calculateprojectcout() throws SQLException, InterruptedException {
        System.out.println();
        System.out.println("🔍 Please enter the project ID you want to calculate:");
        String projectid = scanner.nextLine();

        UUID id = UUID.fromString(projectid);
        Optional<Projets> projets = projetsService.findprojet(id);

        if(projets.isPresent()){
                CalculateLeCout(projets.get());
                CreateDevis(projets.get());
        }else{
            System.out.println("😞 Sorry, we couldn't find that project. Please check the ID and try again.");
        }
    }


}
