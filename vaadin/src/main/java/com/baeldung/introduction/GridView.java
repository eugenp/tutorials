package com.baeldung.introduction;

import com.baeldung.data.Contact;
import com.baeldung.data.ContactRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

@Route("grid")
public class GridView extends VerticalLayout {

    public GridView(ContactRepository contactRepository) {

        add(new H1("Using data grids"));

        // Define a grid for a Contact entity
        var grid = new Grid<Contact>();
        grid.addColumn(Contact::getName).setHeader("Name");
        grid.addColumn(Contact::getEmail).setHeader("Email");
        grid.addColumn(Contact::getPhone).setHeader("Phone");

        // There are two ways to populate the grid with data:

        // 1) In-memory with a list of items
        var contacts = contactRepository.findAll();
        grid.setItems(contacts);

        // 2) with a callback to lazily load items from a data source.
        grid.setItems(query -> {
            // Turn the page, offset, filter, sort and other info in query into a Spring Data PageRequest
            var pageRequest = VaadinSpringDataHelpers.toSpringPageRequest(query);
            return contactRepository.findAll(pageRequest).stream();
        });

        add(grid);

    }
}
