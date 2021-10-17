package com.baeldung.architecture.hexagonal.repository.impl;

import com.baeldung.architecture.hexagonal.model.Issue;
import com.baeldung.architecture.hexagonal.repository.IssueRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryIssueRepositoryImpl implements IssueRepository {
    private static final Map<Long, Issue> issueMap = new ConcurrentHashMap<Long, Issue>(0);


    @Override
    public List<Issue> getIssues() {
        return new ArrayList<Issue>(issueMap.values());
    }

    @Override
    public Issue getIssueById(Long id) {
        return issueMap.get(id);
    }

    @Override
    public Issue addIssue(Issue issue) {
        issueMap.put(issue.getId(), issue);
        return issue;
    }

    @Override
    public Issue removeIssue(Long id) {
        if (issueMap.get(id) != null) {
            Issue issue = issueMap.get(id);
            issueMap.remove(id);
            return issue;
        } else {
            return null;
        }
    }
}
