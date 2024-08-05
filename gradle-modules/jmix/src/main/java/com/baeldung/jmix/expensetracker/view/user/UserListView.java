package com.baeldung.jmix.expensetracker.view.user;

import com.baeldung.jmix.expensetracker.entity.User;
import com.baeldung.jmix.expensetracker.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "users", layout = MainView.class)
@ViewController("User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {
}