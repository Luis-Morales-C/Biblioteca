package co.edu.uniquindio.biblioteca.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCPServer {
    public static final int PORT = 3400;
    private ServerSocket listener;
    private Socket serverSideSocket;
    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;
    private PrincipalServidor serv;

    public EchoTCPServer(PrincipalServidor ps) {
        serv = ps;
        System.out.println("Servidor ejecutándose en el puerto: " + PORT);
    }

    public void init() throws Exception {
        listener = new ServerSocket(PORT);
        serverSideSocket = listener.accept();
        createStreams(serverSideSocket);
        protocol(serverSideSocket);
    }

    public void protocol(Socket socket) throws IOException {
        String message;
        while ((message = fromNetwork.readLine()) != null) {
            System.out.println("El Cliente dice: " + message);


            String[] partes = message.split(";");
            if (partes.length == 3 && "autenticar".equals(partes[0])) {
                String cedula = partes[1];
                String contrasena = partes[2];

                if (serv.autenticarEstudiante(cedula, contrasena)) {
                    responder("OK");
                } else {
                    responder("Error");
                }
            }
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
