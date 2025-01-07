module com.cheikh.gestionstock {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jakarta.persistence;
    requires static lombok;
    requires jdk.jfr;
    requires spring.security.crypto;
    requires org.slf4j;
    requires mysql.connector.j;
    requires org.hibernate.orm.core;
    opens com.cheikh.gestionstock to javafx.fxml;
    opens com.cheikh.gestionstock.models to org.hibernate.orm.core;
    exports com.cheikh.gestionstock;
    exports com.cheikh.gestionstock.controllers;
    opens com.cheikh.gestionstock.controllers to javafx.fxml;
}