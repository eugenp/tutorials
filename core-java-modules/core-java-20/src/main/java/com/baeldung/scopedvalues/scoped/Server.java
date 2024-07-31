package com.baeldung.scopedvalues.scoped;

import com.baeldung.scopedvalues.data.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.incubator.concurrent.ScopedValue;

import java.util.List;
import java.util.Optional;

public class Server {

    private static final List<User> AUTHENTICATED_USERS = List.of(
            new User("1", "admin", "123456", true),
            new User("2", "user", "123456", false)
    );
    public final static ScopedValue<User> LOGGED_IN_USER = ScopedValue.newInstance();
    private final Controller controller = new Controller();

    public void serve(HttpServletRequest request, HttpServletResponse response) {
        Optional<User> user = authenticateUser(request);
        if (user.isPresent()) {
            ScopedValue.where(LOGGED_IN_USER, user.get())
                    .run(() -> controller.processRequest(request, response));
        } else {
            response.setStatus(401);
        }
    }

    private Optional<User> authenticateUser(HttpServletRequest request) {
        return AUTHENTICATED_USERS.stream()
                .filter(user -> checkUserPassword(user, request))
                .findFirst();
    }

    private boolean checkUserPassword(User user, HttpServletRequest request) {
        return user.name().equals(request.getParameter("user_name"))
                && user.password().equals(request.getParameter("user_pw"));
    }

}
