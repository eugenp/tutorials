package com.baeldung.hexagonal.outside.driving.console;

import com.baeldung.hexagonal.inside.domain.User;
import com.baeldung.hexagonal.inside.port.inbound.UserServicePort;

import java.util.Formatter;
import java.util.List;

public class UserServiceAdapter {
    private final UserServicePort userService;

    public UserServiceAdapter(UserServicePort userService) {
        this.userService = userService;
    }

    public String process(String commandLine) {
        if (commandLine == null || "".equals(commandLine)) {
            return "invalid command line";
        }

        if ("list".equalsIgnoreCase(commandLine)) {
            List<User> userList = userService.getUsers();
            StringBuilder sb = new StringBuilder();
            Formatter fm = new Formatter(sb);
            fm.format("User List:%n");
            for (User user : userList) {
                fm.format("- %s%n", user);
            }
            return sb.toString();
        }

        String[] array = commandLine.split(" ");
        if (array.length == 2 && "add".equals(array[0])) {
            String name = array[1];
            boolean flag = userService.addUser(name);
            return flag ? "add user success" : "add user failed";
        }

        return "command line is not supported";
    }
}