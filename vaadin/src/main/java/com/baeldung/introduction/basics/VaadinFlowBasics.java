package com.baeldung.introduction.basics;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

// The @Route annotation defines the URL path for the view
// Any component, most commonly a layout, can be used as a view
@Route("basics")
public class VaadinFlowBasics extends VerticalLayout {
    public VaadinFlowBasics() {

        // Add components to the layout with the add method
        add(new H1("Vaadin Flow Basics"));

        // Components are Java objects
        var textField = new TextField("Name");
        var button = new Button("Click me");

        // Layouts define the structure of the UI
        var verticalLayout = new VerticalLayout(
                new Button("Top"),
                new Button("Middle"),
                new Button("Bottom")
        );
        add(verticalLayout);

        var horizontalLayout = new HorizontalLayout(
                new Button("Left"),
                new Button("Center"),
                new Button("Right")
        );
        add(horizontalLayout);

        // Layouts can be nested for more complex structures
        var nestedLayout = new VerticalLayout(
                new HorizontalLayout(new Button("Top Left"), new Button("Top Right")),
                new HorizontalLayout(new Button("Bottom Left"), new Button("Bottom Right"))
        );
        add(nestedLayout);

        add(new RouterLink("Example layout", ExampleLayout.class));

        // Use RouterLink to navigate to other views
        var link = new RouterLink("Hello world view", HelloWorldView.class);
        add(link);

        // Use events to react to user input
        var nameField = new TextField("Your name");
        var helloButton = new Button("Say hello");
        helloButton.addClickListener(e -> {
            Notification.show("Hello, " + nameField.getValue());
        });
        add(nameField, helloButton);

    }
}
