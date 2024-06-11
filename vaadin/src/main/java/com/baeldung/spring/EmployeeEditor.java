package com.baeldung.spring;

import com.baeldung.data.Employee;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

public class EmployeeEditor extends Composite<VerticalLayout> {

    public interface SaveListener {
        void onSave(Employee employee);
    }

    public interface DeleteListener {
        void onDelete(Employee employee);
    }

    public interface CancelListener {
        void onCancel();
    }

    private Employee employee;

    private SaveListener saveListener;
    private DeleteListener deleteListener;
    private CancelListener cancelListener;

    private final Binder<Employee> binder = new BeanValidationBinder<>(Employee.class);

public EmployeeEditor() {
    var firstName = new TextField("First name");
    var lastName = new TextField("Last name");

    var save = new Button("Save", VaadinIcon.CHECK.create());
    var cancel = new Button("Cancel");
    var delete = new Button("Delete", VaadinIcon.TRASH.create());

    binder.forField(firstName).bind("firstName");
    binder.forField(lastName).bind("lastName");

    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    save.addClickListener(e -> save());
    save.addClickShortcut(Key.ENTER);

    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    delete.addClickListener(e -> deleteListener.onDelete(employee));

    cancel.addClickListener(e -> cancelListener.onCancel());

    getContent().add(firstName, lastName, new HorizontalLayout(save, cancel, delete));
}

    private void save() {
        // Save the form into a new instance of Employee
        var updated = new Employee();
        updated.setId(employee.getId());

        if (binder.writeBeanIfValid(updated)) {
            saveListener.onSave(updated);
        }
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        binder.readBean(employee);
    }

    public void setSaveListener(SaveListener saveListener) {
        this.saveListener = saveListener;
    }

    public void setDeleteListener(DeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setCancelListener(CancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }
}
