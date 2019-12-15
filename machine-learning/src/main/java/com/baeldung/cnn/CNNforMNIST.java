package com.baeldung.cnn;

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.*;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.IOException;
import java.net.URL;

public class CNNforMNIST extends MultiLayerNetwork {

    public static final int SEED = 123;

    private CNNforMNIST(MultiLayerConfiguration conf) {
        super(conf);
    }

    public static CNNforMNIST getInstance(String fileName) throws IOException {
        URL mnistModel = CNNforMNIST.class.getResource(fileName);
        if (mnistModel != null) {
            return (CNNforMNIST) ModelSerializer.restoreMultiLayerNetwork(fileName);
        }
        return createDefaultCNNforMNIST().initModel().train().persist(fileName);
    }

    private CNNforMNIST initModel() {
        System.out.println("Initializing Model...");
        this.init();
        System.out.println("Model Initialized");
        return this;
    }

    private CNNforMNIST train() throws IOException {
        System.out.println("Training Model...");
        MnistDataSetIterator mnistDataSetIterator = new MnistDataSetIterator(128, true, SEED);
        int numEpochs = 10;
        for (int i = 0; i < numEpochs; i++) {
            System.out.println(String.format("Starting epoch n° %s", i + 1));
            this.fit(mnistDataSetIterator);
        }
        System.out.println("Model Trained");
        return this;
    }

    private CNNforMNIST persist(String fileName) throws IOException {
        System.out.println("Persisting Model...");
        ModelSerializer.writeModel(this, fileName, true);
        System.out.println("Model Persisted");
        return this;
    }

    private static CNNforMNIST createDefaultCNNforMNIST() {
        return new CNNforMNIST(
                new NeuralNetConfiguration.Builder()
                        .seed(SEED)
                        .l2(0.0005)
                        .updater(new Adam())
                        .weightInit(WeightInit.XAVIER)
                        .list()
                        .layer(0, buildInitialConvolutionLayer())
                        .layer(1, buildBatchNormalizationLayer())
                        .layer(2, buildPoolingLayer())
                        .layer(3, buildConvolutionLayer())
                        .layer(4, buildBatchNormalizationLayer())
                        .layer(5, buildPoolingLayer())
                        .layer(6, buildDenseLayer())
                        .layer(7, buildBatchNormalizationLayer())
                        .layer(8, buildDenseLayer())
                        .layer(9, buildOutputLayer())
                        .setInputType(InputType.convolutionalFlat(28, 28, 1))
                        .backprop(true)
                        .build()
        );
    }

    private static OutputLayer buildOutputLayer() {
        return new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                .nOut(10)
                .activation(Activation.SOFTMAX)
                .build();
    }

    private static DenseLayer buildDenseLayer() {
        return new DenseLayer.Builder().activation(Activation.RELU)
                .nOut(500)
                .dropOut(0.5)
                .build();
    }

    private static SubsamplingLayer buildPoolingLayer() {
        return new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                .kernelSize(2, 2)
                .stride(2, 2)
                .build();
    }

    private static BatchNormalization buildBatchNormalizationLayer() {
        return new BatchNormalization.Builder().build();
    }

    private static ConvolutionLayer buildConvolutionLayer() {
        return new ConvolutionLayer.Builder(5, 5)
                .stride(1, 1) // nIn need not specified in later layers
                .nOut(50)
                .activation(Activation.IDENTITY)
                .build();
    }

    private static ConvolutionLayer buildInitialConvolutionLayer() {
        return new ConvolutionLayer.Builder(5, 5)
                .nIn(1)
                .stride(1, 1)
                .nOut(20)
                .activation(Activation.IDENTITY)
                .build();
    }
}
