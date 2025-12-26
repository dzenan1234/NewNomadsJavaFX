package com.example.newnomads;

import bazneTabele.Ugovor;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class RegruterUgovoriController {

    @FXML private TableView<Ugovor> ugovoriTable;
    @FXML private TableColumn<Ugovor, String> radnikCol;
    @FXML private TableColumn<Ugovor, String> firmaCol;
    @FXML private TableColumn<Ugovor, String> datumPocetkaCol;
    @FXML private TableColumn<Ugovor, String> datumKrajaCol;
    @FXML private TableColumn<Ugovor, String> statusCol;
    @FXML private TableColumn<Ugovor, String> opisCol;
    @FXML private TableColumn<Ugovor, Void> exportCol;

    private final ObservableList<Ugovor> ugovori = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Povezivanje podataka
        radnikCol.setCellValueFactory(data -> data.getValue().radnikProperty());
        firmaCol.setCellValueFactory(data -> data.getValue().firmaProperty());

        datumPocetkaCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDatumPocetkaRada() != null
                        ? data.getValue().getDatumPocetkaRada().toString() : ""));

        datumKrajaCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDatumKrajaRada() != null
                        ? data.getValue().getDatumKrajaRada().toString() : "-"));

        statusCol.setCellValueFactory(data -> data.getValue().statusUgovoraProperty());

        opisCol.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getOpis() != null ? data.getValue().getOpis() : ""));

        // PREMIUM CENTRIRANJE KOLONA
        radnikCol.setStyle("-fx-alignment: CENTER-LEFT; -fx-padding: 0 0 0 15;");
        firmaCol.setStyle("-fx-alignment: CENTER;");
        datumPocetkaCol.setStyle("-fx-alignment: CENTER;");
        datumKrajaCol.setStyle("-fx-alignment: CENTER;");
        statusCol.setStyle("-fx-alignment: CENTER;");
        opisCol.setStyle("-fx-alignment: CENTER-LEFT; -fx-padding: 0 0 0 10;");

        setupExportColumn();
        loadUgovori();
    }

    private void setupExportColumn() {
        exportCol.setCellFactory(col -> new TableCell<>() {
            private final Button exportBtn = new Button("Export ⬇");
            private final ContextMenu menu = new ContextMenu();

            {
                exportBtn.getStyleClass().add("table-button"); // Tvoj CSS stil
                exportBtn.setMinWidth(90);

                MenuItem pdf = new MenuItem("Export kao PDF");
                pdf.setOnAction(e -> exportSelectedRow(ExportType.PDF));

                MenuItem word = new MenuItem("Export kao Word");
                word.setOnAction(e -> exportSelectedRow(ExportType.WORD));

                menu.getItems().addAll(pdf, word);

                exportBtn.setOnAction(e -> menu.show(exportBtn, Side.BOTTOM, 0, 0));
            }

            private void exportSelectedRow(ExportType type) {
                Ugovor row = getTableView().getItems().get(getIndex());
                if (row == null) return;

                // Koristi DAO za puni podatak (ID se povlači iz skrivenog polja objekta)
                Ugovor ugovor = UgovorDAO.getUgovorById(row.getIdUgovora());
                if (ugovor == null) {
                    showAlert(Alert.AlertType.ERROR, "Greška", "Ugovor nije pronađen.");
                    return;
                }

                FileChooser fc = new FileChooser();
                fc.setInitialFileName(buildFileName(ugovor, type));

                if (type == ExportType.PDF) {
                    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf"));
                } else {
                    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word (*.docx)", "*.docx"));
                }

                File out = fc.showSaveDialog(ugovoriTable.getScene().getWindow());
                if (out == null) return;

                try {
                    if (type == ExportType.PDF) PdfUgovorExporter.export(ugovor, out);
                    else WordUgovorExporter.export(ugovor, out);

                    showAlert(Alert.AlertType.INFORMATION, "Uspjeh", "Ugovor je exportovan.");
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Greška", "Neuspješan export: " + ex.getMessage());
                }
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    setGraphic(exportBtn);
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });
    }

    @FXML
    private void loadUgovori() {
        String sql = """
            SELECT u.idUgovora, u.idFirme, u.idRadnika, u.idPotraznjeRadnika,
                   u.datumPocetkaRada, u.datumKrajaRada, u.statusUgovora, u.opis,
                   r.ime, r.prezime, f.imeFirme, u.drzavaRadaId, u.datumKreiranja
            FROM ugovor u
            JOIN radnici r ON u.idRadnika = r.idRadnika
            JOIN firmeKlijenti f ON u.idFirme = f.idFirme
            ORDER BY u.datumKreiranja DESC
        """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            ugovori.clear();
            while (rs.next()) {
                ugovori.add(new Ugovor(
                        rs.getInt("idUgovora"), rs.getInt("idFirme"), rs.getInt("idRadnika"),
                        rs.getTimestamp("datumKreiranja"), rs.getInt("drzavaRadaId"),
                        rs.getDate("datumPocetkaRada"), rs.getDate("datumKrajaRada"),
                        rs.getString("statusUgovora"), rs.getString("opis"),
                        rs.getString("ime") + " " + rs.getString("prezime"),
                        rs.getString("imeFirme"), rs.getInt("idPotraznjeRadnika")
                ));
            }
            ugovoriTable.setItems(ugovori);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void dodajUgovor() {
        try {
            // 1. Pronađi glavni contentPane preko scene
            StackPane cp = (StackPane) ugovoriTable.getScene().lookup("#contentPane");

            // 2. Učitaj formu za dodavanje
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/newnomads/dodajUgovor.fxml"));
            Parent root = loader.load();

            // 3. Postavi formu u centar umjesto tabele
            cp.getChildren().setAll(root);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Greška", "Nije moguće otvoriti formu za dodavanje.");
        }
    }

    @FXML
    private void goBack() {
        try {
            // KLJUČNO: lookup dohvaća contentPane iz RegruterController-a
            StackPane cp = (StackPane) ugovoriTable.getScene().lookup("#contentPane");
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/newnomads/regruter_potraznje.fxml"));
            cp.getChildren().setAll(root);
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void logout() {
        try {
            // 1. Dohvati trenutni stage
            Stage stage = (Stage) ugovoriTable.getScene().getWindow();

            // 2. Učitaj Login FXML
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/newnomads/login.fxml"));

            // 3. Postavi novi root u POSTOJEĆU scenu (ovo sprečava skakanje prozora)
            stage.getScene().setRoot(root);

            // 4. Forsiraj Full Screen preko tvog Utils-a ili direktno
            stage.setFullScreen(true);
            // Ako imaš StageUtils.setFullScreen(stage), pozovi njega ovdje.

            stage.setTitle("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildFileName(Ugovor u, ExportType type) {
        String safeName = (u.getRadnik() != null ? u.getRadnik() : "radnik").replaceAll("[\\\\/:*?\"<>|]", "_");
        return "Ugovor_" + u.getIdUgovora() + "_" + safeName + (type == ExportType.PDF ? ".pdf" : ".docx");
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        a.showAndWait();
    }

    private enum ExportType { PDF, WORD }
}