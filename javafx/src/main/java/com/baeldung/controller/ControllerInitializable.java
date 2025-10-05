package com.baeldung.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ControllerInitializable implements Initializable {
    private final String appName;

    @FXML
    private Label appNameLabel;

    public ControllerInitializable(String name) {
        this.appName = name;
    }

    @Override
    public void initialize(URL location, ResourceBundle res) {
        this.appNameLabel.setText(this.appName);
    }
}