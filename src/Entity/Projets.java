package Entity;
import Enum.*;

import java.util.ArrayList;
import java.util.List;

public class Projets {
    private int id_Projets;
    private String projects_name;
    private Double marge_benificiare;
    private Double cout_total;
    private Etat_Projet etat_projet;
    private Clients client;
    private List<Composants> composantsList = new ArrayList<Composants>();
    private List<Devis> devisList = new ArrayList<>();

    public Projets() {

    }

    public int getId(){
        return id_Projets;
    }
    public void setId(int id_Projets){
        this.id_Projets = id_Projets;
    }
    public String getProjects_name(){
        return projects_name;
    }
    public void setProjects_name(String projects_name){
        this.projects_name = projects_name;
    }
    public Double getMarge_benificiare(){
        return marge_benificiare;
    }
    public void setMarge_benificiare(Double marge_benificiare){
        this.marge_benificiare = marge_benificiare;
    }
    public Double getCout_total(){
        return cout_total;
    }
    public void setCout_total(Double cout_total){
        this.cout_total = cout_total;
    }
    public Etat_Projet getEtat_projet(){
        return etat_projet;
    }
    public void setEtat_projet(Etat_Projet etat_projet){
        this.etat_projet = etat_projet;
    }

    public Clients getClient() {
        return client;
    }
    public void setClient(Clients client) {
        this.client = client;
    }


    public List<Devis> getDevisList() {
        return devisList;
    }
    public void setDevisList(Devis devis) {
        this.devisList.add(devis);
    }
    public List<Composants> getComposantsList() {
        return composantsList;
    }
    public void setComposantsList(Composants composants) {
        this.composantsList.add(composants);
    }
}
