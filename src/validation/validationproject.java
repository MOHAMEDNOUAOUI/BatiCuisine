package validation;

import Entity.Clients;
import Entity.MainDœuvre;
import Entity.Materiaux;

import java.util.List;

public class validationproject {


    public static boolean validationprojectfunction(String nom , Double tauxtva , Double margebenificiaire , List<Materiaux> materiauxList , List<MainDœuvre> mainDœuvreList , Clients client) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be null or empty.");
        }

        if (tauxtva == null || tauxtva < 0 || tauxtva > 100) {
            throw new IllegalArgumentException("Taux TVA must be between 0 and 100.");
        }

        if (margebenificiaire == null || margebenificiaire < 0 || margebenificiaire > 100) {
            throw new IllegalArgumentException("Marge Bénéficiaire must be between 0 and 100.");
        }

        if (materiauxList == null || materiauxList.isEmpty()) {
            throw new IllegalArgumentException("The list of materials (Materiaux) cannot be null or empty.");
        }

        if (mainDœuvreList == null || mainDœuvreList.isEmpty()) {
            throw new IllegalArgumentException("The list of labor (MainD'œuvre) cannot be null or empty.");
        }

        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }

        return true;
    }

}
