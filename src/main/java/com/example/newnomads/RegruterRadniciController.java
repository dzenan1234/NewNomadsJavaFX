package com.example.newnomads;

import bazneTabele.Radnik;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegruterRadniciController {

    @FXML private TableView<Radnik> radniciTable;
    @FXML private TableColumn<Radnik, String> imeCol, prezimeCol, drzavaCol, granaCol;
    @FXML private ComboBox<String> comboDrzava, comboGrana;
    @FXML private Button logout;

    private ObservableList<Radnik> radnici = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        imeCol.setCellValueFactory(data -> data.getValue().imeProperty());
        prezimeCol.setCellValueFactory(data -> data.getValue().prezimeProperty());

        // koristimo SimpleStringProperty za direktne stringove iz SQL-a
        drzavaCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStatus().split(" \\| ")[0])
        );
        granaCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getStatus().split(" \\| ")[1])
        );

        loadCombos();
        loadRadnici(null, null);
    }

    private void loadCombos() {
        try (Connection conn = DB.getConnection()) {
            comboDrzava.getItems().clear();
            ResultSet rsDrz = conn.prepareStatement("SELECT nazivDrzave FROM drzave").executeQuery();
            while (rsDrz.next()) comboDrzava.getItems().add(rsDrz.getString("nazivDrzave"));

            comboGrana.getItems().clear();
            ResultSet rsGrana = conn.prepareStatement("SELECT nazivGraneRada FROM granaRada").executeQuery();
            while (rsGrana.next()) comboGrana.getItems().add(rsGrana.getString("nazivGraneRada"));

        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void loadRadnici() {
        loadRadnici(null, null);
    }

    @FXML
    private void filterRadnici() {
        loadRadnici(comboDrzava.getValue(), comboGrana.getValue());
    }

    private void loadRadnici(String drzavaFilter, String granaFilter) {
        try (Connection conn = DB.getConnection()) {
            String sql = """
                SELECT r.idRadnika, r.ime, r.prezime, d.nazivDrzave AS drzava, g.nazivGraneRada AS grana
                FROM radnici r
                JOIN drzave d ON r.drzavaId = d.drzavaId
                JOIN granaRada g ON r.idGraneRada = g.idGraneRada
                WHERE (? IS NULL OR d.nazivDrzave = ?)
                  AND (? IS NULL OR g.nazivGraneRada = ?)
                """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, drzavaFilter);
            stmt.setString(2, drzavaFilter);
            stmt.setString(3, granaFilter);
            stmt.setString(4, granaFilter);

            ResultSet rs = stmt.executeQuery();

            radnici.clear();
            while (rs.next()) {
                // Spremamo nazive države i grane u status property kao "drzava|grana"
                Radnik r = new Radnik(
                        rs.getInt("idRadnika"),
                        "", // brojPasosa
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        0, // drzavaId
                        0, // granaId
                        "", // spol
                        null, null, null,
                        rs.getString("drzava") + " | " + rs.getString("grana") // status property
                );
                radnici.add(r);
            }

            radniciTable.setItems(radnici);

        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void goBack() {
        try {
            Stage stage = (Stage) radniciTable.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/newnomads/regruter.fxml"));
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) { e.printStackTrace(); }
    }
}
