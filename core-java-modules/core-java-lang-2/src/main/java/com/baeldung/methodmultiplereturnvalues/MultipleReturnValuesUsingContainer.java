package com.baeldung.methodmultiplereturnvalues;

class MultipleReturnValuesUsingContainer {

    Coordinates getCoordinates() {
        
        double longitude = 154.2;
        double latitude = 15;
        String directionsNote = "directions note";
        
        return new Coordinates(longitude, latitude, directionsNote);
    }
    
}
