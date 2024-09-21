package Service.Interface;

import Entity.Projets;

import java.sql.SQLException;

public interface DevisServiceInterface {


    public void createdevis(String dateemision , String datevalidite , Projets projets) throws SQLException;

}
