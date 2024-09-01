package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.cliente.EchoTCPClient;
import co.edu.uniquindio.biblioteca.cliente.PrincipalCliente;
import co.edu.uniquindio.biblioteca.modelo.Libro;
import co.edu.uniquindio.biblioteca.utils.ArchivoLibros;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InicioController {

    private ObservableList<Libro> listaLibros;
    private EchoTCPClient cliente;
    private PrincipalCliente principalCliente;

    @FXML
    private Button btnConsultarAutor;

    @FXML
    private Button btnConsultarGenero;

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
    private TableColumn<Libro, String > colGenero;

    @FXML
    private Label labelRespuesta;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtGenero;

    @FXML
    private TextField txtNombre;

    @FXML
    void reservar(ActionEvent event) {
        try {
            Libro libroSeleccionado = tableLibros.getSelectionModel().getSelectedItem();
            if (libroSeleccionado != null) {
                String idLibro = libroSeleccionado.getId();
                cliente.enviarMensaje("reservar;" + idLibro);
                String respuesta = cliente.leerMensaje();
                if ("Reserva exitosa".equals(respuesta)) {
                    labelRespuesta.setText("Libro reservado exitosamente.");
                    cargarLibrosEnTabla();
                } else {
                    labelRespuesta.setText("Error al reservar el libro.");
                }
            } else {
                labelRespuesta.setText("Seleccione un libro para reservar.");
            }
        } catch (IOException e) {
            labelRespuesta.setText("Error de comunicación con el servidor.");
        }
    }


    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("tema"));
        colDisponible.setCellValueFactory(new PropertyValueFactory<>("disponible"));

        cargarLibrosEnTabla();


        cliente = PrincipalCliente.getInstance().getCliente();
    }

    private void cargarLibrosEnTabla() {
        try {
            List<Libro> libros = ArchivoLibros.cargarLibros();
            listaLibros = FXCollections.observableArrayList(libros);
            tableLibros.setItems(listaLibros);
        } catch (IOException e) {
            System.err.println("Error al cargar datos de libros: " + e.getMessage());
            listaLibros = FXCollections.observableArrayList();
            tableLibros.setItems(listaLibros);
        }
    }

    private void actualizarTablaConRespuesta(String respuesta) {
        System.out.println("Respuesta recibida para actualizar la tabla: " + respuesta);  // Agrega un mensaje para depuración
        String[] lineas = respuesta.split("\n");
        List<Libro> libros = new ArrayList<>();
        for (String linea : lineas) {
            String[] partes = linea.split(";");
            if (partes.length == 5) {
                String id = partes[0];
                String titulo = partes[1];
                String autor = partes[2];
                String tema = partes[3];
                boolean disponible = "Disponible".equals(partes[4]);
                libros.add(new Libro(id, titulo, autor, tema, disponible));
            }
        }
        listaLibros = FXCollections.observableArrayList(libros);
        tableLibros.setItems(listaLibros);
    }

    @FXML
    void ConsultarPorAutor(ActionEvent event) {
        try {
            String autor = txtAutor.getText();
            cliente.enviarMensaje("consultarAutor;" + autor);  // Asegúrate de incluir el delimitador
            String respuesta = cliente.leerMensaje();
            actualizarTablaConRespuesta(respuesta);
        } catch (IOException e) {
            labelRespuesta.setText("Error de comunicación con el servidor.");
        }
    }

    @FXML
    void ConsultarPorGenero(ActionEvent event) {
        try {
            String genero = txtGenero.getText();
            cliente.enviarMensaje("consultarGenero;" + genero);  // Asegúrate de incluir el delimitador
            String respuesta = cliente.leerMensaje();
            actualizarTablaConRespuesta(respuesta);
        } catch (IOException e) {
            labelRespuesta.setText("Error de comunicación con el servidor.");
        }
    }

    @FXML
    void consultarPorNombre(ActionEvent event) {
        try {
            String nombre = txtNombre.getText();
            cliente.enviarMensaje("consultarNombre;" + nombre);  // Asegúrate de incluir el delimitador
            String respuesta = cliente.leerMensaje();
            actualizarTablaConRespuesta(respuesta);
        } catch (IOException e) {
            labelRespuesta.setText("Error de comunicación con el servidor.");
        }
    }

}

