package com.example.newnomads;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ugovor {

    private final IntegerProperty idUgovora;
    private final IntegerProperty idRadnika;
    private final IntegerProperty idFirme;
    private final StringProperty radnik;
    private final StringProperty firma;
    private final ObjectProperty<LocalDate> datumPocetka;
    private final ObjectProperty<LocalDate> datumKraja;
    private final StringProperty status;
    private final StringProperty opis;

    public Ugovor(int idUgovora, int idRadnika, int idFirme, String radnik,
                  String firma, LocalDate datumPocetka, LocalDate datumKraja,
                  String status, String opis) {
        this.idUgovora = new SimpleIntegerProperty(idUgovora);
        this.idRadnika = new SimpleIntegerProperty(idRadnika);
        this.idFirme = new SimpleIntegerProperty(idFirme);
        this.radnik = new SimpleStringProperty(radnik);
        this.firma = new SimpleStringProperty(firma);
        this.datumPocetka = new SimpleObjectProperty<>(datumPocetka);
        this.datumKraja = new SimpleObjectProperty<>(datumKraja);
        this.status = new SimpleStringProperty(status);
        this.opis = new SimpleStringProperty(opis);
    }

    public int getIdUgovora() { return idUgovora.get(); }
    public IntegerProperty idUgovoraProperty() { return idUgovora; }

    public int getIdRadnika() { return idRadnika.get(); }
    public IntegerProperty idRadnikaProperty() { return idRadnika; }

    public int getIdFirme() { return idFirme.get(); }
    public IntegerProperty idFirmeProperty() { return idFirme; }

    public String getRadnik() { return radnik.get(); }
    public StringProperty radnikProperty() { return radnik; }

    public String getFirma() { return firma.get(); }
    public StringProperty firmaProperty() { return firma; }

    public LocalDate getDatumPocetka() { return datumPocetka.get(); }
    public ObjectProperty<LocalDate> datumPocetkaProperty() { return datumPocetka; }

    public LocalDate getDatumKraja() { return datumKraja.get(); }
    public ObjectProperty<LocalDate> datumKrajaProperty() { return datumKraja; }

    public String getStatus() { return status.get(); }
    public StringProperty statusProperty() { return status; }

    public String getOpis() { return opis.get(); }
    public StringProperty opisProperty() { return opis; }
}
