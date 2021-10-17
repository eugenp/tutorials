package com.baeldung.architecture.hexagonal.repository;

import com.baeldung.architecture.hexagonal.model.Issue;

import java.util.List;

public interface IssueRepository {
    List<Issue> getIssues();
    Issue getIssueById(Long id);
    Issue addIssue(Issue issue);
    Issue removeIssue(Long id);
}
