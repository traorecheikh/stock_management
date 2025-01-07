package com.cheikh.gestionstock.controllers;

import com.cheikh.gestionstock.HelloApplication;
import com.cheikh.gestionstock.services.UserServices;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;

import static com.cheikh.gestionstock.services.EntityManagerUtils.getEM;
import static com.cheikh.gestionstock.services.UserServices.checkSession;

public class DashboardController {

    private UserServices us = new UserServices();

    @FXML
    private Label idText;

    @FXML
    public void initialize() throws IOException {
        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) idText.getScene().getWindow();

                if (checkSession() == null) {
                    getEM();
                    HelloApplication.change(stage, "register");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
