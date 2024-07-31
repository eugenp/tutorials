package com.baeldung.introduction;

import com.baeldung.data.Contact;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route("form")
public class FormView extends HorizontalLayout {

    public FormView() {
        addBeanValidationExample();
        addCustomValidationExample();
    }

    private void addBeanValidationExample() {
        var nameField = new TextField("Name");
        var emailField = new TextField("Email");
        var phoneField = new TextField("Phone");
        var saveButton = new Button("Save");

        var binder = new BeanValidationBinder<>(Contact.class);

        binder.forField(nameField).bind(Contact::getName, Contact::setName);
        binder.forField(emailField).bind(Contact::getEmail, Contact::setEmail);
        binder.forField(phoneField).bind(Contact::getPhone, Contact::setPhone);

        var contact = new Contact("John Doe", "john@doe.com", "123-456-7890");
        binder.setBean(contact);

        saveButton.addClickListener(e -> {
            if (binder.validate().isOk()) {
                Notification.show("Saved " + contact);
            }
        });

        add(new VerticalLayout(
                new H2("Bean Validation Example"),
                nameField,
                emailField,
                phoneField,
                saveButton
        ));
    }

    private void addCustomValidationExample() {

        var nameField = new TextField("Name");
        var emailField = new TextField("Email");
        var phoneField = new TextField("Phone");
        var saveButton = new Button("Save");

        var binder = new Binder<>(Contact.class);

        binder.forField(nameField)
                .asRequired()
                .bind(Contact::getName, Contact::setName);
        binder.forField(emailField)
                .withValidator(email -> email.contains("@"), "Not a valid email address")
                .bind(Contact::getEmail, Contact::setEmail);
        binder.forField(phoneField)
                .withValidator(phone -> phone.matches("\\d{3}-\\d{3}-\\d{4}"), "Not a valid phone number")
                .bind(Contact::getPhone, Contact::setPhone);

        var contact = new Contact("John Doe", "john@doe.com", "123-456-7890");
        binder.setBean(contact);

        saveButton.addClickListener(e -> {
            if (binder.validate().isOk()) {
                Notification.show("Saved " + contact);
            }
        });

        add(new VerticalLayout(
                new H2("Custom Validation Example"),
                nameField,
                emailField,
                phoneField,
                saveButton
        ));
    }
}
