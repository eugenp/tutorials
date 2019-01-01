package com.baeldung.list.multidimensional;

import java.util.ArrayList;

public class ArrayListOfArrayList {

    public static void main(String args[]) {

        int numVertices = 3;
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>(numVertices);

        //Initializing each element of ArrayList with ArrayList
        for(int i=0; i< numVertices; i++) {
            graph.add(new ArrayList<Integer>());
        }

        //We can add any number of columns to each row
        graph.get(0).add(1);
        graph.get(1).add(2);
        graph.get(2).add(0);
        graph.get(1).add(0);
        graph.get(2).add(1);
        graph.get(0).add(2);
      
        //Printing all the edges
        for(int vertexNo=0; vertexNo<numVertices; vertexNo++) {
            int edgeCount = graph.get(vertexNo).size();
            ArrayList<Integer> listOfVertices = graph.get(vertexNo);
            for(int i=0; i<edgeCount; i++) {
                System.out.println("Vertex "+vertexNo+" is connected to vetex "+listOfVertices.get(i));
            }
        }
    }

}