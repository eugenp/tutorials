package com.baeldung.architecture.hexagonal.controller;

import com.baeldung.architecture.hexagonal.model.Issue;
import com.baeldung.architecture.hexagonal.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/issues")
public class IssueController {
    private IssueService service;

    @Autowired
    public IssueController(IssueService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Issue>> getIssues() {
        return new ResponseEntity<List<Issue>> (service.getIssues(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        return new ResponseEntity<Issue>(service.getIssueById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Issue> addIssue(@RequestBody Issue issue) {
        return new ResponseEntity<Issue>(service.addIssue(issue), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Issue> removeIssue(@PathVariable Long id) {
        return new ResponseEntity<Issue>(service.removeIssue(id), HttpStatus.OK);
    }
}
