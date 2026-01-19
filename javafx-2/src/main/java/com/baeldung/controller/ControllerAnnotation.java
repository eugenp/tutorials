package com.baeldung.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerAnnotation {
    private final String appName;

    @FXML
    private Label appNameLabel;

    public ControllerAnnotation(String name) {
        this.appName = name;
    }

    @FXML
    public void initialize() {
        this.appNameLabel.setText(this.appName);
    }
}