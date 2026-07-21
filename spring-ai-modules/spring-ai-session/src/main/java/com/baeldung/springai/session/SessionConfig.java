package com.baeldung.springai.session;

import org.springframework.ai.session.DefaultSessionService;
import org.springframework.ai.session.InMemorySessionRepository;
import org.springframework.ai.session.SessionService;
import org.springframework.ai.session.advisor.SessionMemoryAdvisor;
import org.springframework.ai.session.compaction.SlidingWindowCompactionStrategy;
import org.springframework.ai.session.compaction.TurnCountTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionConfig {

    @Bean
    public SessionService sessionService() {
        return DefaultSessionService.builder()
            .sessionRepository(InMemorySessionRepository.builder().build())
            .build();
    }

    @Bean
    public SessionMemoryAdvisor sessionMemoryAdvisor(SessionService sessionService) {
        return SessionMemoryAdvisor.builder(sessionService)
            .defaultUserId("alice")
            .compactionTrigger(new TurnCountTrigger(20))
            .compactionStrategy(SlidingWindowCompactionStrategy.builder()
                .maxEvents(10)
                .build())
            .build();
    }

}
