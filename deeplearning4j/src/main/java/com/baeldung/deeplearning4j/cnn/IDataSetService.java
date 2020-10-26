package com.baeldung.deeplearning4j.cnn;

import org.deeplearning4j.nn.conf.inputs.InputType;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.util.List;

interface IDataSetService {
    DataSetIterator trainIterator();

    DataSetIterator testIterator();

    InputType inputType();

    List<String> labels();
}
