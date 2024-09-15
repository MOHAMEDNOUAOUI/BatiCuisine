package Entity;

public class MainDœuvre extends Composants {

    private Double tauxHoraire;
    private Double heuresTravai;
    private Double productiviteOuvrier;

    public MainDœuvre() {
        super();
    }

    public Double getTauxTVA() {
        return super.getTauxTVA();
    }
    public Double getHeuresTravai() {
        return heuresTravai;
    }
    public Double getProductiviteOuvrier() {
        return productiviteOuvrier;
    }
    public void setTauxHoraire(Double tauxHoraire) {
        this.tauxHoraire = tauxHoraire;
    }
    public void setHeuresTravai(Double heuresTravai) {
        this.heuresTravai = heuresTravai;
    }
    public void setProductiviteOuvrier(Double productiviteOuvrier) {
        this.productiviteOuvrier = productiviteOuvrier;
    }


}
