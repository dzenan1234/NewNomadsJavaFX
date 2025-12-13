package com.example.newnomads;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Radnik {
    private final StringProperty ime;
    private final StringProperty prezime;
    private final StringProperty drzava;
    private final StringProperty grana;

    public Radnik(String ime, String prezime, String drzava, String grana) {
        this.ime = new SimpleStringProperty(ime);
        this.prezime = new SimpleStringProperty(prezime);
        this.drzava = new SimpleStringProperty(drzava);
        this.grana = new SimpleStringProperty(grana);
    }

    public String getIme() { return ime.get(); }
    public StringProperty imeProperty() { return ime; }

    public String getPrezime() { return prezime.get(); }
    public StringProperty prezimeProperty() { return prezime; }

    public String getDrzava() { return drzava.get(); }
    public StringProperty drzavaProperty() { return drzava; }

    public String getGrana() { return grana.get(); }
    public StringProperty granaProperty() { return grana; }
}
