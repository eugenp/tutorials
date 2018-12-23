package com.baeldung.list.multidimensional;

import java.util.ArrayList;

public class ArrayListOfArrayList {

    public static void main(String args[]) {

        int vertex = 3;
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>(vertex);

        //Initializing each element of ArrayList with ArrayList
        for(int i=0; i< vertex; i++) {
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
        for(int i=0; i<vertex; i++) {
            int vertexNo = i;
            int edgeCount = graph.get(vertexNo).size();
            for(int j=0; j<edgeCount; j++) {
                System.out.println("Vertex "+vertexNo+" is connected to vetex "+graph.get(vertexNo).get(j));
            }
        }
    }

}