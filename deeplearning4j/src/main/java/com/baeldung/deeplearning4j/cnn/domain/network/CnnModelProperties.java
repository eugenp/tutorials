package com.baeldung.deeplearning4j.cnn.domain.network;

import lombok.Value;
import org.deeplearning4j.nn.conf.Updater;

@Value
public class CnnModelProperties {
    private final int epochsNum = 512;

    private final double learningRate = 0.001;

    private final Updater optimizer = Updater.ADAM;
}
