package com.example.newnomads;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import bazneTabele.PotraznjaRadnika;

public class RegruterPotraznjeController {

    @FXML
    private TableView<PotraznjaRadnika> potraznjeTable;

    @FXML private TableColumn<PotraznjaRadnika, Number> idCol;
    @FXML private TableColumn<PotraznjaRadnika, String> naslovCol;
    @FXML private TableColumn<PotraznjaRadnika, Number> firmaCol;
    @FXML private TableColumn<PotraznjaRadnika, String> statusCol;
    @FXML private TableColumn<PotraznjaRadnika, String> granaCol;
    @FXML private TableColumn<PotraznjaRadnika, Void> akcijaCol;

    private final ObservableList<PotraznjaRadnika> potraznje = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(cellData -> cellData.getValue().idPotraznjeRadnikaProperty());
        firmaCol.setCellValueFactory(cellData -> cellData.getValue().idFirmeProperty());
        naslovCol.setCellValueFactory(cellData -> cellData.getValue().naslovPotraznjeProperty());
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusPotraznjeProperty());
        granaCol.setCellValueFactory(data -> data.getValue().granaProperty());
        idCol.setStyle("-fx-alignment: CENTER;");
        firmaCol.setStyle("-fx-alignment: CENTER;");
        naslovCol.setStyle("-fx-alignment: CENTER-LEFT;"); // Naslov obično ide lijevo
        statusCol.setStyle("-fx-alignment: CENTER;");
        granaCol.setStyle("-fx-alignment: CENTER;");

        dodajDugmeUTableu();

        // OVDJE JE BIO PROBLEM: Dodan idFirme u poziv
        potraznjeTable.setRowFactory(tv -> {
            TableRow<PotraznjaRadnika> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    PotraznjaRadnika p = row.getItem();
                    otvoriRadnikeSaFilterom(p.getGrana(), p.getIdFirme());
                }
            });
            return row;
        });

        potraznjeTable.setItems(potraznje);
        loadPotraznje();
    }

    private void dodajDugmeUTableu() {
        akcijaCol.setCellFactory(new Callback<>() {
            @Override
            public TableCell<PotraznjaRadnika, Void> call(final TableColumn<PotraznjaRadnika, Void> param) {
                return new TableCell<>() {
                    // Koristimo tvoju klasu 'table-button' koju smo definisali u CSS-u
                    private final Button btn = new Button("Pretraži");
                    {
                        btn.getStyleClass().add("table-button"); // Ovo vuče stil iz CSS-a
                        btn.setOnAction(event -> {
                            PotraznjaRadnika data = getTableView().getItems().get(getIndex());
                            otvoriRadnikeSaFilterom(data.getGrana(), data.getIdFirme());
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else {
                            setGraphic(btn);
                            // Centriramo dugme u ćeliji
                            setStyle("-fx-alignment: CENTER;");
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void loadPotraznje() {
        String sql = "SELECT idPotraznjeRadnika, naslovPotraznje, idFirme, statusPotraznje, grana FROM potraznjaRadnika";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            potraznje.clear();
            while (rs.next()) {
                PotraznjaRadnika p = new PotraznjaRadnika(
                        rs.getInt("idPotraznjeRadnika"),
                        null, 0,
                        rs.getInt("idFirme"),
                        null,
                        rs.getString("naslovPotraznje"),
                        "",
                        rs.getString("statusPotraznje"),
                        rs.getString("grana"),
                        0
                );
                potraznje.add(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void otvoriRadnikeSaFilterom(String nazivGrane, int idFirme) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/newnomads/regruter_radnici.fxml"));
            Parent root = loader.load();

            RegruterRadniciController radniciCtrl = loader.getController();
            radniciCtrl.filtrirajPoGrani(nazivGrane, idFirme);

            // KLJUČ: Tražimo centralni StackPane
            StackPane cp = (StackPane) potraznjeTable.getScene().lookup("#contentPane");
            if (cp != null) {
                cp.getChildren().setAll(root);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    /** ISPRAVLJENO: Sve switchScene metode sada rade unutar contentPane-a **/
    @FXML private void goBack() { updateContent("/com/example/newnomads/regruter_potraznje.fxml"); }
    @FXML private void openRadnici() { updateContent("/com/example/newnomads/regruter_radnici.fxml"); }
    @FXML private void openUgovori() { updateContent("/com/example/newnomads/regruter_ugovori.fxml"); }

    private void updateContent(String fxmlPath) {
        try {
            StackPane cp = (StackPane) potraznjeTable.getScene().lookup("#contentPane");
            if (cp != null) {
                Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
                cp.getChildren().setAll(root);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void logout() {
        try {
            // Jedino Logout zapravo treba skroz promijeniti scenu (vratiti na login prozor)
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/newnomads/login.fxml"));
            Stage stage = (Stage) potraznjeTable.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setTitle("Login");
        } catch (Exception e) { e.printStackTrace(); }
    }
}