package com.baeldung.controller;

package com.baeldung.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ExampleController implements Initializable {
    private final String appName;

    @FXML
    private Label appNameLabel;

    public ExampleController(String name) {
        this.appName = name;
    }

    @Override
    public void initialize(URL location, ResourceBundle res) {
        this.appNameLabel.setText(this.appName);
    }
}