module co.edu.uniquindio.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens co.edu.uniquindio.biblioteca.controller to javafx.fxml;
    opens co.edu.uniquindio.biblioteca to javafx.graphics, javafx.fxml;
    opens co.edu.uniquindio.biblioteca.cliente to javafx.graphics, javafx.fxml;

    exports co.edu.uniquindio.biblioteca;
}
