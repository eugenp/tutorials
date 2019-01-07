package com.baeldung.list.multidimensional;

import java.util.ArrayList;

public class ThreeDimensionalArrayList {

    public static void main(String args[]) {
        
        int x_axis_length = 2;
        int y_axis_length = 2;
        int z_axis_length = 2;
        ArrayList< ArrayList< ArrayList<String> > > space = new ArrayList<>(x_axis_length);

        //Initializing each element of ArrayList with ArrayList< ArrayList<String> >
        for(int i = 0; i < x_axis_length; i++) {
            space.add(new ArrayList< ArrayList<String> >(y_axis_length));
            for(int j = 0; j < y_axis_length; j++) {
                space.get(i).add(new ArrayList<String>(z_axis_length));
            }
        }

        //Set Red color for points (0,0,0) and (0,0,1)
        space.get(0).get(0).add(0,"Red");
        space.get(0).get(0).add(1,"Red");
        //Set Blue color for points (0,1,0) and (0,1,1)
        space.get(0).get(1).add(0,"Blue");
        space.get(0).get(1).add(1,"Blue");
        //Set Green color for points (1,0,0) and (1,0,1)
        space.get(1).get(0).add(0,"Green");
        space.get(1).get(0).add(1,"Green");
        //Set Yellow color for points (1,1,0) and (1,1,1)
        space.get(1).get(1).add(0,"Yellow");
        space.get(1).get(1).add(1,"Yellow");

        //Printing colors for all the points
        for(int i = 0; i < x_axis_length; i++) {
            for(int j = 0; j < y_axis_length; j++) {
                for(int k = 0; k < z_axis_length; k++) {
                    System.out.println("Color of point ("+i+","+j+","+k+") is :"+space.get(i).get(j).get(k));
                }
            }
        }
    }

}