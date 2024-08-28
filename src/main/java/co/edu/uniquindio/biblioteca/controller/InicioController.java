package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.modelo.Libro;
import co.edu.uniquindio.biblioteca.utils.ArchivoLibros;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class InicioController {

    private ObservableList<Libro> listaLibros;

    @FXML
    private Button btnConsultarAutor;

    @FXML
    private Button btnConsultarId;

    @FXML
    private Button btnConsultarNombre;

    @FXML
    private Button btnReservar;

    @FXML
    private TableView<Libro> tableLibros;

    @FXML
    private TableColumn<Libro, String> colAutor;

    @FXML
    private TableColumn<Libro, Boolean> colDisponible;

    @FXML
    private TableColumn<Libro, String> colId;

    @FXML
    private TableColumn<Libro, String> colTitulo;

    @FXML
    private Label labelRespuesta;


    public void initialize() {
        // Configurar las columnas con los atributos de la clase Libro
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        // Cargar los datos de los libros
        cargarLibrosEnTabla();
    }

    private void cargarLibrosEnTabla() {
        try {

            List<Libro> libros = ArchivoLibros.cargarLibros();
            listaLibros = FXCollections.observableArrayList(libros);


            tableLibros.setItems(listaLibros);
        } catch (IOException e) {
            System.err.println("Error al cargar datos de libros: " + e.getMessage());
            listaLibros = FXCollections.observableArrayList();
        }
    }
}

