package com.baeldung.methodmultiplereturnvalues;

class MultipleReturnValuesUsingContainer {

    static Coordinates getCoordinates() {
        
        double longitude = 10;
        double latitude = 12.5;
        String placeName = "home";
        
        return new Coordinates(longitude, latitude, placeName);
    }
    
}
