package com.baeldung.hexagonal.web;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.hexagonal.core.domain.Topic;

public interface TopicRestUI {

    @PostMapping("/addTopic")
    public void addTopic(@RequestBody Topic topic);

    @GetMapping("/getAllTopics")
    public List<Topic> getAllTopics();

    @GetMapping("/getTopic/{id}")
    public Topic getTopic(@PathVariable String id);

    @PutMapping("/updateTopic/{id}")
    public void updateTopic(@RequestBody Topic topic, @PathVariable String id);

    @DeleteMapping("/deleteTopic/{id}")
    public void deleteTopic(@PathVariable String id);
}
