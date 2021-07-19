package com.baeldung.methodmultiplereturnvalues;

class MultipleReturnValuesUsingArrays {

    
    static double[] getCoordinatesDoubleArray() {
        
        double[] coordinates = new double[2];

        coordinates[0] = 10;
        coordinates[1] = 12.5;
        
        return coordinates;
    }

    
    static Number[] getCoordinatesNumberArray() {
        
        Number[] coordinates = new Number[2];

        coordinates[0] = 10; //Integer
        coordinates[1] = 12.5; //Double
        
        return coordinates;
    }

}
