package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.MainApp;
import co.edu.uniquindio.biblioteca.cliente.EchoTCPClient;


import co.edu.uniquindio.biblioteca.cliente.PrincipalCliente;
import co.edu.uniquindio.biblioteca.modelo.Estudiante;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

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
            System.out.println("Respuesta del servidor: " + response);


            if ("OK".equals(response)) {

                Estudiante estudianteLogueado = new Estudiante(cedula, contrasena);
                PrincipalCliente.getInstance().setEstudiante(estudianteLogueado);


                MainApp.mostrarInicio();
            } else {

                showAlert(AlertType.ERROR, "Error de Autenticación", "Credenciales incorrectas.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "Error de conexión con el servidor.");
        }
    }




    @FXML
    private void handleCambiarContrasena(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/biblioteca/CambiarContrasena.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Cambiar Contraseña");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Error", "No se pudo abrir la ventana de cambio de contraseña.");
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

