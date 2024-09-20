package Service.Interface;

import Entity.Materiaux;
import Entity.Projets;

import java.sql.SQLException;
import java.util.List;

public interface MateriauxServiceinterface {


    public Materiaux creatematerial(String nom, Double quantite , Double coutUnitaire , Double coutTransport , Double coefficientQualite) throws SQLException;


    public Double CalculateTotalMaterials(List<Materiaux> materiauxList);

}
