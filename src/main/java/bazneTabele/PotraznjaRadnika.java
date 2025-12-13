package bazneTabele;

import javafx.beans.property.*;
import java.sql.Date;
import java.sql.Timestamp;

public class PotraznjaRadnika {

    private final IntegerProperty idPotraznjeRadnika;
    private final ObjectProperty<Timestamp> datumKreiranjaPotraznje;
    private final IntegerProperty drzavaId;
    private final IntegerProperty idFirme;
    private final ObjectProperty<Date> krajnjiRok;
    private final StringProperty naslovPotraznje;
    private final StringProperty opisPotraznje;
    private final StringProperty statusPotraznje;

    // Dodano za TableView u RegruterController
    private final StringProperty grana;
    private final IntegerProperty brojRadnika;

    // Konstruktor koji pokriva sve kolone iz baze + grana i brojRadnika
    public PotraznjaRadnika(int idPotraznjeRadnika,
                            Timestamp datumKreiranjaPotraznje,
                            int drzavaId,
                            int idFirme,
                            Date krajnjiRok,
                            String naslovPotraznje,
                            String opisPotraznje,
                            String statusPotraznje,
                            String grana,
                            Integer brojRadnika) {

        this.idPotraznjeRadnika = new SimpleIntegerProperty(idPotraznjeRadnika);
        this.datumKreiranjaPotraznje = new SimpleObjectProperty<>(datumKreiranjaPotraznje);
        this.drzavaId = new SimpleIntegerProperty(drzavaId);
        this.idFirme = new SimpleIntegerProperty(idFirme);
        this.krajnjiRok = new SimpleObjectProperty<>(krajnjiRok);
        this.naslovPotraznje = new SimpleStringProperty(naslovPotraznje != null ? naslovPotraznje : "");
        this.opisPotraznje = new SimpleStringProperty(opisPotraznje != null ? opisPotraznje : "");
        this.statusPotraznje = new SimpleStringProperty(statusPotraznje != null ? statusPotraznje : "");
        this.grana = new SimpleStringProperty(grana != null ? grana : "");
        this.brojRadnika = new SimpleIntegerProperty(brojRadnika != null ? brojRadnika : 0);
    }

    // Property metode za TableView
    public IntegerProperty idPotraznjeRadnikaProperty() { return idPotraznjeRadnika; }
    public ObjectProperty<Timestamp> datumKreiranjaPotraznjeProperty() { return datumKreiranjaPotraznje; }
    public IntegerProperty drzavaIdProperty() { return drzavaId; }
    public IntegerProperty idFirmeProperty() { return idFirme; }
    public ObjectProperty<Date> krajnjiRokProperty() { return krajnjiRok; }
    public StringProperty naslovPotraznjeProperty() { return naslovPotraznje; }
    public StringProperty opisPotraznjeProperty() { return opisPotraznje; }
    public StringProperty statusPotraznjeProperty() { return statusPotraznje; }
    public StringProperty granaProperty() { return grana; }
    public IntegerProperty brojRadnikaProperty() { return brojRadnika; }

    // Getteri
    public int getIdPotraznjeRadnika() { return idPotraznjeRadnika.get(); }
    public Timestamp getDatumKreiranjaPotraznje() { return datumKreiranjaPotraznje.get(); }
    public int getDrzavaId() { return drzavaId.get(); }
    public int getIdFirme() { return idFirme.get(); }
    public Date getKrajnjiRok() { return krajnjiRok.get(); }
    public String getNaslovPotraznje() { return naslovPotraznje.get(); }
    public String getOpisPotraznje() { return opisPotraznje.get(); }
    public String getStatusPotraznje() { return statusPotraznje.get(); }
    public String getGrana() { return grana.get(); }
    public int getBrojRadnika() { return brojRadnika.get(); }

    // Setteri
    public void setStatusPotraznje(String statusPotraznje) { this.statusPotraznje.set(statusPotraznje); }
    public void setNaslovPotraznje(String naslovPotraznje) { this.naslovPotraznje.set(naslovPotraznje); }
    public void setOpisPotraznje(String opisPotraznje) { this.opisPotraznje.set(opisPotraznje); }
    public void setGrana(String grana) { this.grana.set(grana); }
    public void setBrojRadnika(int brojRadnika) { this.brojRadnika.set(brojRadnika); }

    @Override
    public String toString() {
        return naslovPotraznje.get() + " (" + grana.get() + ", " + brojRadnika.get() + " radnika)";
    }
}
