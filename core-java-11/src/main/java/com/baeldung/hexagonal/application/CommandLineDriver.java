package java.com.baeldung.hexagonal.application;

import java.com.baeldung.hexagonal.domain.TodoService;
import java.time.LocalDateTime;

public class CommandLineDriver {
    private TodoService todoService;

    public void handlCommandlineInput(String input){

        // some command parsing logic here

        String title=""; // replace with value passed via CLI
        LocalDateTime dueDate = null; // replace with value passed via CLI
        todoService.create(title, dueDate);
    }
}
