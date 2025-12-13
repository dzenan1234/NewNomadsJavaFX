package com.example.newnomads;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import bazneTabele.PotraznjaRadnika;

public class RegruterPotraznjeController {

    @FXML
    private TableView<PotraznjaRadnika> potraznjeTable;

    @FXML
    private TableColumn<PotraznjaRadnika, Number> idCol;
    @FXML
    private TableColumn<PotraznjaRadnika, String> naslovCol;
    @FXML
    private TableColumn<PotraznjaRadnika, Number> firmaCol;
    @FXML
    private TableColumn<PotraznjaRadnika, String> statusCol;

    private final ObservableList<PotraznjaRadnika> potraznje = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(cellData -> cellData.getValue().idPotraznjeRadnikaProperty());
        firmaCol.setCellValueFactory(cellData -> cellData.getValue().idFirmeProperty());
        naslovCol.setCellValueFactory(cellData -> cellData.getValue().naslovPotraznjeProperty());
        statusCol.setCellValueFactory(cellData -> cellData.getValue().statusPotraznjeProperty());

        potraznjeTable.setItems(potraznje);
        loadPotraznje();
    }

    @FXML
    private void loadPotraznje() {
        try (Connection conn = DB.getConnection()) {
            String sql = "SELECT idPotraznjeRadnika, naslovPotraznje, idFirme, statusPotraznje FROM potraznjaRadnika";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            potraznje.clear();
            while (rs.next()) {
                PotraznjaRadnika p = new PotraznjaRadnika(
                        rs.getInt("idPotraznjeRadnika"),
                        null,
                        0,
                        rs.getInt("idFirme"),
                        null,
                        rs.getString("naslovPotraznje"),
                        "",
                        rs.getString("statusPotraznje"),
                        "",
                        0
                );
                potraznje.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Nazad na dashboard
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/newnomads/regruter.fxml"));
            Stage stage = (Stage) potraznjeTable.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Regruter Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Opcionalno: ako želiš direktno otvoriti druge prozore iz potražnji
    @FXML
    private void openRadnici() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/newnomads/regruter_radnici.fxml"));
            Stage stage = (Stage) potraznjeTable.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Pregled radnika");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openUgovori() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/newnomads/regruter_ugovori.fxml"));
            Stage stage = (Stage) potraznjeTable.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Ugovori");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/newnomads/login.fxml"));
            Stage stage = (Stage) potraznjeTable.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
