package com.example.newnomads;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class RegisterController {
    @FXML private TextField imeField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label message;
    @FXML private Button loginButton;

    @FXML
    private void register() throws Exception {
        String ime = imeField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        User user = new User(ime, email, password, "user"); // default role user
        if (UserDAO.registerUser(user)) {
            // Prikaz poruke
            message.setText("Registration successful! Redirecting to login...");

            // Prebacivanje na login scenu
            Stage stage = (Stage) imeField.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/newnomads/login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
        } else {
            message.setText("Registration failed!");
        }
    }

    @FXML
    private void goToLogin() throws Exception {
        Stage stage = (Stage) imeField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/newnomads/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
}
