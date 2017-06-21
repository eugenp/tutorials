package com.baeldung.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class IPLSeasonOldFormatService implements IPLSeasonService {

    private TournamentFormat format;

    @Autowired
    public IPLSeasonOldFormatService(TournamentFormat format) {
        super();
        this.format = format;
    }

    public List<String> getVenues() {
        return Arrays.asList("Kolkata", "Mumbai", "Mohali", "Bangalore");
    }

    public String getFormat() {
        return format.getName();
    }
}
