package com.baeldung.introduction;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Push
@Theme("mytheme")
public class VaadinUI extends UI {

    private Label currentTime;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(true);
        final GridLayout gridLayout = new GridLayout(3, 2);
        gridLayout.setSpacing(true);
        gridLayout.setMargin(true);
        final HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.setMargin(true);
        final FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        formLayout.setMargin(true);
        final GridLayout buttonLayout = new GridLayout(3, 5);
        buttonLayout.setMargin(true);
        buttonLayout.setSpacing(true);

        final Label label = new Label();
        label.setId("Label");
        label.setValue("Label Value");
        label.setCaption("Label");
        gridLayout.addComponent(label);

        final Link link = new Link("Baeldung", new ExternalResource("http://www.baeldung.com/"));
        link.setId("Link");
        link.setTargetName("_blank");
        gridLayout.addComponent(link);

        final TextField textField = new TextField();
        textField.setId("TextField");
        textField.setCaption("TextField:");
        textField.setValue("TextField Value");
        textField.setIcon(VaadinIcons.USER);
        gridLayout.addComponent(textField);

        final TextArea textArea = new TextArea();
        textArea.setCaption("TextArea");
        textArea.setId("TextArea");
        textArea.setValue("TextArea Value");
        gridLayout.addComponent(textArea);

        final DateField dateField = new DateField("DateField", LocalDate.ofEpochDay(0));
        dateField.setId("DateField");
        gridLayout.addComponent(dateField);

        final PasswordField passwordField = new PasswordField();
        passwordField.setId("PasswordField");
        passwordField.setCaption("PasswordField:");
        passwordField.setValue("password");
        gridLayout.addComponent(passwordField);

        final RichTextArea richTextArea = new RichTextArea();
        richTextArea.setCaption("Rich Text Area");
        richTextArea.setValue("<h1>RichTextArea</h1>");
        richTextArea.setSizeFull();

        Panel richTextPanel = new Panel();
        richTextPanel.setContent(richTextArea);

        final InlineDateField inlineDateField = new InlineDateField();
        inlineDateField.setValue(LocalDate.ofEpochDay(0));
        inlineDateField.setCaption("Inline Date Field");
        horizontalLayout.addComponent(inlineDateField);

        Button normalButton = new Button("Normal Button");
        normalButton.setId("NormalButton");
        normalButton.addClickListener(e -> {
            label.setValue("CLICK");
        });
        buttonLayout.addComponent(normalButton);

        Button tinyButton = new Button("Tiny Button");
        tinyButton.addStyleName("tiny");
        buttonLayout.addComponent(tinyButton);

        Button smallButton = new Button("Small Button");
        smallButton.addStyleName("small");
        buttonLayout.addComponent(smallButton);

        Button largeButton = new Button("Large Button");
        largeButton.addStyleName("large");
        buttonLayout.addComponent(largeButton);

        Button hugeButton = new Button("Huge Button");
        hugeButton.addStyleName("huge");
        buttonLayout.addComponent(hugeButton);

        Button disabledButton = new Button("Disabled Button");
        disabledButton.setDescription("This button cannot be clicked");
        disabledButton.setEnabled(false);
        buttonLayout.addComponent(disabledButton);

        Button dangerButton = new Button("Danger Button");
        dangerButton.addStyleName("danger");
        buttonLayout.addComponent(dangerButton);

        Button friendlyButton = new Button("Friendly Button");
        friendlyButton.addStyleName("friendly");
        buttonLayout.addComponent(friendlyButton);

        Button primaryButton = new Button("Primary Button");
        primaryButton.addStyleName("primary");
        buttonLayout.addComponent(primaryButton);

        NativeButton nativeButton = new NativeButton("Native Button");
        buttonLayout.addComponent(nativeButton);

        Button iconButton = new Button("Icon Button");
        iconButton.setIcon(VaadinIcons.ALIGN_LEFT);
        buttonLayout.addComponent(iconButton);

        Button borderlessButton = new Button("BorderLess Button");
        borderlessButton.addStyleName("borderless");
        buttonLayout.addComponent(borderlessButton);

        Button linkButton = new Button("Link Button");
        linkButton.addStyleName("link");
        buttonLayout.addComponent(linkButton);

