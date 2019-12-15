package com.baeldung.cnn;

import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;

import java.io.IOException;

class CNNforMNISTUnitTest {

    @Test
    void whenAModelAlreadyExists_ThenTheResultIsQuickAndAccurate() throws IOException {
        Evaluation eval = new Evaluation(10);
        MnistDataSetIterator mnistTest = new MnistDataSetIterator(128, false, 123);
        CNNforMNIST mnistCNN = CNNforMNIST.getInstance("mnistCNN");

        while (mnistTest.hasNext()) {
            DataSet next = mnistTest.next();
            INDArray output = mnistCNN.output(next.getFeatures());
            eval.eval(next.getLabels(), output);
        }

        System.out.println(eval.stats());
    }
}