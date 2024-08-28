package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.MainApp;
import co.edu.uniquindio.biblioteca.cliente.EchoTCPClient;
import co.edu.uniquindio.biblioteca.cliente.PrincipalCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtCedula;

    @FXML
    private TextField txtContrasena;

    @FXML
    void loginAction(ActionEvent event) {
        String cedula = txtCedula.getText();
        String contrasena = txtContrasena.getText();
        if (cedula.isEmpty() || contrasena.isEmpty()) {
            showAlert(AlertType.ERROR, "Error de Autenticación", "Por favor, ingrese cédula y contraseña.");
            return;
        }

        try {
            EchoTCPClient cliente = PrincipalCliente.getInstance().getCliente();
            cliente.enviarMensaje("autenticar;" + cedula + ";" + contrasena);
            String response = cliente.leerMensaje();
            System.out.println("Respuesta del servidor:" + response);

            if ("OK".equals(response)) {
                MainApp.mostrarInicio();
            } else {
                showAlert(AlertType.ERROR, "Error de Autenticación", "Credenciales incorrectas.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Error de conexión con el servidor.");
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
