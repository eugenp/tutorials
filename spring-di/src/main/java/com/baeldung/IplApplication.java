package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.baeldung.service.IPLSeasonOldFormatService;
import com.baeldung.service.IPLSeasonService;
import com.baeldung.service.RoundRobinTournamentFormat;
import com.baeldung.service.TournamentFormat;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class IplApplication {

    public static void main(String[] args) {
        SpringApplication.run(IplApplication.class, args);
    }

    @Bean
    public IPLSeasonService getCurrentFormat(TournamentFormat format) {
        return new IPLSeasonOldFormatService(format);
    }

    @Bean
    @Lazy
    public TournamentFormat getTournamentFormat() {
        return new RoundRobinTournamentFormat();
    }

}
