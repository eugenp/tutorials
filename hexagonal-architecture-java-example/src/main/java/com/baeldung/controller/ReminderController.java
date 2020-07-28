package com.baeldung.controller;

import com.baeldung.domain.Reminder;
import com.baeldung.exception.ReminderNotFoundException;
import com.baeldung.port.input.ReminderServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class ReminderController {

    private final ReminderServicePort reminderService;

    public ReminderController(ReminderServicePort reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping("/reminder/create")
    public Reminder addReminder(@RequestParam String message,
                                @RequestParam("reminderTime")
                                String reminderTime) {
        return reminderService.createReminder(message, reminderTime);
    }

    @GetMapping("/reminder/{id}")
    public Reminder getReminder(@PathVariable String id) {
        return reminderService.getReminder(id);
    }

    @ExceptionHandler({ReminderNotFoundException.class})
    public ResponseEntity<String> handleException() {
        return new ResponseEntity<>("Reminder not found", NOT_FOUND);
    }
}
