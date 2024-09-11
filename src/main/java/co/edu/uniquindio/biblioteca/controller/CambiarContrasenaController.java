package co.edu.uniquindio.biblioteca.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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


    private static final String RUTA_ARCHIVO = "src\\main\\java\\co\\edu\\uniquindio\\biblioteca\\archivos\\Estudiantes.txt";

    @FXML
    public void cambiarContrasena(ActionEvent event) {
        String cedula = cedulaInput.getText().trim();
        String contrasenaActual = antiguaContrasena.getText().trim();
        String nueva = nuevaContrasena.getText().trim();

        if (cedula.isEmpty() || contrasenaActual.isEmpty() || nueva.isEmpty()) {
            System.out.println("Todos los campos deben estar llenos.");
            return;
        }

        try {
            // Validamos si la contraseña actual es correcta antes de cambiarla
            boolean cambioExitoso = modificarContrasena(cedula, contrasenaActual, nueva);
            if (cambioExitoso) {
                System.out.println("Contraseña cambiada exitosamente.");
            } else {
                System.out.println("Error: La cédula o contraseña actual no coinciden.");
            }
        } catch (IOException e) {
            System.out.println("Error al cambiar la contraseña: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean modificarContrasena(String cedula, String contrasenaActual,String nuevaContrasena) throws IOException {
        // Leer todas las líneas del archivo
        List<String> lineas = Files.readAllLines(Paths.get(RUTA_ARCHIVO));
        List<String> nuevasLineas = new ArrayList<>();

        // Bandera para verificar si se encontró la cédula
        boolean encontrado = false;

        // Iterar sobre cada línea para encontrar la cédula
        for (String linea : lineas) {
            String[] datos = linea.split(";");
            if (datos[0].equals(cedula)) {
                // Si la cédula coincide, cambiar la contraseña
                nuevasLineas.add(cedula + ";" + nuevaContrasena);
                encontrado = true;
            } else {
                // Si no coincide, mantener la línea original
                nuevasLineas.add(linea);
            }
        }

        if (encontrado) {
            Files.write(Paths.get(RUTA_ARCHIVO), nuevasLineas);
            System.out.println("Contraseña modificada exitosamente.");
        } else {
            System.out.println("Cédula no encontrada.");
        }
        return encontrado;
    }
}