package com.example.newnomads;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void login() {
        String email = emailField.getText();
        String password = passwordField.getText();

        User user = UserDAO.login(email, password);

        if (user != null) {
            openDashboard(user.getRole());
        } else {
            // ako želiš možeš i dalje prikazati alert
            System.out.println("Invalid email or password");
        }
    }

    private void openDashboard(String role) {
        try {
            Stage stage = (Stage) emailField.getScene().getWindow();
            FXMLLoader fxmlLoader;

            switch (role.toLowerCase()) {
                case "admin":
                    fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/newnomads/admin.fxml"));
                    break;
                case "user":
                    fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/newnomads/user.fxml"));
                    break;
                case "poslodavac":
                    fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/newnomads/poslodavac.fxml"));
                    break;
                default:
                    // ako role nije prepoznata, ostani na loginu
                    System.out.println("Unknown role: " + role);
                    return;
            }

            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRegister() throws Exception {
        Stage stage = (Stage) emailField.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/newnomads/register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }
}
