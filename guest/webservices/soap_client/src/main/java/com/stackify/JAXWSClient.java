package com.stackify;

import com.stackify.services.DefaultUserImplService;
import com.stackify.services.User;
import com.stackify.services.UserService;
import com.stackify.services.Users;

public class JAXWSClient {
    public static void main(String[] args) {
        DefaultUserImplService service = new DefaultUserImplService();
        User user = new User();
        user.setEmail("john@gmail.com");
        user.setName("John");
        UserService port = service.getDefaultUserImplPort();
        port.addUser(user);
        Users users = port.getUsers();
        System.out.println(users.getUsers().iterator().next().getName());
    }
}
