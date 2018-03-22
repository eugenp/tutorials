package com.baeldung.javafx.view;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SearchController {

    public static final int PAGE_ITEMS_COUNT = 30;

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Pagination pagination;

    private ObservableList<Image> masterData = FXCollections.observableArrayList();


    public SearchController() {

    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * <p>
     * Initializes the table columns and sets up sorting and filtering.
     */
    @FXML
    private void initialize() {

        // search panel
        searchButton.setText("Search");
        searchButton.setOnAction(event -> loadData());

        searchField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                loadData();
            }
        });

        pagination.setPageFactory(SearchController.this::createPage);
    }

    private Node createPage(Integer pageIndex) {

        VBox iconContainer = new VBox();

        //iconContainer.getChildren().add(myGrid);

        return iconContainer;
    }

    private void loadData() {

        String searchText = searchField.getText();

        Task<List<String>> task = new Task<List<String>>() {
            @Override
            protected List<String> call() throws Exception {
                updateMessage("Loading images");

                List<String> result = new ArrayList<>();

                return result;
            }
        };

//        task.setOnRunning((e) -> loadingDialog.show());
        task.setOnSucceeded(event -> {
            List<String> data = task.getValue();
            data.forEach(url -> masterData.add(new Image(url)));
            pagination.setVisible(true);
            pagination.setPageCount(masterData.size() / PAGE_ITEMS_COUNT);
        });

        Thread th = new Thread(task);

        th.setDaemon(true);

        th.start();
    }

}
