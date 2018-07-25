package com.baeldung.creational.factory;

public class FactoryDriver {
    public static void main(String[] args) {
        Polygon p;
        PolygonFactory factory = new PolygonFactory();
        
        //get the shape which has 4 sides
        p = factory.getPolygon(4);
        System.out.println("The shape with 4 sides is a " + p.getType());
        
        //get the shape which has 4 sides
        p = factory.getPolygon(8);
        System.out.println("The shape with 8 sides is a " + p.getType());
    }
}
