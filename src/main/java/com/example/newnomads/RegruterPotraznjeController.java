package com.example.newnomads;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegruterPotraznjeController {

    @FXML private TableView<Potraznja> potraznjeTable;
    @FXML private TableColumn<Potraznja, Number> idCol;
    @FXML private TableColumn<Potraznja, String> naslovCol;
    @FXML private TableColumn<Potraznja, Number> firmaCol;
    @FXML private TableColumn<Potraznja, String> statusCol;

    private final ObservableList<Potraznja> potraznje = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(data -> data.getValue().idProperty());
        naslovCol.setCellValueFactory(data -> data.getValue().naslovProperty());
        firmaCol.setCellValueFactory(data -> data.getValue().firmaIdProperty());
        statusCol.setCellValueFactory(data -> data.getValue().statusProperty());

        loadPotraznje(); // automatski ucitaj na start
    }

    @FXML
    private void goBack() {
        try {
            Stage stage = (Stage) potraznjeTable.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/newnomads/regruter.fxml"));
            stage.setScene(new Scene(loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadPotraznje() {
        try (Connection conn = DB.getConnection()) {
            String sql = "SELECT idPotraznjeRadnika AS id, naslovPotraznje AS naslov, idFirme AS firmaId, statusPotraznje AS status FROM potraznjaRadnika";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            potraznje.clear();
            while (rs.next()) {
                potraznje.add(new Potraznja(
                        rs.getInt("id"),             // id
                        null,                        // grana
                        null,                        // brojRadnika
                        rs.getString("status"),      // status
                        rs.getString("naslov"),      // naslov
                        rs.getInt("firmaId")         // firmaId
                ));
            }

            potraznjeTable.setItems(potraznje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
