package co.edu.uniquindio.biblioteca.utils;

import co.edu.uniquindio.biblioteca.modelo.Estudiante;

import java.io.*;
import java.util.*;

public class ArchivoEstudiantes {
    private static final String ARCHIVO_ESTUDIANTES = "src/main/java/co/edu/uniquindio/biblioteca/archivos/Estudiantes.txt";

    public static List<Estudiante> cargarEstudiantes() throws IOException {
        List<Estudiante> estudiantes = new ArrayList<>();
        File file = new File(ARCHIVO_ESTUDIANTES);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(";");
                    if (partes.length == 2) {
                        estudiantes.add(new Estudiante(partes[0], partes[1]));
                    }
                }
            }
        }
        return estudiantes;
    }

    public static void guardarEstudiantes(List<Estudiante> estudiantes) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_ESTUDIANTES))) {
            for (Estudiante est : estudiantes) {
                writer.println(est.getCodigo() + ";" + est.getContrasena());
            }
        }
    }
}

