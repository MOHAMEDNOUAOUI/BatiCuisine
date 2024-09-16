package Entity;

public class MainDœuvre extends Composants {

    private Double tauxHoraire;
    private Double heuresTravai;
    private Double productiviteOuvrier;

    public MainDœuvre() {
        super();
    }

    public Double getHeuresTravai() {
        return heuresTravai;
    }
    public Double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }
    public Double getTauxHoraire() {
        return tauxHoraire;
    }

    public void setTauxHoraire(Double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }
    public void setHeuresTravail(Double heuresTravai) {
        this.heuresTravai = heuresTravai;
    }
    public void setProductiviteOuvrier(Double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }


}
