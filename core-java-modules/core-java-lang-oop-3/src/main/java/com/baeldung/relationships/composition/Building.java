package com.baeldung.relationships.composition;

import java.util.List;

public class Building {

    private String address;
    private List<Room> rooms;
    
    public class Room {
        
        public String getBuildingAddress() {
            return Building.this.address;
        }
        
    }
    
}
