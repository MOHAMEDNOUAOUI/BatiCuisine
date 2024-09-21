package Service.Interface;

import Entity.Clients;
import Entity.MainDœuvre;
import Entity.Materiaux;
import Entity.Projets;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjetsServiceInterface {

    public Projets createProject(String nom ,Double tauxtva , Double margebenificiaire , List<Materiaux> materiauxList , List<MainDœuvre> mainDœuvreList , Clients client) throws SQLException;
    public List<Projets> FindALlProject() throws SQLException;
    public Optional<Projets> findprojet(UUID id) throws SQLException;
}
