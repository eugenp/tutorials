package com.baeldung.listview.cellfactory;

import com.baeldung.listview.Person;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class CheckboxCellFactory implements Callback<ListView<Person>, ListCell<Person>> {
    @Override
    public ListCell<Person> call(ListView<Person> param) {
        return new ListCell<Person>(){
            @Override
            public void updateItem(Person person, boolean empty) {
                super.updateItem(person, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else if (person != null) {
                    setText(null);
                    setGraphic(new CheckBox(person.getFirstName() + " " + person.getLastName()));
                } else {
                    setText("null");
                    setGraphic(null);
                }
            }
        };
    }
}
