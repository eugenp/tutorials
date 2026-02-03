package com.baeldung.logging.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CampaignService {

    private final JdbcTemplate jdbcTemplate;

    public CampaignService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long findCampaignIdByName(String name) {
        return jdbcTemplate.queryForObject("SELECT id FROM campaign WHERE name = ?", Long.class, name);
    }
}
