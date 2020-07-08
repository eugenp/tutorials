package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapters.FileWriterAdapter;
import com.baeldung.hexagonal.adapters.InMemorySportsDataAdapter;
import com.baeldung.hexagonal.adapters.UserRequestAdapter;
import com.baeldung.hexagonal.ports.FetchSportsRevenue;
import com.baeldung.hexagonal.ports.UserRequest;
import com.baeldung.hexagonal.ports.WriteSportsRevenue;

public class Main {

    public static void main(String[] args) {
        FetchSportsRevenue sportsRevenue = new InMemorySportsDataAdapter();
        WriteSportsRevenue writeSportsRevenue = new FileWriterAdapter();
        UserRequest userReq = new UserRequestAdapter(sportsRevenue, writeSportsRevenue);
        
        userReq.processRequest("Football");
        userReq.processRequest("Cricket");
    }

}
