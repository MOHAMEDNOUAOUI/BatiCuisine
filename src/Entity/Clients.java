package Entity;

import java.util.ArrayList;
import java.util.List;

public class Clients {

    private int id_Clients;
    private String nom;
    private String address;
    private String telephone;
    private boolean estProfessionel;
    private List<Projets> projetsList = new ArrayList<>();

    public Clients() {
    }

    public int getId() {
        return id_Clients;
    }
    public void setId(int id_Clients) {
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


    public List<Projets> getProjetsList() {
        return projetsList;
    }

    public void setProjetsList(Projets projets) {
        this.projetsList.add(projets);
    }
}
