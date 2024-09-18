package Entity;

import java.time.LocalDate;
import java.util.UUID;

public class Devis {
    private UUID id_Devis;
    private Double montantEstime;
    private LocalDate dateEmission;
    private LocalDate dateValidite;
    private boolean accepte;
    private Projets projet;

    public Devis() {
        this.id_Devis = UUID.randomUUID();
    }

    public UUID getId_Devis() {
        return id_Devis;
    }
    public void setId_Devis(UUID id_Devis) {
        this.id_Devis = id_Devis;
    }
    public Double getMontantEstime() {
        return montantEstime;
    }
    public void setMontantEstime(Double montantEstime) {
        this.montantEstime = montantEstime;
    }
    public LocalDate getDateEmission() {
        return dateEmission;
    }
    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }
    public LocalDate getDateValidite() {
        return dateValidite;
    }
    public void setDateValidite(LocalDate dateValidite) {
        this.dateValidite = dateValidite;
    }
    public boolean isAccepte() {
        return accepte;
    }
    public void setAccepte(boolean accepte) {
        this.accepte = accepte;
    }
    public Projets getProjet() {
        return projet;
    }
    public void setProjet(Projets projet) {
        this.projet = projet;
    }

}
