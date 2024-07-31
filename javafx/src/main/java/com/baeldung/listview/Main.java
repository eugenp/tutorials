package com.baeldung.javafx.listview;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/example.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();

        primaryStage.setTitle("List View Demo");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
