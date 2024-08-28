package co.edu.uniquindio.biblioteca.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType; // Importar AlertType
import javafx.scene.control.TextField;
import co.edu.uniquindio.biblioteca.cliente.EchoTCPClient;

public class LoginController {

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtContrasena;

    @FXML
    void loginAction(ActionEvent event) {
        String cedula = txtCedula.getText();
        String contrasena = txtContrasena.getText();

    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
