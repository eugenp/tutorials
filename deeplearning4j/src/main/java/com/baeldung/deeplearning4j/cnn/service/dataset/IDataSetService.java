package com.baeldung.deeplearning4j.cnn.service.dataset;

import org.deeplearning4j.nn.conf.inputs.InputType;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.util.List;

public interface IDataSetService {
    DataSetIterator trainIterator();

    DataSetIterator testIterator();

    InputType inputType();

    List<String> labels();
}
