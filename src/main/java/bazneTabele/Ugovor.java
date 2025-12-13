package bazneTabele;

import javafx.beans.property.*;
import java.sql.Date;
import java.sql.Timestamp;

public class Ugovor {

    private final IntegerProperty idUgovora;
    private final IntegerProperty idFirme;
    private final IntegerProperty idRadnika;
    private final ObjectProperty<Timestamp> datumKreiranja;
    private final IntegerProperty drzavaRadaId;
    private final ObjectProperty<Date> datumPocetkaRada;
    private final ObjectProperty<Date> datumKrajaRada;
    private final StringProperty statusUgovora;
    private final StringProperty opis;
    private final StringProperty radnik;   // novo
    private final StringProperty firma;    // novo

    public Ugovor(int idUgovora,
                  int idFirme,
                  int idRadnika,
                  Timestamp datumKreiranja,
                  int drzavaRadaId,
                  Date datumPocetkaRada,
                  Date datumKrajaRada,
                  String statusUgovora,
                  String opis,
                  String radnik,
                  String firma) {

        this.idUgovora = new SimpleIntegerProperty(idUgovora);
        this.idFirme = new SimpleIntegerProperty(idFirme);
        this.idRadnika = new SimpleIntegerProperty(idRadnika);
        this.datumKreiranja = new SimpleObjectProperty<>(datumKreiranja);
        this.drzavaRadaId = new SimpleIntegerProperty(drzavaRadaId);
        this.datumPocetkaRada = new SimpleObjectProperty<>(datumPocetkaRada);
        this.datumKrajaRada = new SimpleObjectProperty<>(datumKrajaRada);
        this.statusUgovora = new SimpleStringProperty(statusUgovora != null ? statusUgovora : "");
        this.opis = new SimpleStringProperty(opis != null ? opis : "");
        this.radnik = new SimpleStringProperty(radnik != null ? radnik : "");
        this.firma = new SimpleStringProperty(firma != null ? firma : "");
    }

    // Property metode
    public IntegerProperty idUgovoraProperty() { return idUgovora; }
    public IntegerProperty idFirmeProperty() { return idFirme; }
    public IntegerProperty idRadnikaProperty() { return idRadnika; }
    public ObjectProperty<Timestamp> datumKreiranjaProperty() { return datumKreiranja; }
    public IntegerProperty drzavaRadaIdProperty() { return drzavaRadaId; }
    public ObjectProperty<Date> datumPocetkaRadaProperty() { return datumPocetkaRada; }
    public ObjectProperty<Date> datumKrajaRadaProperty() { return datumKrajaRada; }
    public StringProperty statusUgovoraProperty() { return statusUgovora; }
    public StringProperty opisProperty() { return opis; }
    public StringProperty radnikProperty() { return radnik; }
    public StringProperty firmaProperty() { return firma; }

    // Getteri
    public int getIdUgovora() { return idUgovora.get(); }
    public int getIdFirme() { return idFirme.get(); }
    public int getIdRadnika() { return idRadnika.get(); }
    public Timestamp getDatumKreiranja() { return datumKreiranja.get(); }
    public int getDrzavaRadaId() { return drzavaRadaId.get(); }
    public Date getDatumPocetkaRada() { return datumPocetkaRada.get(); }
    public Date getDatumKrajaRada() { return datumKrajaRada.get(); }
    public String getStatusUgovora() { return statusUgovora.get(); }
    public String getOpis() { return opis.get(); }
    public String getRadnik() { return radnik.get(); }
    public String getFirma() { return firma.get(); }

    // Setteri
    public void setStatusUgovora(String statusUgovora) { this.statusUgovora.set(statusUgovora); }
    public void setOpis(String opis) { this.opis.set(opis); }

    @Override
    public String toString() {
        return "Ugovor " + idUgovora.get() + " (Radnik: " + radnik.get() + ", Firma: " + firma.get() + ")";
    }


}
