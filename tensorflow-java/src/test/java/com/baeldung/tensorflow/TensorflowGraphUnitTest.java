package com.baeldung.tensorflow;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.tensorflow.Graph;

public class TensorflowGraphUnitTest {
	
	@Test
	public void givenTensorflowGraphWhenRunInSessionReturnsExpectedResult() {
		
		Graph graph = TensorflowGraph.createGraph();
		Object result = TensorflowGraph.runGraph(graph, 3.0, 6.0);
		assertEquals(21.0, result);
		System.out.println(result);
		graph.close();
		
	}

}
