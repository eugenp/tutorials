package com.baeldung.deeplearning4j.cnn;

import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.util.stream.IntStream;

@Slf4j
class CnnModel {

    private final IDataSetService dataSetService;

    private final MultiLayerNetwork network;

    private final CnnModelProperties properties;

    CnnModel(IDataSetService dataSetService, CnnModelProperties properties) {

        this.dataSetService = dataSetService;
        this.properties = properties;

        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
          .seed(1611)
          .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
          .learningRate(properties.getLearningRate())
          .regularization(true)
          .updater(properties.getOptimizer())
          .list()
          .layer(0, conv5x5())
          .layer(1, pooling2x2Stride2())
          .layer(2, conv3x3Stride1Padding2())
          .layer(3, pooling2x2Stride1())
          .layer(4, conv3x3Stride1Padding1())
          .layer(5, pooling2x2Stride1())
          .layer(6, dense())
          .pretrain(false)
          .backprop(true)
          .setInputType(dataSetService.inputType())
          .build();

        network = new MultiLayerNetwork(configuration);
    }

    void train() {
        network.init();
        int epochsNum = properties.getEpochsNum();
        IntStream.range(1, epochsNum + 1).forEach(epoch -> {
            log.info("Epoch {} / {}", epoch, epochsNum);
            network.fit(dataSetService.trainIterator());
        });
    }

    Evaluation evaluate() {
        return network.evaluate(dataSetService.testIterator());
    }

    private ConvolutionLayer conv5x5() {
        return new ConvolutionLayer.Builder(5, 5)
                .nIn(3)
                .nOut(16)
                .stride(1, 1)
                .padding(1, 1)
                .weightInit(WeightInit.XAVIER_UNIFORM)
                .activation(Activation.RELU)
                .build();
    }

    private SubsamplingLayer pooling2x2Stride2() {
        return new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                .kernelSize(2, 2)
                .stride(2, 2)
                .build();
    }

    private ConvolutionLayer conv3x3Stride1Padding2() {
        return new ConvolutionLayer.Builder(3, 3)
                .nOut(32)
                .stride(1, 1)
                .padding(2, 2)
                .weightInit(WeightInit.XAVIER_UNIFORM)
                .activation(Activation.RELU)
                .build();
    }

    private SubsamplingLayer pooling2x2Stride1() {
        return new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                .kernelSize(2, 2)
                .stride(1, 1)
                .build();
    }

    private ConvolutionLayer conv3x3Stride1Padding1() {
        return new ConvolutionLayer.Builder(3, 3)
                .nOut(64)
                .stride(1, 1)
                .padding(1, 1)
                .weightInit(WeightInit.XAVIER_UNIFORM)
                .activation(Activation.RELU)
                .build();
    }

    private OutputLayer dense() {
        return new OutputLayer.Builder(LossFunctions.LossFunction.MEAN_SQUARED_LOGARITHMIC_ERROR)
                .activation(Activation.SOFTMAX)
                .weightInit(WeightInit.XAVIER_UNIFORM)
                .nOut(dataSetService.labels().size() - 1)
                .build();
    }
}
