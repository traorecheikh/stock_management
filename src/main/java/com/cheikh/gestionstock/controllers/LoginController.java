package com.cheikh.gestionstock.controllers;

import com.cheikh.gestionstock.HelloApplication;
import com.cheikh.gestionstock.services.UserServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private static UserServices us = new UserServices();
    private Stage stage;
    @FXML
    public TextField email;
    @FXML
    public PasswordField password;

    @FXML
    public void validerClick() throws IOException {
        String email = this.email.getText();
        String password = this.password.getText();
        if (us.Login(email,password)){
            stage  = (Stage) this.email.getScene().getWindow();
            HelloApplication.change(stage,"dashboard");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("email ou mot de passe incorrect");
    }

    @FXML
    public void registerClick(ActionEvent actionEvent) throws IOException {
        stage  = (Stage) this.email.getScene().getWindow();
        HelloApplication.change(stage,"register");
    }
}
