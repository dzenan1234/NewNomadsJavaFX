package com.example.newnomads;

import bazneTabele.PotraznjaRadnika;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class FirmaDodajPotraznjuController {

    @FXML private TextField naslovField;
    @FXML private TextArea opisField;
    @FXML private DatePicker rokPicker;
    @FXML private ComboBox<String> granaCombo;
    @FXML private TextField brojRadnikaField;

    @FXML
    public void initialize() {
        ucitajGrane();
    }

    private void ucitajGrane() {
        String sql = "SELECT nazivGraneRada FROM granaRada ORDER BY nazivGraneRada ASC";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            granaCombo.getItems().clear();

            while (rs.next()) {
                granaCombo.getItems().add(rs.getString("nazivGraneRada"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Greška pri učitavanju grana rada.");
        }
    }

    @FXML
    private void objaviPotraznju() {
        // 1. Validacija
        if (naslovField.getText().isEmpty() || granaCombo.getValue() == null || rokPicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Popunite sva polja!");
            alert.initOwner(naslovField.getScene().getWindow()); // Postavlja "vlasnika"
            alert.showAndWait(); // ČEKA da korisnik klikne OK
            return;
        }

        try {
            // ... tvoje prikupljanje podataka ostaje isto ...
            String naslov = naslovField.getText();
            String opis = opisField.getText();
            Date rok = Date.valueOf(rokPicker.getValue());
            String grana = granaCombo.getValue();
            int brojRadnika = Integer.parseInt(brojRadnikaField.getText());

            PotraznjaRadnika p = new PotraznjaRadnika(
                    0, new Timestamp(System.currentTimeMillis()), 1,
                    Session.getIdFirme(), rok, naslov, opis, "Aktivna", grana, brojRadnika
            );

            boolean uspjeh = PotraznjaDAO.dodajPotraznju(p);

            if (uspjeh) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Potražnja uspješno dodana!");
                alert.initOwner(naslovField.getScene().getWindow());

                // KLJUČNO: Program će se zaustaviti ovdje dok korisnik ne klikne OK
                alert.showAndWait();

                // Tek kad zatvori Alert, vraća se na listu
                vratiNaListuPotraznji();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Greška pri dodavanju potražnje!");
                alert.initOwner(naslovField.getScene().getWindow());
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Greška: " + e.getMessage());
            alert.initOwner(naslovField.getScene().getWindow());
            alert.showAndWait();
        }
    }
    @FXML
    private void odustani() {
        vratiNaListuPotraznji(); // Vrati se na listu
    }

    private void vratiNaListuPotraznji() {
        try {
            // Dobavi trenutnu scenu
            Stage stage = (Stage) naslovField.getScene().getWindow();
            Scene scene = stage.getScene();

            // Pronađi BorderPane
            BorderPane borderPane = (BorderPane) scene.getRoot();

            // Pronađi contentPane
            StackPane contentPane = (StackPane) borderPane.getCenter();

            // Učitaj firmuPotraznje.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/newnomads/firmaPotraznje.fxml"));
            Parent potraznjeView = loader.load();

            // Očisti i dodaj u contentPane
            contentPane.getChildren().clear();
            contentPane.getChildren().add(potraznjeView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}