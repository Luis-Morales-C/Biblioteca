package co.edu.uniquindio.biblioteca;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MainApp extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        copiarArchivoDeRecursos();


        showLoginScreen();
    }


    private void copiarArchivoDeRecursos() throws IOException {
        Path archivoDestino = Paths.get(System.getProperty("user.home"), "Estudiantes.txt");

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("co/edu/uniquindio/biblioteca/archivos/Estudiantes.txt");
        if (inputStream == null) {
            System.out.println("El archivo Estudiantes.txt no fue encontrado en el classpath.");
            return;
        }

        if (!Files.exists(archivoDestino)) {
            Files.copy(inputStream, archivoDestino, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Archivo Estudiantes.txt copiado a: " + archivoDestino.toString());
        } else {
            System.out.println("Archivo Estudiantes.txt ya existe en: " + archivoDestino.toString());
        }
    }

    public static void showLoginScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showPasswordScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void mostrarInicio() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        primaryStage.setScene(scene);
    }


    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
