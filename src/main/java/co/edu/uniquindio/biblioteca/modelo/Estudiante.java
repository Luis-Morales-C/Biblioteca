package co.edu.uniquindio.biblioteca.modelo;

import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    private String codigo;
    private String contrasena;
    private List<Libro> librosReservados;



    public Estudiante(String codigo, String contrasena) {
        this.codigo = codigo;
        this.contrasena = contrasena;
        this.librosReservados = new ArrayList<>();
    }

    public void agregarLibroReservado(Libro libro) {
        if (!librosReservados.contains(libro)) {
            librosReservados.add(libro);
        }

    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public List<Libro> getLibrosReservados() {
        return librosReservados;
    }

    public void setLibrosReservados(List<Libro> librosReservados) {
        this.librosReservados = librosReservados;
    }
}
