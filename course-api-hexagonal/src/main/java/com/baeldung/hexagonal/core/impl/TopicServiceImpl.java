package com.baeldung.hexagonal.core.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.core.domain.Topic;
import com.baeldung.hexagonal.port.TopicRepository;
import com.baeldung.hexagonal.port.TopicService;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void addTopic(Topic topic) {
        topicRepository.addTopic(topic);
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.getAllTopics();
    }

    @Override
    public Topic getTopic(String id) {
        return topicRepository.getTopic(id);
    }

    @Override
    public void updateTopic(Topic topic, String id) {
        topicRepository.updateTopic(topic, id);
    }

    @Override
    public void deleteTopic(String id) {
        topicRepository.deleteTopic(id);
    }

}
