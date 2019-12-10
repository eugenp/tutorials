package com.baeldung.methodmultiplereturnvalues;

class MultipleReturnValuesUsingArrays {

    
    double[] getCoordinates() {
        
        double[] coordinates = new double[2];

        coordinates[0] = 10.2;
        coordinates[1] = 12.5;
        
        return coordinates;
    }

    Number[] getCoordinateNumbers() {
        
        Number[] coordinates = new Double[2];

        coordinates[0] = 10; //throws ArrayStoreException
        
        return coordinates;
    }
    
    Object[] getCoordinateObjects() {
        
        Object[] coordinates = new Object[2];

        coordinates[0] = 10; //Integer
        coordinates[1] = 10.1; //Double
        
        return coordinates;
    }

}
