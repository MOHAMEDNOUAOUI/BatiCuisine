package Service.Implementation;

import Database.Database;
import Entity.*;
import Repository.Implementation.MainDoeuvreRepository;
import Repository.Implementation.MateriauxRepository;
import Repository.Implementation.ProjetsRepository;
import Service.Interface.ProjetsServiceInterface;
import com.sun.tools.javac.Main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import Enum.*;

import static validation.validationproject.validationprojectfunction;

public class ProjetsService implements ProjetsServiceInterface {

    private Connection connection = Database.getConnection();
    private MateriauxService materiauxService = new MateriauxService();
    private MainDoeuvreService mainDoeuvreService = new MainDoeuvreService();
    private ProjetsRepository projetsRepository = new ProjetsRepository(connection);
    private MainDoeuvreRepository mainDoeuvreRepository = new MainDoeuvreRepository(connection);
    private MateriauxRepository materiauxRepository = new MateriauxRepository(connection);

    @Override
    public Projets createProject(String nom, Double tauxtva, Double margebenificiaire, List<Materiaux> materiauxList, List<MainDœuvre> mainDœuvreList, Clients client) throws SQLException {
        Projets MainProject = new Projets();
        if(validationprojectfunction(nom, tauxtva , margebenificiaire , materiauxList , mainDœuvreList, client)){


            /// Creating Project instance first

            MainProject.setProjects_name(nom);
            MainProject.setEtat_projet(Etat_Projet.ENCOURS);
            MainProject.setMarge_benificiare(margebenificiaire);
            MainProject.setClient(client);










            //CHecking each item on material list and add a tauxtva in it

            materiauxList = materiauxList.stream()
                    .map(e-> {e.setTauxTVA(tauxtva);
                        e.setProjet(MainProject);
                    return  e;
                    })
                    .collect(Collectors.toList());
            mainDœuvreList = mainDœuvreList.stream()
                    .map(e ->{e.setTauxTVA(tauxtva);
                        e.setProjet(MainProject);
                    return e;
                    })
                    .collect(Collectors.toList());



            MainProject.setMainDœuvreList(mainDœuvreList);
            MainProject.setMateriauxList(materiauxList);

            ////////////////////////////////////////////



            //calculate materials total and maindouvre total price
            Double totalmaterials = materiauxService.CalculateTotalMaterials(materiauxList);
            Double totalMainDouvre = mainDoeuvreService.CalculateTotalMaindouvre(mainDœuvreList);


            //CalCulate marge
            Double marge = (totalMainDouvre + totalmaterials) * MainProject.getMarge_benificiare();
            Double Lastmarge =  marge / 100;
            //Price without marge
            Double PriceWithoutMarge = totalMainDouvre + totalmaterials;


            //set project total price with marge
            MainProject.setCout_total(Lastmarge + PriceWithoutMarge);

            projetsRepository.save(MainProject);
            mainDœuvreList.forEach(e -> mainDoeuvreRepository.save(e));
            materiauxList.forEach(e -> materiauxRepository.save(e));



        }

        return MainProject;
    }



}
