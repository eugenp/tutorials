package com.baeldung.controller;

package com.baeldung.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ExampleController implements Initializable {
    private final String appName;

    @FXML
    private Label appNameLabel;

    public ExampleController(String name) {
        this.appName = name;
    }

    @FXML
    public void initialize() {
        this.appNameLabel.setText(this.appName);
    }
}