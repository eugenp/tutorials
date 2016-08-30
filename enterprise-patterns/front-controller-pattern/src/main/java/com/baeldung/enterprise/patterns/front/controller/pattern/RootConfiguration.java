package com.baeldung.enterprise.patterns.front.controller.pattern;

import com.baeldung.enterprise.patterns.front.controller.pattern.models.Task;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class RootConfiguration {
    @Bean
    public Map<String, List<Task>> taskList() {
        Map<String, List<Task>> taskMap = new HashMap<>();
        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task("Clean the dishes!", new Date()));
        taskMap.put("Cid", taskList);
        return taskMap;
    }
}
