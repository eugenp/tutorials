package com.baeldung.hexagonal.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.core.domain.Topic;
import com.baeldung.hexagonal.port.TopicRepository;

@Repository
public class TopicRepositoryImpl implements TopicRepository {

    private Map<String, Topic> topicMap = new HashMap<>();

    @Override
    public void addTopic(Topic topic) {
        topicMap.put(topic.getId(), topic);
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicMap.values()
            .stream()
            .collect(Collectors.toList());
    }

    @Override
    public Topic getTopic(String id) {
        return topicMap.get(id);
    }

    @Override
    public void updateTopic(Topic topic, String id) {
        topicMap.put(id, topic);
    }

    @Override
    public void deleteTopic(String id) {
        topicMap.remove(id);
    }

}
