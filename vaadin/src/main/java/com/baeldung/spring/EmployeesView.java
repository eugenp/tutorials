package com.baeldung.spring;

import com.baeldung.data.Employee;
import com.baeldung.data.EmployeeRepository;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("employees")
public class EmployeesView extends VerticalLayout {

    private final EmployeeRepository employeeRepository;

    private final TextField filter;
    private final Grid<Employee> grid;
    private final EmployeeEditor editor;


    public EmployeesView(EmployeeRepository repo) {
        employeeRepository = repo;

        // Create components
        var addButton = new Button("New employee", VaadinIcon.PLUS.create());
        filter = new TextField();
        grid = new Grid<>(Employee.class);
        editor = new EmployeeEditor();

        // Configure components
        configureEditor();

        addButton.addClickListener(e -> editEmployee(new Employee()));

        filter.setPlaceholder("Filter by last name");
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateEmployees(e.getValue()));

        grid.setHeight("200px");
        grid.asSingleSelect().addValueChangeListener(e -> editEmployee(e.getValue()));

        // Compose layout
        var actionsLayout = new HorizontalLayout(filter, addButton);
        add(actionsLayout, grid, editor);

        // List customers
        updateEmployees("");
    }

    private void configureEditor() {
        editor.setVisible(false);

        editor.setSaveListener(employee -> {
            var saved = employeeRepository.save(employee);
            updateEmployees(filter.getValue());
            editor.setEmployee(null);
            grid.asSingleSelect().setValue(saved);
        });

        editor.setDeleteListener(employee -> {
            employeeRepository.delete(employee);
            updateEmployees(filter.getValue());
            editEmployee(null);
        });

        editor.setCancelListener(() -> {
            editEmployee(null);
        });
    }

    private void editEmployee(Employee employee) {
        editor.setEmployee(employee);

        if (employee != null) {
            editor.setVisible(true);
        } else {
            // Deselect grid
            grid.asSingleSelect().setValue(null);
            editor.setVisible(false);
        }

    }

    private void updateEmployees(String filterText) {
        if (filterText.isEmpty()) {
            grid.setItems(employeeRepository.findAll());
        } else {
            grid.setItems(employeeRepository.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }
}
