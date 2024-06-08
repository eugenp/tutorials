package com.baeldung.javaxval.childvalidation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ChildValidationUnitTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void whenInvalidProjectAndUser_thenAssertConstraintViolations() {
        Project project = new Project(null);
        project.setOwner(new User(null, "invalid-email"));

        List<String> messages = validate(project);

        assertEquals(3, messages.size());
        assertTrue(messages.contains("Project title must be present"));
        assertTrue(messages.contains("User name must be present"));
        assertTrue(messages.contains("User email format is incorrect"));
    }

    @Test
    public void whenCollectionsIsMap_thenAssertKeyIsValidated() {
        Map<User, Task> assignedTasks = new HashMap<>();
        assignedTasks.put(new User(), new Task(null, null));

        Project project = new Project("Project name");
        project.setAssignedTask(assignedTasks);

        List<String> messages = validate(project);

        assertEquals(3, messages.size());
        assertTrue(messages.contains("Task title must be present"));
        assertTrue(messages.contains("User name must be present"));
        assertTrue(messages.contains("User email must be present"));
    }

    @Test
    public void whenAnnotationOnTypeArgument_thenAssertValidated() {
        Map<String, List<Task>> groupedTasks = new HashMap<>();
        groupedTasks.put(null, Arrays.asList(new Task("Task1", null), new Task(null, "T2")));

        Project project = new Project("Project name");
        project.setTaskByType(groupedTasks);

        List<String> messages = validate(project);

        assertEquals(2, messages.size());
        assertTrue(messages.contains("Task title must be present"));
        assertTrue(messages.contains("Task description size not valid"));
    }

    private List<String> validate(Project project) {
        return validator.validate(project)
            .stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());
    }

}
