package Service.Interface;

import Entity.MainDœuvre;
import Entity.Materiaux;

import java.sql.SQLException;
import java.util.List;

public interface MainDoeuvreServiceInterface {


    public MainDœuvre createmaindoeuvre(String nom , Double tauxHoraire , Double heuresTravai , Double productiviteOuvrier) throws SQLException;

    public Double CalculateTotalMaindouvre(List<MainDœuvre> mainDœuvreList);
}
