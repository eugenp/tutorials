package com.baeldung.scopedvalues.classic;

import com.baeldung.scopedvalues.data.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final List<User> AUTHENTICATED_USERS = List.of(new User("1", "admin", "123456", true));
    private final Controller controller = new Controller();
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public void serve(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Optional<User> user = authenticateUser(request);
        if (user.isPresent()) {
            executor.submit(() -> {
                controller.processRequest(request, response, user.get());
            });
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
        return user.id().equals(request.getParameter("user_id"))
                && user.password().equals(request.getParameter("user_pw"));
    }

}
