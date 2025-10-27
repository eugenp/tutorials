package com.baeldung.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class MainController implements Initializable {

    private final Logger logger;
    private final MetricsCollector metrics;
    private final String appName;

    @FXML
    private Label statusLabel;

    @FXML
    private Label appNameLabel;

    public MainController(String name) {
        this.logger = Logger.getLogger(MainController.class.getName());
        this.metrics = new MetricsCollector("dashboard-controller");
        this.appName = name;

        logger.info("DashboardController created");
        metrics.incrementCounter("controller.instances");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.appNameLabel.setText(this.appName);
        this.statusLabel.setText("App is ready!");
        logger.info("UI initialized successfully");
    }

    // Placeholder classes for demo
    static class Logger {
        private final String name;
        private Logger(String name) { this.name = name; }
        public static Logger getLogger(String name) { return new Logger(name); }
        public void info(String msg) { System.out.println("[INFO] " + msg); }
    }

    static class MetricsCollector {
        private final String source;
        public MetricsCollector(String source) { this.source = source; }
        public void incrementCounter(String key) {
            System.out.println("Metric incremented: " + key + " (source: " + source + ")");
        }
    }
}