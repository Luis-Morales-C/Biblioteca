package co.edu.uniquindio.biblioteca.utils;


import co.edu.uniquindio.biblioteca.modelo.Libro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArchivoLibros {
    private static final String ARCHIVO_LIBROS = "src/main/java/co/edu/uniquindio/biblioteca/archivos/Libros.txt";

    public static List<Libro> cargarLibros() throws IOException {
        List<Libro> libros = new ArrayList<>();
        File file = new File(ARCHIVO_LIBROS);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(";");
                    if (partes.length == 5) {
                        String id = partes[0];
                        String titulo = partes[1];
                        String autor = partes[2];
                        String tema = partes[3];
                        boolean disponible = Boolean.parseBoolean(partes[4]);
                        libros.add(new Libro(id, titulo, autor, tema, disponible));
                    }
                }
            }
        }
        return libros;
    }

    public static void guardarLibros(List<Libro> libros) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_LIBROS))) {
            for (Libro libro : libros) {
                writer.println(libro.getId() + ";" + libro.getTitulo() + ";" + libro.getAutor() + ";" + libro.getTema() + ";" + libro.isDisponible());
            }
        }
    }
}
