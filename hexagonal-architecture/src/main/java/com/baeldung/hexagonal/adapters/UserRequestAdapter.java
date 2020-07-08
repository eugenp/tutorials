package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.core.SportsApp;
import com.baeldung.hexagonal.ports.FetchSportsRevenue;
import com.baeldung.hexagonal.ports.UserRequest;
import com.baeldung.hexagonal.ports.WriteSportsRevenue;

public class UserRequestAdapter implements UserRequest {
    
    private SportsApp sportsApp;
    
    public UserRequestAdapter(FetchSportsRevenue sportsRevenue, WriteSportsRevenue writeSportsRevenue) {
        sportsApp = new SportsApp(sportsRevenue, writeSportsRevenue);
    }
    
    @Override
    public void processRequest(String sportName) {
        sportsApp.fetchAndWrite(sportName);
    }

}
