package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.cliente.EchoTCPClient;
import co.edu.uniquindio.biblioteca.cliente.PrincipalCliente;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private TextField antiguaContrasena;

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnDevolver;

    @FXML
    private TextField cedulaInput;

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

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    EchoTCPClient cliente = PrincipalCliente.getInstance().getCliente();
                    cliente.enviarMensaje("cambiarContrasena;" + cedula + ";" + contrasenaActual + ";" + nueva);
                    String response = cliente.leerMensaje();

                    Platform.runLater(() -> {
                        if ("OK".equals(response)) {
                            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Contraseña cambiada correctamente.");
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Error", response);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Platform.runLater(() -> showAlert(Alert.AlertType.ERROR, "Error", "No se pudo conectar con el servidor."));
                }
                return null;
            }
        };

        new Thread(task).start();
    }
    @FXML
    void devolver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/biblioteca/Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de cambio de contraseña.");
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
