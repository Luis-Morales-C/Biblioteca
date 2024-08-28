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
        System.out.println("Servidor ejecutandose en el puerto : " + PORT);
    }

    public void init() throws Exception {
        listener = new ServerSocket(PORT);
        serverSideSocket = listener.accept();
        createStreams(serverSideSocket);
        protocol(serverSideSocket);

    }


    public void protocol(Socket socket) throws Exception {
        String message = fromNetwork.readLine();
        System.out.println("El Cliente dice: " + message);

        String answer = "Si sr, lo escucho";

        toNetwork.println(answer);
    }

    private void createStreams(Socket socket) throws Exception {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private void responder(String res) {
        toNetwork.println(res);
    }

}
