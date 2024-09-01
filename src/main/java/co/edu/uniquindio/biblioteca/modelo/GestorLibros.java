package co.edu.uniquindio.biblioteca.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorLibros {
    private List<Libro> libros;

    public GestorLibros() {
        this.libros = new ArrayList<>();
    }


    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }


    public Libro consultarPorId(String id) {
        for (Libro libro : libros) {
            if (libro.getId().equalsIgnoreCase(id)) {
                return libro;
            }
        }
        return null;
    }


    public List<Libro> consultarPorNombre(String titulo) {
        return libros.stream()
                .filter(libro -> libro.getTitulo().equalsIgnoreCase(titulo))
                .collect(Collectors.toList());
    }

    public List<Libro> consultarPorAutor(String autor) {
        return libros.stream()
                .filter(libro -> libro.getAutor().equalsIgnoreCase(autor))
                .collect(Collectors.toList());
    }

    public void mostrarLibros() {
        libros.forEach(System.out::println);
    }
}
