package co.edu.uniquindio.biblioteca.cliente;

import co.edu.uniquindio.biblioteca.MainApp;
import co.edu.uniquindio.biblioteca.modelo.Estudiante;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.Stage;

public class PrincipalCliente extends Application {

    private Estudiante estudiante;
    private MainApp mainApp = new MainApp();
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

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
}



