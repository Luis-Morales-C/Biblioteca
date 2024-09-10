package co.edu.uniquindio.biblioteca.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class CambiarContrasenaController {

    @FXML
    private TextField cedulaInput;

    @FXML
    private TextField antiguaContrasena;

    @FXML
    private TextField nuevaContrasena;

    // Método que se ejecuta cuando se presiona el botón "Aceptar"
    @FXML
    public void cambiarContrasena(ActionEvent event) {
        String cedula = cedulaInput.getText();
        String contrasenaActual = antiguaContrasena.getText();
        String nueva = nuevaContrasena.getText();

        if (cedula.isEmpty() || contrasenaActual.isEmpty() || nueva.isEmpty()) {
            System.out.println("Todos los campos deben estar llenos.");
            return;
        }

        try {
            // Llamar al método para cambiar la contraseña en el archivo
            boolean cambioExitoso = cambiarContrasenaEnArchivo(cedula, contrasenaActual, nueva);
            if (cambioExitoso) {
                System.out.println("Contraseña cambiada exitosamente.");
            } else {
                System.out.println("Error: La cédula o contraseña actual no coinciden.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cambiar la contraseña en el archivo de estudiantes
    private boolean cambiarContrasenaEnArchivo(String cedula, String contrasenaActual, String nuevaContrasena) throws IOException {
        String archivo = "co/edu/uniquindio/biblioteca/archivos/Estudiantes.txt";

        List<String> lineas = Files.readAllLines(Paths.get(archivo));

        // Usamos un AtomicBoolean para poder modificar el valor dentro de la lambda
        AtomicBoolean encontrado = new AtomicBoolean(false);

        List<String> nuevasLineas = lineas.stream().map(linea -> {
            String[] partes = linea.split(";");
            if (partes[0].equals(cedula) && partes[1].equals(contrasenaActual)) {
                encontrado.set(true);  // Marcar como encontrado
                return partes[0] + ";" + nuevaContrasena;  // Actualiza la contraseña
            }
            return linea;
        }).collect(Collectors.toList());

        if (encontrado.get()) {
            Files.write(Paths.get(archivo), nuevasLineas, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        }

        return encontrado.get();
    }
}
