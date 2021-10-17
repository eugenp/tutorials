package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.controller.IssueController;
import com.baeldung.architecture.hexagonal.model.Issue;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootTest
public class HexagonalArchitectureIntegrationTest {

    @Autowired
    private IssueController controller;

    @BeforeEach
    public void before_every_test() {
        Issue issue = new Issue();
        issue.setId(System.currentTimeMillis());
        issue.setDescription("Out of memory when load increase");
        issue.setType("Memory Overflow");
        Assert.assertEquals(controller.addIssue(issue).getStatusCode(), HttpStatus.OK);
    }

    @AfterEach
    public void after_every_test() {
        Assert.assertTrue(
                controller.getIssues().getBody().stream()
                        .map(Issue::getId)
                        .map(controller::removeIssue)
                        .allMatch(r -> r.getStatusCode().equals(HttpStatus.OK)));
    }


    @Test
    public void test_context_loads() throws Exception {
        Assert.assertNotNull(controller);
    }

    @Test
    public void test_add_issue() {
        Long id = System.currentTimeMillis();
        Issue issue = new Issue();
        issue.setId(id);
        issue.setDescription("Out of memory when load increase");
        issue.setType("Memory Overflow");
        Assert.assertEquals(controller.addIssue(issue).getStatusCode(), HttpStatus.OK);

        Assert.assertEquals(controller.getIssueById(id).getBody().getId(), id);
    }

    @Test
    public void test_list_issue() {
        Assert.assertEquals(controller.getIssues().getBody().size(), 1);
    }
}
