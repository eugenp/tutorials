package com.baeldung.dependencyinjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public GreetingService greetingService(GreetingDao greetingDao) {
        return new GreetingService(greetingDao);
    }

    @Bean
    public GreetingDao greetingDao() {
        return new GreetingDao();
    }

    @Bean
    public MeetingDao meetingDao() {
        return new MeetingDao();
    }

    @Bean
    public MeetingService meetingService(MeetingDao meetingDao) {
        MeetingService meetingService = new MeetingService();
        meetingService.setMeetingDao(meetingDao);
        return meetingService;
    }

}
