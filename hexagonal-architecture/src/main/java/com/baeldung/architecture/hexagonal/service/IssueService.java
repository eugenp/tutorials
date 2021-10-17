package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.model.Issue;

import java.util.List;

public interface IssueService {
    List<Issue> getIssues();
    Issue getIssueById(Long id);
    Issue addIssue(Issue issue);
    Issue removeIssue (Long id);
}
