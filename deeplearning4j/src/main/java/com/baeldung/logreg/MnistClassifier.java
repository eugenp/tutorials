package com.baeldung.logreg;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.split.FileSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handwritten digit image classification based on LeNet-5 architecture by Yann LeCun.
 * 
 * This code accompanies the article "Logistic  regression in Java" and is heavily based on 
 * <a href="https://github.com/deeplearning4j/dl4j-examples/blob/master/dl4j-examples/src/main/java/org/deeplearning4j/examples/convolution/mnist/MnistClassifier.java">MnistClassifier</a>. 
 * Some minor changes have been made in order to make article's flow smoother.
 * 
 */

public class MnistClassifier {
    private static final Logger logger = LoggerFactory.getLogger(MnistClassifier.class);
    private static final String basePath = System.getProperty("java.io.tmpdir") + File.separator + "mnist" + File.separator;
    private static final File modelPath = new File(basePath + "mnist-model.zip");
    private static final String dataUrl = "http://github.com/myleott/mnist_png/raw/master/mnist_png.tar.gz";

    public static void main(String[] args) throws Exception {
        // input image sizes in pixels
        int height = 28;
        int width = 28;
        // input image colour depth (1 for gray scale images)
        int channels = 1;
        // the number of output classes
        int outputClasses = 10;
        // number of samples that will be propagated through the network in each iteration
        int batchSize = 54;
        // total number of training epochs
        int epochs = 1;

        // initialize a pseudorandom number generator
        int seed = 1234;
        Random randNumGen = new Random(seed);

        final String path = basePath + "mnist_png" + File.separator;
        if (!new File(path).exists()) {
            logger.info("Downloading data {}", dataUrl);
            String localFilePath = basePath + "mnist_png.tar.gz";
            File file = new File(localFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                Utils.downloadAndSave(dataUrl, file);
                Utils.extractTarArchive(file, basePath);
            }
        } else {
            logger.info("Using the local data from folder {}", path);
        }

        logger.info("Vectorizing the data from folder {}", path);
        // vectorization of train data
        File trainData = new File(path + "training");
        FileSplit trainSplit = new FileSplit(trainData, NativeImageLoader.ALLOWED_FORMATS, randNumGen);
        // use parent directory name as the image label
        ParentPathLabelGenerator labelMaker = new ParentPathLabelGenerator();
        ImageRecordReader trainRR = new ImageRecordReader(height, width, channels, labelMaker);
        trainRR.initialize(trainSplit);
        DataSetIterator train = new RecordReaderDataSetIterator(trainRR, batchSize, 1, outputClasses);

        // pixel values from 0-255 to 0-1 (min-max scaling)
        DataNormalization imageScaler = new ImagePreProcessingScaler();
        imageScaler.fit(train);
        train.setPreProcessor(imageScaler);

        // vectorization of test data
        File testData = new File(path + "testing");
        FileSplit testSplit = new FileSplit(testData, NativeImageLoader.ALLOWED_FORMATS, randNumGen);
        ImageRecordReader testRR = new ImageRecordReader(height, width, channels, labelMaker);
        testRR.initialize(testSplit);
        DataSetIterator test = new RecordReaderDataSetIterator(testRR, batchSize, 1, outputClasses);
        // same normalization for better results
        test.setPreProcessor(imageScaler);

        logger.info("Network configuration and training...");
        // reduce the learning rate as the number of training epochs increases
        // iteration #, learning rate
        Map<Integer, Double> learningRateSchedule = new HashMap<>();
        learningRateSchedule.put(0, 0.06);
        learningRateSchedule.put(200, 0.05);
        learningRateSchedule.put(600, 0.028);
        learningRateSchedule.put(800, 0.0060);
        learningRateSchedule.put(1000, 0.001);

        final ConvolutionLayer layer1 = new ConvolutionLayer.Builder(5, 5).nIn(channels)
            .stride(1, 1)
            .nOut(20)
            .activation(Activation.IDENTITY)
            .build();
        final SubsamplingLayer layer2 = new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX).kernelSize(2, 2)
            .stride(2, 2)
            .build();
        // nIn need not specified in later layers
        final ConvolutionLayer layer3 = new ConvolutionLayer.Builder(5, 5).stride(1, 1)
            .nOut(50)
            .activation(Activation.IDENTITY)
            .build();
        final DenseLayer layer4 = new DenseLayer.Builder().activation(Activation.RELU)
            .nOut(500)
            .build();
        final OutputLayer layer5 = new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD).nOut(outputClasses)
            .activation(Activation.SOFTMAX)
            .build();
        final MultiLayerConfiguration config = new NeuralNetConfiguration.Builder().seed(seed)
            .l2(0.0005) // ridge regression value
            .updater(new Nesterovs()) //TODO new MapSchedule(ScheduleType.ITERATION, learningRateSchedule)
            .weightInit(WeightInit.XAVIER)
            .list()
            .layer(0, layer1)
            .layer(1, layer2)
            .layer(2, layer3)
            .layer(3, layer2)
            .layer(4, layer4)
            .layer(5, layer5)
            .setInputType(InputType.convolutionalFlat(height, width, channels))
            .build();

        final MultiLayerNetwork model = new MultiLayerNetwork(config);
        model.init();
        model.setListeners(new ScoreIterationListener(100));
        logger.info("Total num of params: {}", model.numParams());

        // evaluation while training (the score should go down)
        for (int i = 0; i < epochs; i++) {
            model.fit(train);
            logger.info("Completed epoch {}", i);
            train.reset();
            test.reset();
        }
        Evaluation eval = model.evaluate(test);
        logger.info(eval.stats());

        ModelSerializer.writeModel(model, modelPath, true);
        logger.info("The MINIST model has been saved in {}", modelPath.getPath());
    }
}
