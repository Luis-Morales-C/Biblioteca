package co.edu.uniquindio.biblioteca.servidor;

import co.edu.uniquindio.biblioteca.modelo.Libro;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class EchoTCPServer {
    public static final int PORT = 1400;
    private ServerSocket listener;
    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;
    private PrincipalServidor serv;

    public EchoTCPServer(PrincipalServidor ps) {
        serv = ps;
        System.out.println("Servidor ejecutándose en el puerto: " + PORT);
    }

    public void init() throws Exception {
        listener = new ServerSocket(PORT);
        while (true) {
            Socket serverSideSocket = listener.accept();
            createStreams(serverSideSocket);
            protocol(serverSideSocket);
        }
    }

    public void protocol(Socket socket) throws IOException {
        String message;
        while ((message = fromNetwork.readLine()) != null) {
            System.out.println("El Cliente dice: " + message);
            String[] partes = message.split(";");
            procesarMensaje(partes);
        }
    }

    private void procesarMensaje(String[] partes) {
        if (partes.length == 3 && "autenticar".equals(partes[0])) {
            autenticarUsuario(partes[1], partes[2]);
        } else if (partes.length == 2 && "reservar".equals(partes[0])) {
            reservarLibro(partes[1]);
        } else if (partes.length == 2) {
            procesarConsulta(partes[0], partes[1]);
        } else {
            responder("Comando inválido");
        }
    }

    private void autenticarUsuario(String cedula, String contrasena) {
        if (serv.autenticarEstudiante(cedula, contrasena)) {
            responder("OK");
        } else {
            responder("Error");
        }
    }
    private void reservarLibro(String idLibro) {
        if (serv.reservarLibro(idLibro)) {
            responder("Reserva exitosa");
        } else {
            responder("Error al reservar el libro");
        }
    }

    private void procesarConsulta(String comando, String parametro) {
        switch (comando) {
            case "consultarAutor":
                responderLibros(serv.consultarPorAutor(parametro));
                break;
            case "consultarGenero":
                responderLibros(serv.consultarPorGenero(parametro));
                break;
            case "consultarNombre":
                responderLibros(serv.consultarPorNombre(parametro));
                break;
            default:
                responder("Comando no reconocido");
                break;
        }
    }

    private void responderLibros(List<Libro> libros) {
        if (libros.isEmpty()) {
            responder("No se encontraron libros.");
        } else {
            StringBuilder respuesta = new StringBuilder();
            for (Libro libro : libros) {
                respuesta.append(libro.getId()).append(";")
                        .append(libro.getTitulo()).append(";")
                        .append(libro.getAutor()).append(";")
                        .append(libro.getTema()).append(";")
                        .append(libro.isDisponible() ? "Disponible" : "No disponible")
                        .append("\n");
            }
            responder(respuesta.toString());
        }
    }

    private void createStreams(Socket socket) throws IOException {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void responder(String res) {
        toNetwork.println(res);
    }

}
