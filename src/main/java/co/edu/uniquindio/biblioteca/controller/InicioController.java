package co.edu.uniquindio.biblioteca.controller;

import co.edu.uniquindio.biblioteca.cliente.EchoTCPClient;
import co.edu.uniquindio.biblioteca.cliente.PrincipalCliente;
import co.edu.uniquindio.biblioteca.modelo.Estudiante;
import co.edu.uniquindio.biblioteca.modelo.Libro;
import co.edu.uniquindio.biblioteca.utils.ArchivoLibros;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InicioController {
    private static final String RUTA_LIBROS = "src\\main\\java\\co\\edu\\uniquindio\\biblioteca\\archivos\\Libros.txt";
    private ObservableList<Libro> listaLibros ;
    public ArrayList<Libro> libros= new ArrayList<>();
    private EchoTCPClient cliente;
    private Estudiante estudiante;
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
                if (!libroSeleccionado.isDisponible()) {
                    labelRespuesta.setText("El libro seleccionado no está disponible.");
                    return;
                }

                Estudiante estudiante = PrincipalCliente.getInstance().getEstudiante();

                if (estudiante == null) {
                    labelRespuesta.setText("No hay estudiante logueado.");
                    return;
                }

                String idLibro = libroSeleccionado.getId();
                String codigoEstudiante = estudiante.getCodigo();
                cliente.enviarMensaje("reservar;" + idLibro);
                String respuesta = cliente.leerMensaje();

                if ("Reserva exitosa".equals(respuesta)) {
                    labelRespuesta.setText("Libro reservado exitosamente.");
                    libroSeleccionado.setDisponible(false);
                    estudiante.agregarLibroReservado(libroSeleccionado);
                    tableLibros.refresh();
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

        principalCliente = PrincipalCliente.getInstance();

        cliente = principalCliente.getCliente();
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

    private void actualizarTablaConRespuesta(String respuesta, String parametro, int n) {
        libros.clear();


        if (n == 1) {
            libros = buscarLibrosPorNombre2(RUTA_LIBROS, parametro);
        } else if (n == 2) {
            libros = buscarLibrosPorGenero2(RUTA_LIBROS, parametro);
        } else if (n == 3) {
            libros = buscarLibrosPorAutor2(RUTA_LIBROS, parametro);
        }


        listaLibros = FXCollections.observableArrayList(libros);
        tableLibros.setItems(listaLibros);
    }


    @FXML
    void ConsultarPorAutor(ActionEvent event) {
        try {
            int n=3;
            String autor = txtAutor.getText();
            cliente.enviarMensaje("consultarAutor;" + autor);
            String respuesta = cliente.leerMensaje();
            actualizarTablaConRespuesta(respuesta, autor,3);
        } catch (IOException e) {
            labelRespuesta.setText("Error de comunicación con el servidor.");
        }
    }

    @FXML
    void ConsultarPorGenero(ActionEvent event) {
        try {
            int n =2;
            String genero = txtGenero.getText();
            cliente.enviarMensaje("consultarGenero;" + genero);
            String respuesta = cliente.leerMensaje();
            actualizarTablaConRespuesta(respuesta, genero,2);

        } catch (IOException e) {
            labelRespuesta.setText("Error de comunicación con el servidor.");
        }
    }

    @FXML
    void consultarPorNombre(ActionEvent event) {
        try {
            int n =1;
            String nombre = txtNombre.getText();
            cliente.enviarMensaje("consultarNombre;" + nombre);
            String respuesta = cliente.leerMensaje();
            actualizarTablaConRespuesta(respuesta, nombre,1);
        } catch (IOException e) {
            labelRespuesta.setText("Error de comunicación con el servidor.");
        }
    }


    public static ArrayList<Libro> buscarLibrosPorGenero2(String rutaArchivo, String generoBuscado) {
        ArrayList<Libro> librosFiltrados = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;


            while ((linea = br.readLine()) != null) {
                String[] datosLibro = linea.split(";");

                if (datosLibro.length == 5) {
                    String id = datosLibro[0];
                    String titulo = datosLibro[1];
                    String autor = datosLibro[2];
                    String genero = datosLibro[3];
                    boolean disponibilidad = Boolean.parseBoolean(datosLibro[4]);


                    if (genero.equalsIgnoreCase(generoBuscado)) {
                        Libro libro = new Libro(id, titulo, autor, genero, disponibilidad);
                        librosFiltrados.add(libro);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(librosFiltrados+ "prueba");
        return librosFiltrados;
    }
    public static ArrayList<Libro> buscarLibrosPorAutor2(String rutaArchivo, String autorLibro) {
        ArrayList<Libro> librosFiltrados = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;


            while ((linea = br.readLine()) != null) {
                String[] datosLibro = linea.split(";");

                if (datosLibro.length == 5) {
                    String id = datosLibro[0];
                    String titulo = datosLibro[1];
                    String autor = datosLibro[2];
                    String genero = datosLibro[3];
                    boolean disponibilidad = Boolean.parseBoolean(datosLibro[4]);


                    if (autor.equalsIgnoreCase(autorLibro)) {
                        Libro libro = new Libro(id, titulo, autor, genero, disponibilidad);
                        librosFiltrados.add(libro);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return librosFiltrados;
    }
    public static ArrayList<Libro> buscarLibrosPorNombre2(String rutaArchivo, String nombreLibro) {
        ArrayList<Libro> librosFiltrados = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;


            while ((linea = br.readLine()) != null) {
                String[] datosLibro = linea.split(";");


                if (datosLibro.length == 5) {
                    String id = datosLibro[0];
                    String titulo = datosLibro[1];
                    String autor = datosLibro[2];
                    String genero = datosLibro[3];
                    boolean disponibilidad = Boolean.parseBoolean(datosLibro[4]);

                    // Comparar el nombre del libro
                    if (titulo.equalsIgnoreCase(nombreLibro)) {
                        Libro libro = new Libro(id, titulo, autor, genero, disponibilidad);
                        librosFiltrados.add(libro);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return librosFiltrados;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }
    public void setPrincipalCliente(PrincipalCliente principalCliente) {
        this.principalCliente = principalCliente;
    }
}


