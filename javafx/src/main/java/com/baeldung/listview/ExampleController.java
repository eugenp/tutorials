package com.baeldung.listview;

import com.baeldung.listview.cellfactory.CheckboxCellFactory;
import com.baeldung.listview.cellfactory.PersonCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ExampleController implements Initializable {
    @FXML
    private ListView<Person> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Person> wordsList = FXCollections.observableArrayList();
        wordsList.add(new Person("Isaac", "Newton"));
        wordsList.add(new Person("Albert", "Einstein"));
        wordsList.add(new Person("Ludwig", "Boltzmann"));
        listView.setItems(wordsList);
    }

    public void defaultButtonClick() {
        listView.setCellFactory(null);
    }

    public void cellFactoryButtonClick() {
        listView.setCellFactory(new PersonCellFactory());
    }

    public void checkboxCellFactoryButtonClick() {
        listView.setCellFactory(new CheckboxCellFactory());
    }
}
