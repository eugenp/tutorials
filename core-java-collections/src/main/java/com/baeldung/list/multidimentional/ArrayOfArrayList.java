package com.baeldung.list.multidimentional;

import java.util.ArrayList;

public class ArrayOfArrayList {
	
	public static void main(String args[]) {
		
		int vertex = 5;
		ArrayList<Integer>[] graph = new ArrayList[vertex];
		
		//Initializing each element of array with ArrayList
		for(int i=0; i< vertex; i++) {
			graph[i] = new ArrayList<Integer>();
		}
		
		//We can add any number of columns to each row
		graph[0].add(1);
		graph[0].add(5);
		graph[1].add(0);
		graph[1].add(2);
		graph[2].add(1);
		graph[2].add(3);
		graph[3].add(2);
		graph[3].add(4);
		graph[4].add(3);
		graph[4].add(5);
		
		//Printing all the edges
		for(int i=0; i<vertex; i++) {
			for(int j=0; j<graph[i].size(); j++) {
				System.out.println("Edge between vertex "+i+"and "+graph[i].get(j));
			}
		}
	}

}