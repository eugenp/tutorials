package com.baeldung.introduction.basics;

import com.baeldung.data.Contact;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("example-layout")
public class ExampleLayout extends SplitLayout {

    public ExampleLayout() {
        var grid = new Grid<>(Contact.class);
        grid.setColumns("name", "email", "phone");
        grid.setItems(List.of(
                new Contact("John Doe", "john@doe.com", "123 456 789"),
                new Contact("Jane Doe", "jane@doe.com", "987 654 321")
        ));

        var form = new VerticalLayout();

        var nameField = new TextField("Name");
        var emailField = new TextField("Email");
        var phoneField = new TextField("Phone");
        var saveButton = new Button("Save");
        var cancelButton = new Button("Cancel");

        form.add(
                nameField,
                emailField,
                phoneField,
                new HorizontalLayout(saveButton, cancelButton)
        );

        setSizeFull();
        setSplitterPosition(70);
        addToPrimary(grid);
        addToSecondary(form);
    }
}
