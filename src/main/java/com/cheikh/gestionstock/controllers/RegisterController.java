package com.cheikh.gestionstock.controllers;

import com.cheikh.gestionstock.HelloApplication;
import com.cheikh.gestionstock.models.User;
import com.cheikh.gestionstock.services.UserServices;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    public static UserServices us = new UserServices();
    private Stage stage;
    @FXML
    public TextField prenom;
    @FXML
    public TextField nom;
    @FXML
    public TextField email;
    @FXML
    public PasswordField password;

    @FXML
    public void validerClick() throws IOException {
        String prenom = this.prenom.getText();
        String nom = this.nom.getText();
        String email = this.email.getText();
        String password = this.password.getText();
        User user = User.builder().prenom(prenom).nom(nom).email(email).password(password).build();
        int result = us.Register(user);
        if(result == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Inscription Reussi");
            alert.show();
            stage = (Stage) this.prenom.getScene().getWindow();
            HelloApplication.change(stage,"login");
        } else if (result == 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("l'email existe deja");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur durant l'inscription");
            alert.showAndWait();
        }
    }
    @FXML
    public void loginClick() throws IOException {
        stage  = (Stage) this.email.getScene().getWindow();
        HelloApplication.change(stage,"login");
    }
}
