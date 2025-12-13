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
import java.time.LocalDate;

public class RegruterUgovoriController {

    @FXML private TableView<Ugovor> ugovoriTable;
    @FXML private TableColumn<Ugovor, String> radnikCol;
    @FXML private TableColumn<Ugovor, String> firmaCol;
    @FXML private TableColumn<Ugovor, String> datumPocetkaCol;
    @FXML private TableColumn<Ugovor, String> datumKrajaCol;
    @FXML private TableColumn<Ugovor, String> statusCol;
    @FXML private Button logout;

    private ObservableList<Ugovor> ugovori = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        radnikCol.setCellValueFactory(data -> data.getValue().radnikProperty());
        firmaCol.setCellValueFactory(data -> data.getValue().firmaProperty());
        datumPocetkaCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getDatumPocetka() != null ? data.getValue().getDatumPocetka().toString() : ""
                )
        );
        datumKrajaCol.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getDatumKraja() != null ? data.getValue().getDatumKraja().toString() : ""
                )
        );
        statusCol.setCellValueFactory(data -> data.getValue().statusProperty());

        loadUgovori();
    }

    @FXML
    private void loadUgovori() {
        try (Connection conn = DB.getConnection()) {

            String sql = "SELECT r.ime, r.prezime, f.imeFirme AS firma, d.nazivDrzave AS drzava, " +
                    "u.datumPocetkaRada, u.datumKrajaRada, u.statusUgovora " +
                    "FROM ugovor u " +
                    "JOIN radnici r ON u.idRadnika = r.idRadnika " +
                    "JOIN firmeKlijenti f ON u.idFirme = f.regruterId " +
                    "JOIN drzave d ON u.drzavaRadaId = d.drzavaId";



            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            ugovori.clear();
            while (rs.next()) {
                String imePrezime = rs.getString("ime") + " " + rs.getString("prezime");
                LocalDate datumPocetka = rs.getDate("datumPocetkaRada") != null ? rs.getDate("datumPocetkaRada").toLocalDate() : null;
                LocalDate datumKraja = rs.getDate("datumKrajaRada") != null ? rs.getDate("datumKrajaRada").toLocalDate() : null;

                ugovori.add(new Ugovor(
                        rs.getInt("idUgovora"),
                        rs.getInt("idRadnika"),
                        rs.getInt("idFirme"),
                        imePrezime,
                        rs.getString("firma"),
                        datumPocetka,
                        datumKraja,
                        rs.getString("statusUgovora"),
                        rs.getString("opis")
                ));
            }
            ugovoriTable.setItems(ugovori);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        try {
            Stage stage = (Stage) ugovoriTable.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/newnomads/regruter.fxml"));
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
