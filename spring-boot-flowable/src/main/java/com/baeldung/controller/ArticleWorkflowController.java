package com.baeldung.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.domain.Approval;
import com.baeldung.domain.Article;
import com.baeldung.service.ArticleWorkflowService;

@RestController
public class ArticleWorkflowController {
    @Autowired
    private ArticleWorkflowService service;
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public void submit(@RequestBody Article article) {
        service.startProcess(article);
    }
    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Article> getTasks(@RequestParam String assignee) {
        return service.getTasks(assignee);
    }
    @RequestMapping(value = "/review", method = RequestMethod.POST)
    public void review(@RequestBody Approval approval) {
        service.submitReview(approval);
    }
}