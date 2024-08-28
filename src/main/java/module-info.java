module co.edu.uniquindio.biblioteca {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.biblioteca to javafx.fxml;
    exports co.edu.uniquindio.biblioteca;
}