package Service.Implementation;

import Entity.Materiaux;
import Entity.Projets;
import Service.Interface.MateriauxServiceinterface;

import java.sql.SQLException;
import java.util.List;

public class MateriauxService implements MateriauxServiceinterface {



    @Override
    public Materiaux creatematerial(String nom, Double quantite, Double coutUnitaire, Double coutTransport, Double coefficientQualite) throws SQLException {

        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be null or empty.");
        }
        if (nom.length() < 3 || nom.length() > 50) {
            throw new IllegalArgumentException("Client name must be between 3 and 50 characters.");
        }
        if (!nom.matches("[A-Za-z ]+")) {
            throw new IllegalArgumentException("Client name can only contain letters and spaces.");
        }



        if(quantite == null) {
            throw new IllegalArgumentException("quantite is empty");
        }

        if(coutUnitaire == null) {
            throw new IllegalArgumentException("cout Unitaire is empty");
        }

        if(coutTransport == null) {
            throw new IllegalArgumentException("cout transport is empty");
        }

        if(coefficientQualite == null) {
            throw new IllegalArgumentException("coefficientQualite is empty");
        }



        Materiaux material = new Materiaux();
        material.setNom(nom);
        material.setQuantite(quantite);
        material.setCoefficientQualite(coefficientQualite);
        material.setCoutTransport(coutTransport);
        material.setCoutUnitaire(coutUnitaire);
        material.setTypeComposant("Material");

        return material;
    }

    @Override
    public Double CalculateTotalMaterials(List<Materiaux> materiauxList) {

        Double Total = null;
        if (!materiauxList.isEmpty()){
            Double TotalSansTVA = materiauxList.stream()
                    .map(e -> {
                        double quantiteXcout = e.getQuantite() * e.getCoutUnitaire();
                        return (quantiteXcout * e.getCoefficientQualite()) + e.getCoutTransport();
                    })
                    .reduce(0.0 , Double::sum);

            Double tva = (TotalSansTVA * materiauxList.getFirst().getTauxTVA()) / 100;
            Total = TotalSansTVA + tva;
        }

        return Total;
    }


}
