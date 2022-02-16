package com.baeldung.businesslogic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.businesslogic.entity.Todo;
import com.baeldung.businesslogic.service.TodoService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BusinessLogicSecurityIntegrationTest {

    @Autowired
    TodoService todoService;

    @Test
    @WithMockUser(username = "Joe")
    public void givenUserIsOwner_whenUpdatingRestrictedTodo_shouldThrowAccessDeniedException() {
        Todo todo = new Todo("This one is not editable", false, "Joe");

        assertThrows(AccessDeniedException.class, () -> todoService.save(todo));
    }

    @Test
    @WithMockUser(username = "Joe")
    public void givenUserIsOwner_whenUpdatingUnrestrictedTodo_shouldSuccess() {
        Todo todo = new Todo("This one is editable", true, "Joe");

        assertThat(todoService.save(todo)).hasNoNullFieldsOrProperties();
    }

    @Test
    @WithMockUser(username = "Joe")
    public void givenUserIsNotOwner_whenUpdatingUnrestrictedTodo_shouldThrowAccessDeniedException() {
        Todo todo = new Todo("This one is editable", true, "Jane");

        assertThrows(AccessDeniedException.class, () -> todoService.save(todo));
    }
}
