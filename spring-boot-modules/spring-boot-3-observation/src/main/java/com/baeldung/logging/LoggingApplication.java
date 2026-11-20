package com.baeldung.logging;

import com.baeldung.logging.repository.CampaignRepository;
import com.baeldung.logging.service.CampaignService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDate;

@SpringBootApplication
@PropertySource({ "classpath:logging/logging.properties" })
class LoggingApplication {

    private static final Logger logger = LoggerFactory.getLogger(LoggingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LoggingApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(CampaignRepository campaignRepository, CampaignService campaignService) {
        return args -> {
            LocalDate startDate = LocalDate.of(2024, 1, 1);
            LocalDate endDate = LocalDate.of(2024, 12, 31);

            logger.info("Executing query to find campaigns between {} and {}", startDate, endDate);
            var campaigns = campaignRepository.findCampaignsByStartDateBetween(startDate, endDate);
            logger.info("Found {} campaigns", campaigns.size());
            campaigns.forEach(campaign -> logger.info("Campaign: {}", campaign));

            logger.info("Executing JdbcTemplate query to find campaign by name");
            Long campaignId = campaignService.findCampaignIdByName("Spring Campaign");
            logger.info("Found campaign with ID: {}", campaignId);
        };
    }

}
