package Entity;

import java.util.UUID;

public class Composants {
    protected UUID id_Composants;
    protected String nom;
    protected Double tauxTVA;
    protected String typeComposant;
    private Projets projet;

    public Composants() {
        this.id_Composants = UUID.randomUUID();
    }

    public UUID getId() {
        return id_Composants;
    }
    public void setId(UUID id) {
        this.id_Composants = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Double getTauxTVA() {
        return tauxTVA;
    }
    public void setTauxTVA(Double tauxTVA) {
        this.tauxTVA = tauxTVA;
    }
    public String getTypeComposant() {
        return typeComposant;
    }
    public void setTypeComposant(String typeComposant) {
        this.typeComposant = typeComposant;
    }
    public Projets getProjet() {
        return projet;
    }
    public void setProjet(Projets projet) {
        this.projet = projet;
    }



}
