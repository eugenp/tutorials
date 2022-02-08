package com.baeldung.button.eventhandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/button_event-handler.fxml"));
        Pane page = loader.load();

        primaryStage.setTitle("Button event handler");
        primaryStage.setScene(new Scene(page));
        primaryStage.show();
    }
}
