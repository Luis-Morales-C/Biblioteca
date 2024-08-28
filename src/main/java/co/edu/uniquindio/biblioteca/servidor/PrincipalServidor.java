package co.edu.uniquindio.biblioteca.servidor;

import co.edu.uniquindio.biblioteca.modelo.Estudiante;
import co.edu.uniquindio.biblioteca.modelo.Libro;
import co.edu.uniquindio.biblioteca.utils.ArchivoEstudiantes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrincipalServidor {
    private EchoTCPServer server;
    private List<Estudiante> listaEstudiantes;
    private ArrayList<Libro> ListaLibros;
    private ArrayList<Cuenta> ListaCuentas;

    public static void main(String args[]) throws Exception {
        PrincipalServidor ps = new PrincipalServidor();
        ps.cargarDatosEstudiantes();
        ps.agregarEstudiante("1091887","123");
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

    public void guardarDatosEstudiantes() {
        try {
            ArchivoEstudiantes.guardarEstudiantes(listaEstudiantes);
        } catch (IOException e) {
            System.err.println("Error al guardar datos de estudiantes: " + e.getMessage());
        }
    }

    public boolean autenticarEstudiante(String cedula, String contrasena) {
        return listaEstudiantes.stream()
                .anyMatch(est -> est.getCodigo().equals(cedula) && est.getContrasena().equals(contrasena));
    }

    public void agregarEstudiante(String cedula, String contrasena) {
        if (listaEstudiantes.stream().noneMatch(est -> est.getCodigo().equals(cedula))) {
            listaEstudiantes.add(new Estudiante(cedula, contrasena));
            guardarDatosEstudiantes(); // Guardar después de agregar un estudiante
        } else {
            System.err.println("El estudiante con cédula " + cedula + " ya existe.");
        }
    }
}


