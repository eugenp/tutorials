package com.baeldung.hexagonal.core;

import com.baeldung.hexagonal.model.SportRevenue;
import com.baeldung.hexagonal.ports.FetchSportsRevenue;
import com.baeldung.hexagonal.ports.WriteSportsRevenue;

public class SportsApp {
    
    private FetchSportsRevenue sportsRevenue;
    private WriteSportsRevenue writeSportsRevenue;
    
    public SportsApp(FetchSportsRevenue sportsCategories, WriteSportsRevenue writeSportsRevenue) {
        this.sportsRevenue = sportsCategories;
        this.writeSportsRevenue = writeSportsRevenue;
    }
    
    public void fetchAndWrite(String sportName) {
        SportRevenue sportRevenue = sportsRevenue.retrieveSportRevenue(sportName);
        writeSportsRevenue.writeSportsReveue(sportRevenue);
    }
}
