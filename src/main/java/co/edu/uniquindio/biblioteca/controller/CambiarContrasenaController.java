package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.cliente.EchoTCPClient;
import co.edu.uniquindio.biblioteca.cliente.PrincipalCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class CambiarContrasenaController {

    @FXML
    private TextField cedulaInput;

    @FXML
    private TextField antiguaContrasena;

    @FXML
    private TextField nuevaContrasena;


    @FXML
    void cambiarContrasena(ActionEvent event) {
        String cedula = cedulaInput.getText();
        String contrasenaActual = antiguaContrasena.getText();
        String nueva = nuevaContrasena.getText();

        if (cedula.isEmpty() || contrasenaActual.isEmpty() || nueva.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Por favor, complete todos los campos.");
            return;
        }

        try {
            EchoTCPClient cliente = PrincipalCliente.getInstance().getCliente();

            cliente.enviarMensaje("cambiarContrasena;" + cedula + ";" + contrasenaActual + ";" + nueva);

            String response = cliente.leerMensaje();


            if ("OK".equals(response)) {
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Contraseña cambiada correctamente.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo conectar con el servidor.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
