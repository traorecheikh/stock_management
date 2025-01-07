module com.cheikh.gestionstock {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jakarta.persistence;
    requires static lombok;
    opens com.cheikh.gestionstock to javafx.fxml;
    exports com.cheikh.gestionstock;
    exports com.cheikh.gestionstock.controllers;
    opens com.cheikh.gestionstock.controllers to javafx.fxml;
}