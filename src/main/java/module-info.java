module co.edu.uniquindio.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;


    opens co.edu.uniquindio.biblioteca.controller to javafx.fxml;
    opens co.edu.uniquindio.biblioteca to javafx.graphics, javafx.fxml;
    opens co.edu.uniquindio.biblioteca.cliente to javafx.graphics, javafx.fxml;
    opens co.edu.uniquindio.biblioteca.modelo to javafx.base;

    exports co.edu.uniquindio.biblioteca;
}
