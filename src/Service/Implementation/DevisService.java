package Service.Implementation;

import Database.Database;
import Entity.Devis;
import Entity.Projets;
import Repository.Implementation.DevisRepository;
import Service.Interface.DevisServiceInterface;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DevisService implements DevisServiceInterface {

    private Connection connection = Database.getConnection();
    private DevisRepository devisRepository = new DevisRepository(connection);

    @Override
    public void createdevis(String dateemision, String datevalidite, Projets projets) throws SQLException {

        LocalDate todaysdate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        if(dateemision != null && datevalidite != null) {
            try {
                LocalDate dateemission = LocalDate.parse(dateemision, formatter);
                LocalDate datevaliditte = LocalDate.parse(datevalidite, formatter);

                if((dateemission.equals(todaysdate) || dateemission.isAfter(todaysdate)) && dateemission.isBefore(datevaliditte)) {
                    Devis devis = new Devis();
                    devis.setProjet(projets);
                    devis.setDateEmission(dateemission);
                    devis.setDateValidite(datevaliditte);
                    devis.setMontantEstime(projets.getCout_total());
                    devis.setAccepte(true);
                    devisRepository.save(devis);
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use dd-MM-yyyy.");
            }

        }
    }
}
