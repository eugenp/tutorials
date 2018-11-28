package com.baeldung.list.multidimentional;

import java.util.ArrayList;

public class ArrayListOfArrayList {
	
	public static void main(String args[]) {
		
		int vertex = 5;
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>(vertex);
		
		//Initializing each element of ArrayList with ArrayList
		for(int i=0; i< vertex; i++) {
			graph.add(new ArrayList<Integer>());
		}
		
		//We can add any number of columns to each row
		graph.get(0).add(1);
		graph.get(0).add(5);
		graph.get(1).add(0);
		graph.get(1).add(2);
		graph.get(2).add(1);
		graph.get(2).add(3);
		graph.get(3).add(2);
		graph.get(3).add(4);
		graph.get(4).add(3);
		graph.get(4).add(5);
		
		//Printing all the edges
		for(int i=0; i<vertex; i++) {
			for(int j=0; j<graph.get(i).size(); j++) {
				System.out.println("Edge between vertex "+i+"and "+graph.get(i).get(j));
			}
		}
	}

}