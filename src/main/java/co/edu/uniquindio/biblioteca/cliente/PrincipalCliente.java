package co.edu.uniquindio.biblioteca.cliente;

import co.edu.uniquindio.biblioteca.MainApp;
import javafx.application.Application;
import javafx.stage.Stage;


public class PrincipalCliente extends Application {

    private MainApp mainApp;

    private EchoTCPClient cliente;
    private static PrincipalCliente instance;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        cliente = new EchoTCPClient();
        cliente.init();

        mainApp.setPrimaryStage(primaryStage);
        mainApp.showLoginScreen();
    }

    public EchoTCPClient getCliente() {
        return cliente;
    }

    public static PrincipalCliente getInstance() {
        return instance;
    }
}

