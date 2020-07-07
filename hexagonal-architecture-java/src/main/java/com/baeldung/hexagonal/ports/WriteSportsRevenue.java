package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.model.SportRevenue;

public interface WriteSportsRevenue {
    public void writeSportsReveue(SportRevenue sportRevenue);   
}
