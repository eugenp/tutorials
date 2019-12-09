package com.baeldung.method.multiplereturnvalues;

import java.util.ArrayList;
import java.util.List;

class MultipleReturnValuesUsingArrays {

    Object[] getStudentData() {
        
        String name = "Alex";
        int age = 15;
        
        return new Object[] {name, age};
    }
    
    double[] getStreetCoordinates() {
        
        double[] coordinates = new double[2];

        coordinates[0] = 12.5;
        coordinates[1] = 10;
        
        return coordinates;
    }

    Number[] getStreetCoordinates2() {
        
        Number[] coordinates = new Double[2];

        coordinates[0] = 10; //throws ArrayStoreException
        
        return coordinates;
    }
    
    Object[] getStreetCoordinates3() {
        
        Object[] coordinates = new Object[2];

        coordinates[0] = 10; //Integer
        coordinates[1] = 10.1; //Double
        
        return coordinates;
    }

}
