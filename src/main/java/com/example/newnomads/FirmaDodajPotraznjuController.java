package com.example.newnomads;

import bazneTabele.PotraznjaRadnika;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.sql.Date;
import java.sql.Timestamp;

public class FirmaDodajPotraznjuController {

    @FXML private TextField naslovField;
    @FXML private TextArea opisField;
    @FXML private DatePicker rokPicker;
    @FXML private ComboBox<String> granaCombo;
    @FXML private TextField brojRadnikaField;

    @FXML
    public void initialize() {
        // primjer učitavanja grana
        granaCombo.getItems().addAll("Građevina", "Ugostiteljstvo", "IT", "Zdravstvo");
    }

    @FXML
    private void objaviPotraznju() {
        String naslov = naslovField.getText();
        String opis = opisField.getText();
        Date rok = Date.valueOf(rokPicker.getValue());
        String grana = granaCombo.getValue();
        int brojRadnika = Integer.parseInt(brojRadnikaField.getText());

        int idFirme = Session.getIdFirme(); // koristiš session da dobiješ firmu koja je prijavljena

        PotraznjaRadnika p = new PotraznjaRadnika(
                0, // id će se auto generisati
                new Timestamp(System.currentTimeMillis()),
                1, // drzavaId placeholder
                idFirme,
                rok,
                naslov,
                opis,
                "Aktivna",
                grana,
                brojRadnika
        );

        boolean uspjeh = PotraznjaDAO.dodajPotraznju(p);

        if (uspjeh) {
            // vrati na listu potražnji
            try {
                Stage stage = (Stage) naslovField.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/newnomads/firmaPotraznje.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Greška pri dodavanju potražnje!");
            alert.show();
        }
    }
}
