package com.baeldung.hexagonal.port;

import java.util.List;

import com.baeldung.hexagonal.core.domain.Topic;

public interface TopicRepository {

    public void addTopic(Topic topic);

    public List<Topic> getAllTopics();

    public Topic getTopic(String id);

    public void updateTopic(Topic topic, String id);

    public void deleteTopic(String id);
}
