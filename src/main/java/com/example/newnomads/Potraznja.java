package com.example.newnomads;

import javafx.beans.property.*;

public class Potraznja {

    private final IntegerProperty id;
    private final StringProperty grana;
    private final IntegerProperty brojRadnika;
    private final StringProperty status;
    private final StringProperty naslov;
    private final IntegerProperty firmaId;

    // Jedan konstruktor za sve
    public Potraznja(int id, String grana, Integer brojRadnika, String status,
                     String naslov, Integer firmaId) {
        this.id = new SimpleIntegerProperty(id);
        this.grana = new SimpleStringProperty(grana != null ? grana : "");
        this.brojRadnika = new SimpleIntegerProperty(brojRadnika != null ? brojRadnika : 0);
        this.status = new SimpleStringProperty(status != null ? status : "");
        this.naslov = new SimpleStringProperty(naslov != null ? naslov : "");
        this.firmaId = new SimpleIntegerProperty(firmaId != null ? firmaId : 0);
    }

    // Property metode
    public IntegerProperty idProperty() { return id; }
    public StringProperty granaProperty() { return grana; }
    public IntegerProperty brojRadnikaProperty() { return brojRadnika; }
    public StringProperty statusProperty() { return status; }
    public StringProperty naslovProperty() { return naslov; }
    public IntegerProperty firmaIdProperty() { return firmaId; }

    // Getteri i setteri
    public int getId() { return id.get(); }
    public String getGrana() { return grana.get(); }
    public int getBrojRadnika() { return brojRadnika.get(); }
    public String getStatus() { return status.get(); }
    public String getNaslov() { return naslov.get(); }
    public int getFirmaId() { return firmaId.get(); }
}
