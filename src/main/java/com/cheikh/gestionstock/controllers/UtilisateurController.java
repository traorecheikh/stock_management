package com.cheikh.gestionstock.controllers;

import com.cheikh.gestionstock.HelloApplication;
import com.cheikh.gestionstock.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import static com.cheikh.gestionstock.controllers.RegisterController.us;

public class UtilisateurController {

    @FXML
    private TableColumn<Long, User> id;
    @FXML
    private TableColumn<String, User> prenom;
    @FXML
    private TableColumn<String, User> nom;
    @FXML
    private TableColumn<String, User> email;
    @FXML
    private TableView<User> table;
    @FXML
    public void initialize(){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        LoadUsers();
    }

    public void LoadUsers(){
        List<User> Liste = us.GetUsers();
        ObservableList<User> userObservableList = FXCollections.observableList(Liste);
        table.setItems(userObservableList);
    }
    @FXML
    public void retourClick() throws IOException {
        Stage stage = (Stage) table.getScene().getWindow();
        HelloApplication.change(stage,"dashboard");
    }
    @FXML
    public void ajouterClick(){

    }

    @FXML
    public void supprimerClick(){
        User user = table.getSelectionModel().getSelectedItem();
        us.deleteUser(user);
        LoadUsers();
    }
}
