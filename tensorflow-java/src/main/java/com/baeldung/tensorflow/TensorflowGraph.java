package com.baeldung.tensorflow;

import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Session;
import org.tensorflow.Tensor;

public class TensorflowGraph {

	public static Graph createGraph() {
		Graph graph = new Graph();
		Operation a = graph.opBuilder("Const", "a").setAttr("dtype", DataType.fromClass(Double.class))
				.setAttr("value", Tensor.<Double>create(3.0, Double.class)).build();
		Operation b = graph.opBuilder("Const", "b").setAttr("dtype", DataType.fromClass(Double.class))
				.setAttr("value", Tensor.<Double>create(2.0, Double.class)).build();
		Operation x = graph.opBuilder("Placeholder", "x").setAttr("dtype", DataType.fromClass(Double.class)).build();
		Operation y = graph.opBuilder("Placeholder", "y").setAttr("dtype", DataType.fromClass(Double.class)).build();
		Operation ax = graph.opBuilder("Mul", "ax").addInput(a.output(0)).addInput(x.output(0)).build();
		Operation by = graph.opBuilder("Mul", "by").addInput(b.output(0)).addInput(y.output(0)).build();
		graph.opBuilder("Add", "z").addInput(ax.output(0)).addInput(by.output(0)).build();
		return graph;
	}

	public static Object runGraph(Graph graph, Double x, Double y) {
		Object result;
		try (Session sess = new Session(graph)) {
			result = sess.runner().fetch("z").feed("x", Tensor.<Double>create(x, Double.class))
					.feed("y", Tensor.<Double>create(y, Double.class)).run().get(0).expect(Double.class)
					.doubleValue();
		}
		return result;
	}

	public static void main(String[] args) {
		Graph graph = TensorflowGraph.createGraph();
		Object result = TensorflowGraph.runGraph(graph, 3.0, 6.0);
		System.out.println(result);
		graph.close();
	}
}