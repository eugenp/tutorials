package com.baeldung.hexagonal;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.services.NotificationService;
import com.baeldung.hexagonal.services.UserService;

public class HexagonalDriver {

    public static void main(String [] args) {

        User randomUser = Fixtures.randomUser();

        UserService userService = new UserService();
        userService.create(randomUser);

        NotificationService service = new NotificationService();
        service.dispatchNotification(Fixtures.randomNotification(randomUser));

    }

}
