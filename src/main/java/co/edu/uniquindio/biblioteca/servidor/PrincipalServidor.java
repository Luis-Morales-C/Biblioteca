package co.edu.uniquindio.biblioteca.servidor;

import co.edu.uniquindio.biblioteca.modelo.Estudiante;
import co.edu.uniquindio.biblioteca.utils.ArchivoEstudiantes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PrincipalServidor {
    private EchoTCPServer server;
    private List<Estudiante> listaEstudiantes;
    private ArrayList<ClienteCuenta> ListaClientesCuenta;
    private ArrayList<Cuenta> ListaCuentas;


    public static void main(String args[]) throws Exception {
        PrincipalServidor ps = new PrincipalServidor();
        ps.startServer();
        ps.agregarEstudiante("1091884","contrasena123");

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
        // Verificar si el estudiante ya existe
        if (listaEstudiantes.stream().noneMatch(est -> est.getCodigo().equals(cedula))) {
            // Agregar el nuevo estudiante a la lista
            listaEstudiantes.add(new Estudiante(cedula, contrasena));
            // Guardar los datos actualizados en el archivo
            guardarDatosEstudiantes();
        } else {
            System.err.println("El estudiante con cédula " + cedula + " ya existe.");
        }
    }
}



