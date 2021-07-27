package com.baeldung.hexagonal.run;

import com.baeldung.hexagonal.inside.business.UserServiceImpl;
import com.baeldung.hexagonal.inside.port.inbound.UserServicePort;
import com.baeldung.hexagonal.inside.port.outbound.UserDaoPort;
import com.baeldung.hexagonal.outside.driven.mockdb.UserDaoAdapter;
import com.baeldung.hexagonal.outside.driving.console.ConsoleApp;
import com.baeldung.hexagonal.outside.driving.console.UserServiceAdapter;

public class ConsoleWithMockRepository {
    public static void main(String[] args) {
        UserDaoPort userDao = new UserDaoAdapter();
        UserServicePort userService = new UserServiceImpl(userDao);
        UserServiceAdapter adapter = new UserServiceAdapter(userService);

        ConsoleApp app = new ConsoleApp(adapter);
        app.run();
    }
}