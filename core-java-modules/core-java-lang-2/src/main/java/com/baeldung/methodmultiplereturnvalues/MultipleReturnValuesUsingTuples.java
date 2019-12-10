package com.baeldung.methodmultiplereturnvalues;

class MultipleReturnValuesUsingTuples {

    Tuple3<Double, Double, String> getCoordinates(){
        
        double longitude = 154.2;
        double latitude = 15;
        String directionsNote = "directions note";
        
        Tuple3<Double, Double, String> coordinatesTuple = 
            new Tuple3<>(longitude, latitude, directionsNote);
        
        return coordinatesTuple;
    }
    
}
