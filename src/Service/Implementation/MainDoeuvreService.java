package Service.Implementation;

import Entity.MainDœuvre;
import Service.Interface.MainDoeuvreServiceInterface;

import java.sql.SQLException;
import java.util.List;

public class MainDoeuvreService implements MainDoeuvreServiceInterface {
    @Override
    public MainDœuvre createmaindoeuvre(String nom , Double tauxHoraire, Double heuresTravai, Double productiviteOuvrier) throws SQLException {

        if(tauxHoraire == null ){
            throw new IllegalArgumentException("tauxHoraire cant be empty");
        }

        if(heuresTravai == null) {
            throw new IllegalArgumentException("heures de travail cant be empty");
        }

        if(productiviteOuvrier == null) {
            throw new IllegalArgumentException("productivity Ouvrier cant be empty");
        }


        MainDœuvre mainDœuvre = new MainDœuvre();

        mainDœuvre.setHeuresTravail(heuresTravai);
        mainDœuvre.setTauxHoraire(tauxHoraire);
        mainDœuvre.setProductiviteOuvrier(productiviteOuvrier);
        mainDœuvre.setTypeComposant("Main-Doeuvre");
        mainDœuvre.setNom(nom);


        return mainDœuvre;
    }

    @Override
    public Double CalculateTotalMaindouvre(List<MainDœuvre> mainDœuvreList) {
        Double Total = null;
        if (!mainDœuvreList.isEmpty()){
            Double totalsanstva = mainDœuvreList.stream()
                    .map(e->{
                        Double tauxhoraireXheures = e.getTauxHoraire() * e.getHeuresTravai();
                        return tauxhoraireXheures * e.getProductiviteOuvrier();
                    })
                    .reduce(0.0 , Double::sum);

            Double tva = (totalsanstva * mainDœuvreList.getFirst().getTauxTVA()) / 100;
            Total = tva + totalsanstva;
        }

        return Total;
    }
}
