package com.baeldung.boot.atlassearch.web;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.boot.atlassearch.service.AtlasSearchService;

@RestController
@RequestMapping("/atlas")
public class AtlasSearchController {

    @Autowired
    private AtlasSearchService service;

    @GetMapping("whatever")
    public List<Document> getWhatever() {
        return service.whatever();
    }

    @GetMapping("paginelson/{skip}/{limit}")
    public List<Document> getPaginelson(@PathVariable int skip, @PathVariable int limit) {
        return service.paginelson(skip, limit);
    }

    @GetMapping("meta/{by}")
    public List<Document> getMeta(@PathVariable String by) {
        return service.meta(by);
    }

    @GetMapping("group/{by}")
    public List<Document> getGroup(@PathVariable String by) {
        return service.grouping(by);
    }

    @GetMapping("scoring/{type}/{weight}")
    public List<Document> getScored(@PathVariable String type, @PathVariable int weight) {
        return service.scoring(type, weight);
    }

    @PostMapping("scoring/function")
    public List<Document> postScored(@RequestBody String f) {
        return service.scoring("function", f);
    }

    @GetMapping("faceting")
    public List<Document> getFaceting() {
        return service.faceting(null, null);
    }
}
