package Entity;

public class Materiaux extends  Composants {

    private Double coutUnitaire;
    private Double quantite;
    private Double coutTransport;
    private Double coefficientQualite;

    public Materiaux(){
        super();
    }

    public Double getCoutUnitaire() {
        return coutUnitaire;
    }
    public void setCoutUnitaire(Double coutUnitaire) {
        this.coutUnitaire = coutUnitaire;
    }
    public Double getQuantite() {
        return quantite;
    }
    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }
    public Double getCoutTransport() {
        return coutTransport;
    }
    public void setCoutTransport(Double coutTransport) {
        this.coutTransport = coutTransport;
    }
    public Double getCoefficientQualite() {
        return coefficientQualite;
    }
    public void setCoefficientQualite(Double coefficientQualite) {
        this.coefficientQualite = coefficientQualite;
    }


}
