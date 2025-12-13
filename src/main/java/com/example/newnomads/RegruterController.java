package com.example.newnomads;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class RegruterController {

    @FXML
    private void openPotraznje(ActionEvent event) {
        switchScene(event, "/com/example/newnomads/regruter_potraznje.fxml", "Potražnje za radnicima");
    }

    @FXML
    private void openRadnici(ActionEvent event) {
        switchScene(event, "/com/example/newnomads/regruter_radnici.fxml", "Pregled radnika");
    }

    @FXML
    private void openUgovori(ActionEvent event) {
        switchScene(event, "/com/example/newnomads/regruter_ugovori.fxml", "Ugovori");
    }

    @FXML
    private void logout(ActionEvent event) {
        switchScene(event, "/com/example/newnomads/login.fxml", "Login");
    }

    private void switchScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
