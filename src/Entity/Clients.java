package Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Enum.*;

public class Clients {

    private UUID id_Clients;
    private String nom;
    private String address;
    private String telephone;
    private boolean estProfessionel;
    private ClientsStatut statut_user;
    private List<Projets> projetsList = new ArrayList<>();

    public Clients() {
        this.id_Clients = UUID.randomUUID();
    }

    public UUID getId() {
        return id_Clients;
    }
    public void setId(UUID id_Clients) {
        this.id_Clients = id_Clients;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public boolean isEstProfessionel() {
        return estProfessionel;
    }
    public void setEstProfessionel(boolean estProfessionel) {
        this.estProfessionel = estProfessionel;
    }
    public ClientsStatut getStatut_user() {
        return statut_user;
    }
    public void setStatut_user(ClientsStatut statut_user) {
        this.statut_user = statut_user;
    }


    public List<Projets> getProjetsList() {
        return projetsList;
    }

    public void setProjetsList(Projets projets) {
        this.projetsList.add(projets);
    }
}
