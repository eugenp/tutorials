package com.baeldung.cnn;

import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.BatchNormalization;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Adam;

import java.io.File;
import java.io.IOException;

import static org.nd4j.linalg.activations.Activation.RELU;
import static org.nd4j.linalg.learning.config.Adam.*;
import static org.nd4j.linalg.lossfunctions.LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD;

public class MultiLayerPerceptronForMNIST extends MultiLayerNetwork {

    public static final int SEED = 123;

    public MultiLayerPerceptronForMNIST(MultiLayerConfiguration conf) {
        super(conf);
    }

    public static MultiLayerNetwork getInstance(String fileName) throws IOException, InterruptedException {
        System.out.println("Looking for pretrained model...");
        File mnistModel = new File(fileName);
        System.out.println(mnistModel);
        if (mnistModel.exists()) {
            System.out.println("Pretrained model found");
            return ModelSerializer.restoreMultiLayerNetwork(fileName);
        }
        System.out.println("Pretrained model not found");
        return createDefaultMLPforMNIST().initModel().train().persist(fileName);
    }

    private MultiLayerPerceptronForMNIST initModel() {
        System.out.println("Initializing Model...");
        this.init();
        System.out.println("Model Initialized");
        return this;
    }

    private MultiLayerPerceptronForMNIST train() throws IOException, InterruptedException {
        System.out.println("Training Model...");
        int numEpochs = 10;
        System.out.println("Loading Training Set...");
        RecordReaderDataSetIterator trainingSet = MNISTDataSet.loadTraining();
        for (int i = 0; i < numEpochs; i++) {
            System.out.println(String.format("Starting epoch n° %s", i + 1));
            this.fit(trainingSet);
        }
        System.out.println("Model Trained");
        return this;
    }

    private MultiLayerPerceptronForMNIST persist(String fileName) throws IOException {
        System.out.println("Persisting Model...");
        ModelSerializer.writeModel(this, fileName, true);
        System.out.println("Model Persisted");
        return this;
    }

    private static MultiLayerPerceptronForMNIST createDefaultMLPforMNIST() {
        double learningRate = 0.01;
        return new MultiLayerPerceptronForMNIST(
                new NeuralNetConfiguration.Builder()
                        .seed(SEED)
                        .l2(0.0005)
                        .updater(adam(learningRate))
                        .weightInit(WeightInit.XAVIER)
                        .list()
                        .layer(0, buildDenseLayer())
                        .layer(1, buildBatchNormalizationLayer())
                        .layer(2, buildHiddenLayer())
                        .layer(3, buildBatchNormalizationLayer())
                        .layer(4, buildHiddenLayer())
                        .layer(5, buildBatchNormalizationLayer())
                        .layer(6, buildOutputLayer())
                        .build()
        );
    }

    private static Adam adam(double learningRate) {
        return new Adam(learningRate, DEFAULT_ADAM_BETA1_MEAN_DECAY, DEFAULT_ADAM_BETA2_VAR_DECAY, DEFAULT_ADAM_EPSILON);
    }

    private static DenseLayer buildDenseLayer() {
        return new DenseLayer.Builder()
                .nIn(28 * 28)
                .nOut(500)
                .dropOut(0.5)
                .activation(RELU)
                .build();
    }

    private static DenseLayer buildHiddenLayer() {
        return new DenseLayer.Builder()
                .nIn(500)
                .nOut(500)
                .dropOut(0.5)
                .activation(RELU)
                .build();
    }

    private static BatchNormalization buildBatchNormalizationLayer() {
        return new BatchNormalization.Builder().build();
    }

    private static OutputLayer buildOutputLayer() {
        return new OutputLayer.Builder(NEGATIVELOGLIKELIHOOD)
                .nOut(10)
                .activation(Activation.SOFTMAX)
                .build();
    }
}
