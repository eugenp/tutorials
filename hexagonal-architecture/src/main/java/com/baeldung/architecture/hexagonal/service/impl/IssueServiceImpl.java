package com.baeldung.architecture.hexagonal.service.impl;

import com.baeldung.architecture.hexagonal.model.Issue;
import com.baeldung.architecture.hexagonal.repository.IssueRepository;
import com.baeldung.architecture.hexagonal.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    private IssueRepository repository;

    public List<Issue> getIssues() {
        return repository.getIssues();
    }

    public Issue getIssueById(Long id) {
        return repository.getIssueById(id);
    }

    public Issue addIssue(Issue issue) {
        return repository.addIssue(issue);
    }

    public Issue removeIssue(Long id) {
        return repository.removeIssue(id);
    }
}
