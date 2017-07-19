package com.baeldung.neuroph;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.ConnectionFactory;
import org.neuroph.util.NeuralNetworkType;

public class App {

    private static NeuralNetwork assembleNeuralNetwork() {

        Layer inputLayer = new Layer();
        inputLayer.addNeuron(new Neuron());
        inputLayer.addNeuron(new Neuron());

        System.out.println("Input Layer Assembled!");

        Layer hiddenLayerOne = new Layer();
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());
        hiddenLayerOne.addNeuron(new Neuron());

        System.out.println("Hidden Layer One Assembled!");

        Layer hiddenLayerTwo = new Layer();
        hiddenLayerTwo.addNeuron(new Neuron());
        hiddenLayerTwo.addNeuron(new Neuron());
        hiddenLayerTwo.addNeuron(new Neuron());
        hiddenLayerTwo.addNeuron(new Neuron());

        System.out.println("Hidden Layer Two Assembled!");

        Layer outputLayer = new Layer();
        outputLayer.addNeuron(new Neuron());

        System.out.println("Output Layer Assembled!");

        NeuralNetwork ann = new NeuralNetwork();

        ann.addLayer(0, inputLayer);
        ann.addLayer(1, hiddenLayerOne);
        ConnectionFactory.fullConnect(ann.getLayerAt(0), ann.getLayerAt(1));
        ann.addLayer(2, hiddenLayerTwo);
        ConnectionFactory.fullConnect(ann.getLayerAt(1), ann.getLayerAt(2));
        ann.addLayer(3, outputLayer);
        ConnectionFactory.fullConnect(ann.getLayerAt(2), ann.getLayerAt(3));
        ConnectionFactory.fullConnect(ann.getLayerAt(0), ann.getLayerAt(ann.getLayersCount()-1), false);
        System.out.println("Layers Combined and Connected!");

        ann.setInputNeurons(inputLayer.getNeurons());
        ann.setOutputNeurons(outputLayer.getNeurons());

        ann.setNetworkType(NeuralNetworkType.MULTI_LAYER_PERCEPTRON);
        System.out.println("Neural Network Assembled!");
        return ann;
    }

    private static NeuralNetwork trainNeuralNetwork(NeuralNetwork ann) {
        int inputSize = 2;
        int outputSize = 1;
        DataSet ds = new DataSet(inputSize, outputSize);

        DataSetRow rOne = new DataSetRow(new double[] {0, 1}, new double[] {0});
        ds.addRow(rOne);
        DataSetRow rTwo = new DataSetRow(new double[] {1, 1}, new double[] {1});
        ds.addRow(rTwo);
        DataSetRow rThree = new DataSetRow(new double[] {0, 0}, new double[] {0});
        ds.addRow(rThree);
        DataSetRow rFour = new DataSetRow(new double[] {1, 0}, new double[] {0});
        ds.addRow(rFour);
        System.out.println("Data Set Assembled!");

        BackPropagation backPropagation = new BackPropagation();
        backPropagation.setMaxIterations(1000);

        ann.learn(ds, backPropagation);
        System.out.println("Neural Network Trained!");
        return ann;
    }

    private static void testNeuralNetwork(NeuralNetwork ann) {
        System.out.println("Testing XOR");
        ann.setInput(0, 1);
        System.out.println("Testing value : {0, 1}");
        ann.calculate();
        double[] networkOutputOne = ann.getOutput();
        System.out.println("Result: " + networkOutputOne[0]);
        System.out.println("Actual value: 0.0");

        ann.setInput(1, 1);
        System.out.println("Testing value : {1, 1}");
        ann.calculate();
        double[] networkOutputTwo = ann.getOutput();
        System.out.println("Result: " + networkOutputTwo[0]);
        System.out.println("Actual value: 1.0");

        ann.setInput(0, 0);
        System.out.println("Testing value : {0, 0}");
        ann.calculate();
        double[] networkOutputThree = ann.getOutput();
        System.out.println("Result: " + networkOutputThree[0]);
        System.out.println("Actual value: 0.0");

        ann.setInput(1, 0);
        System.out.println("Testing value : {1, 0}");
        ann.calculate();
        double[] networkOutputFour = ann.getOutput();
        System.out.println("Result: " + networkOutputFour[0]);
        System.out.println("Actual value: 0.0");
    }

    public static void main(String[] args){
        testNeuralNetwork(trainNeuralNetwork(assembleNeuralNetwork()));
    }

}
