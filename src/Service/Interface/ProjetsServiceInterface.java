package Service.Interface;

import Entity.Clients;
import Entity.MainDœuvre;
import Entity.Materiaux;
import Entity.Projets;

import java.sql.SQLException;
import java.util.List;

public interface ProjetsServiceInterface {

    public void createProject(String nom ,Double tauxtva , Double margebenificiaire , List<Materiaux> materiauxList , List<MainDœuvre> mainDœuvreList , Clients client) throws SQLException;

}
