package com.baeldung.methodmultiplereturnvalues;

class MultipleReturnValuesUsingContainer {

    static Coordinates getCoordinates() {
        
        double longitude = 10;
        double latitude = 12.5;
        Coordinates nearbyLocation = null;
        
        return new Coordinates(longitude, latitude, nearbyLocation);
    }
    
}
