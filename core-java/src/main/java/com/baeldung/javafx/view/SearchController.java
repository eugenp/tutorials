package com.baeldung.javafx.view;


import com.baeldung.javafx.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

public class SearchController {

    public static final int PAGE_ITEMS_COUNT = 30;

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Pagination pagination;
    @FXML
    private Label searchLabel;

    private ObservableList<Person> masterData = FXCollections.observableArrayList();

    public SearchController() {
        masterData.add(new Person(5, "John", true));
        masterData.add(new Person(7, "Albert", true));
        masterData.add(new Person(11, "Monica", false));
    }

    @FXML
    private void initialize() {

        // search panel
        searchButton.setText("Search");
        searchButton.setOnAction(event -> loadData());

        searchButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");

        searchField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                loadData();
            }
        });

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchLabel.setText(newValue);
        });

        pagination.setPageFactory(SearchController.this::createPage);
    }

    private Node createPage(Integer pageIndex) {

        VBox iconContainer = new VBox();

        TableView<Person> tableView = new TableView<>(masterData);
        TableColumn id = new TableColumn("ID");
        TableColumn name = new TableColumn("NAME");
        TableColumn employed = new TableColumn("EMPLOYED");

        tableView.getColumns().addAll(id, name, employed);
        iconContainer.getChildren().add(tableView);

        return iconContainer;
    }

    private void loadData() {

        String searchText = searchField.getText();

        Task<List<Person>> task = new Task<List<Person>>() {
            @Override
            protected List<Person> call() throws Exception {
                updateMessage("Loading data");
                return masterData
                        .stream()
                        .filter(value -> value.getName().toLowerCase().contains(searchText))
                        .collect(Collectors.toList());
            }
        };

        task.setOnSucceeded(event -> {
            List<Person> data = task.getValue();
            data.forEach(p -> masterData.add(p));
            pagination.setVisible(true);
            pagination.setPageCount(masterData.size() / PAGE_ITEMS_COUNT);
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

}
