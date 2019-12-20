package com.baeldung.cnn;

import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

class CNNforMNISTUnitTest {

    @Test
    void whenAModelAlreadyExists_ThenTheResultIsQuickAndAccurate() throws IOException, InterruptedException {
        Evaluation eval = new Evaluation(10);
        RecordReaderDataSetIterator mnistTest = MNISTDataSet.load(true);
        MultiLayerNetwork mnistCNN = CNNforMNIST.getInstance("src/main/resources/mnist/mnistCNN");

        while (mnistTest.hasNext()) {
            DataSet next = mnistTest.next();
            INDArray output = mnistCNN.output(next.getFeatures());
            eval.eval(next.getLabels(), output);
        }

        System.out.println(eval.stats());

        assertTrue(eval.accuracy() > 0.9);
        assertTrue(eval.precision() > 0.9);
        assertTrue(eval.recall() > 0.9);
        assertTrue(eval.f1() > 0.9);
    }
}