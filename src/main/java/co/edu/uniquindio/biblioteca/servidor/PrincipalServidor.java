package co.edu.uniquindio.biblioteca.servidor;

import  co.edu.uniquindio.biblioteca.modelo.Estudiante;
import co.edu.uniquindio.biblioteca.modelo.Libro;
import co.edu.uniquindio.biblioteca.utils.ArchivoEstudiantes;
import co.edu.uniquindio.biblioteca.utils.ArchivoLibros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PrincipalServidor {
    private EchoTCPServer server;
    private List<Estudiante> listaEstudiantes;
    private List<Libro> listaLibros;

    public static void main(String[] args) throws Exception {
        PrincipalServidor ps = new PrincipalServidor();
        ps.cargarDatosEstudiantes();
        ps.cargarDatosLibros();
        ps.startServer();
    }

    private void startServer() throws Exception {
        server = new EchoTCPServer(this);
        server.init();
    }

    private void cargarDatosEstudiantes() {
        try {
            listaEstudiantes = ArchivoEstudiantes.cargarEstudiantes();
        } catch (IOException e) {
            System.err.println("Error al cargar datos de estudiantes: " + e.getMessage());
            listaEstudiantes = new ArrayList<>();
        }
    }

    private void cargarDatosLibros() {
        try {
            listaLibros = ArchivoLibros.cargarLibros();
        } catch (IOException e) {
            System.err.println("Error al cargar datos de libros: " + e.getMessage());
            listaLibros = new ArrayList<>();
        }
    }

    public boolean autenticarEstudiante(String cedula, String contrasena) {
        return listaEstudiantes.stream()
                .anyMatch(est -> est.getCodigo().equals(cedula) && est.getContrasena().equals(contrasena));
    }



    public void guardarDatosEstudiantes() {
        try {
            ArchivoEstudiantes.guardarEstudiantes(listaEstudiantes);
        } catch (IOException e) {
            System.err.println("Error al guardar datos de estudiantes: " + e.getMessage());
        }
    }

    public List<Libro> consultarPorAutor(String autor) {
        return listaLibros.stream()
                .filter(libro -> libro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .toList();
    }

    public List<Libro> consultarPorGenero(String genero) {
        return listaLibros.stream()
                .filter(libro -> libro.getTema().toLowerCase().contains(genero.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Libro> consultarPorNombre(String nombre) {
        return listaLibros.stream()
                .filter(libro -> libro.getTitulo().toLowerCase().contains(nombre.toLowerCase()))
                .toList();
    }

    public boolean reservarLibro(String idLibro) {
        for (Libro libro : listaLibros) {
            if (libro.getId().equals(idLibro) && libro.isDisponible()) {
                libro.setDisponible(false);
                guardarDatosLibros();
                return true;
            }
        }
        return false;
    }

    public void guardarDatosLibros() {
        try {
            ArchivoLibros.guardarLibros(listaLibros);
        } catch (IOException e) {
            System.err.println("Error al guardar datos de libros: " + e.getMessage());
        }
    }
}
