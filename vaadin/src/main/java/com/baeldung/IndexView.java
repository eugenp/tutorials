package com.baeldung;

import com.baeldung.introduction.PushView;
import com.baeldung.introduction.basics.VaadinFlowBasics;
import com.baeldung.introduction.FormView;
import com.baeldung.introduction.GridView;
import com.baeldung.spring.EmployeesView;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("")
public class IndexView extends VerticalLayout {

    public IndexView() {
        add(new H1("Vaadin Flow examples"));

        add(new RouterLink("Basics", VaadinFlowBasics.class));
        add(new RouterLink("Grid", GridView.class));
        add(new RouterLink("Form", FormView.class));
        add(new RouterLink("Push", PushView.class));
        add(new RouterLink("CRUD", EmployeesView.class));
    }
}
