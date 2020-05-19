package com.baeldung.hexagonal.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.core.domain.Topic;
import com.baeldung.hexagonal.port.TopicService;
import com.baeldung.hexagonal.web.TopicRestUI;

@RestController
@RequestMapping("/topic")
public class TopicController implements TopicRestUI {

    @Autowired
    TopicService topicService;

    @Override
    public void addTopic(@RequestBody Topic topic) {
        topicService.addTopic(topic);
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @Override
    public Topic getTopic(@PathVariable String id) {
        return topicService.getTopic(id);
    }

    @Override
    public void updateTopic(@RequestBody Topic topic, @PathVariable String id) {
        topicService.updateTopic(topic, id);
    }

    @Override
    public void deleteTopic(@PathVariable String id) {
        topicService.deleteTopic(id);
    }

}
