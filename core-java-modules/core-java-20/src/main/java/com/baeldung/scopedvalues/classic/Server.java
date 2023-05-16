package com.baeldung.scopedvalues.classic;

import com.baeldung.scopedvalues.data.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class Server {

    private static final List<User> AUTHENTICATED_USERS = List.of(
            new User("1", "admin", "123456", true),
            new User("2", "user", "123456", false)
    );
    private final Controller controller = new Controller();
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public void serve(HttpServletRequest request, HttpServletResponse response) throws InterruptedException, ExecutionException {
        Optional<User> user = authenticateUser(request);
        if (user.isPresent()) {
            Future<?> future = executor.submit(() ->
                    controller.processRequest(request, response, user.get()));
            future.get();
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