        Button quietButton = new Button("Quiet Button");
        quietButton.addStyleName("quiet");
        buttonLayout.addComponent(quietButton);

        horizontalLayout.addComponent(buttonLayout);

        final CheckBox checkbox = new CheckBox("CheckBox");
        checkbox.setValue(true);
        checkbox.addValueChangeListener(e -> checkbox.setValue(!checkbox.getValue()));
        formLayout.addComponent(checkbox);

        List<String> numbers = new ArrayList<String>();
        numbers.add("One");
        numbers.add("Ten");
        numbers.add("Eleven");
        ComboBox comboBox = new ComboBox("ComboBox");
        comboBox.setItems(numbers);
        formLayout.addComponent(comboBox);

        ListSelect listSelect = new ListSelect("ListSelect");
        listSelect.setItems(numbers);
        listSelect.setRows(2);
        formLayout.addComponent(listSelect);

        NativeSelect nativeSelect = new NativeSelect("NativeSelect");
        nativeSelect.setItems(numbers);
        formLayout.addComponent(nativeSelect);

        TwinColSelect twinColSelect = new TwinColSelect("TwinColSelect");
        twinColSelect.setItems(numbers);

        Grid<Row> grid = new Grid(Row.class);
        grid.setColumns("column1", "column2", "column3");
        Row row1 = new Row("Item1", "Item2", "Item3");
        Row row2 = new Row("Item4", "Item5", "Item6");
        List<Row> rows = new ArrayList();
        rows.add(row1);
        rows.add(row2);
        grid.setItems(rows);

        Panel panel = new Panel("Panel");
        panel.setContent(grid);
        panel.setSizeUndefined();

        Panel serverPushPanel = new Panel("Server Push");
        FormLayout timeLayout = new FormLayout();
        timeLayout.setSpacing(true);
        timeLayout.setMargin(true);
        currentTime = new Label("No TIME...");
        timeLayout.addComponent(currentTime);
        serverPushPanel.setContent(timeLayout);
        serverPushPanel.setSizeUndefined();
        ScheduledExecutorService scheduleExecutor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            currentTime.setValue("Current Time : " + Instant.now());
        };
        scheduleExecutor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);

        FormLayout dataBindingLayout = new FormLayout();
        dataBindingLayout.setSpacing(true);
        dataBindingLayout.setMargin(true);

        Binder<BindData> binder = new Binder<>();
        BindData bindData = new BindData("BindData");
        binder.readBean(bindData);
        TextField bindedTextField = new TextField();
        bindedTextField.setWidth("250px");
        binder.forField(bindedTextField).bind(BindData::getBindName, BindData::setBindName);
        dataBindingLayout.addComponent(bindedTextField);

        FormLayout validatorLayout = new FormLayout();
        validatorLayout.setSpacing(true);
        validatorLayout.setMargin(true);

        HorizontalLayout textValidatorLayout = new HorizontalLayout();
        textValidatorLayout.setSpacing(true);
        textValidatorLayout.setMargin(true);

        
        BindData stringValidatorBindData = new BindData("");
        TextField stringValidator = new TextField();
        Binder<BindData> stringValidatorBinder = new Binder<>();
        stringValidatorBinder.setBean(stringValidatorBindData);
        stringValidatorBinder.forField(stringValidator)
        .withValidator(new StringLengthValidator("String must have 2-5 characters lenght", 2, 5))
        .bind(BindData::getBindName, BindData::setBindName);
        
        textValidatorLayout.addComponent(stringValidator);
        Button buttonStringValidator = new Button("Validate String");
        buttonStringValidator.addClickListener(e -> stringValidatorBinder.validate());
        textValidatorLayout.addComponent(buttonStringValidator);

        validatorLayout.addComponent(textValidatorLayout);
        verticalLayout.addComponent(gridLayout);
        verticalLayout.addComponent(richTextPanel);
        verticalLayout.addComponent(horizontalLayout);
        verticalLayout.addComponent(formLayout);
        verticalLayout.addComponent(twinColSelect);
        verticalLayout.addComponent(panel);
        verticalLayout.addComponent(serverPushPanel);
        verticalLayout.addComponent(dataBindingLayout);
        verticalLayout.addComponent(validatorLayout);
        setContent(verticalLayout);
    }

    @WebServlet(urlPatterns = "/VAADIN/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = VaadinUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}