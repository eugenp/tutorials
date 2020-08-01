package com.baeldung.deeplearning4j.cnn;

import lombok.Getter;
import org.deeplearning4j.datasets.iterator.impl.CifarDataSetIterator;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.util.List;

@Getter
class CifarDataSetService implements IDataSetService {

    private final InputType inputType = InputType.convolutional(32, 32, 3);
    private final int trainImagesNum = 512;
    private final int testImagesNum = 128;
    private final int trainBatch = 16;
    private final int testBatch = 8;

    private final CifarDataSetIterator trainIterator;

    private final CifarDataSetIterator testIterator;

    CifarDataSetService() {
        trainIterator = new CifarDataSetIterator(trainBatch, trainImagesNum, true);
        testIterator = new CifarDataSetIterator(testBatch, testImagesNum, false);
    }

    @Override
    public DataSetIterator trainIterator() {
        return trainIterator;
    }

    @Override
    public DataSetIterator testIterator() {
        return testIterator;
    }

    @Override
    public InputType inputType() {
        return inputType;
    }

    @Override
    public List<String> labels() {
        return trainIterator.getLabels();
    }
}
