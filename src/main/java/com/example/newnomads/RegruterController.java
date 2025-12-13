package com.example.newnomads;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Controller za Regruter dashboard.
 * Omogućava pregled potražnji, radnika i ugovora,
 * te navigaciju i logout.
 */
public class RegruterController {

    @FXML private Button logout;

    // Potražnje TableView
    @FXML private TableView<Potraznja> potraznjaTable;
    @FXML private TableColumn<Potraznja, String> granaCol;
    @FXML private TableColumn<Potraznja, Integer> brojCol;
    @FXML private TableColumn<Potraznja, String> statusCol;

    private final ObservableList<Potraznja> potraznje = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Postavi TableView kolone
        if (potraznjaTable != null) {
            granaCol.setCellValueFactory(data -> data.getValue().granaProperty());
            brojCol.setCellValueFactory(data -> data.getValue().brojRadnikaProperty().asObject());
            statusCol.setCellValueFactory(data -> data.getValue().statusProperty());
            loadPotraznje();
        }
    }

    // Otvori Potražnje FXML
    @FXML
    private void openPotraznje() {
        loadView("regruter_potraznje.fxml");
    }

    // Otvori Radnike FXML
    @FXML
    private void openRadnici() {
        loadView("regruter_radnici.fxml");
    }

    // Otvori Ugovore FXML
    @FXML
    private void openUgovori() {
        loadView("regruter_ugovori.fxml");
    }

    // Logout na login ekran
    @FXML
    private void logout() {
        loadView("login.fxml");
    }

    // Pomoćna metoda za load scene
    private void loadView(String fxml) {
        try {
            Stage stage = (Stage) logout.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/newnomads/" + fxml));
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadPotraznje() {
        try (Connection conn = DB.getConnection()) {
            String sql = "SELECT idPotraznjeRadnika AS id, naslovPotraznje AS naslov, idFirme AS firmaId, statusPotraznje AS status " +
                    "FROM potraznjaRadnika";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            potraznje.clear();
            while (rs.next()) {
                potraznje.add(new Potraznja(
                        rs.getInt("id"),
                        null,                 // grana
                        null,                 // brojRadnika
                        rs.getString("status"),
                        rs.getString("naslov"),
                        rs.getInt("firmaId")
                ));
            }

            potraznjaTable.setItems(potraznje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
