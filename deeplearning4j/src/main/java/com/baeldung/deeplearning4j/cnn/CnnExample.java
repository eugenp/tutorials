package com.baeldung.deeplearning4j.cnn;


import com.baeldung.deeplearning4j.cnn.domain.network.CnnModel;
import com.baeldung.deeplearning4j.cnn.domain.network.CnnModelProperties;
import com.baeldung.deeplearning4j.cnn.service.dataset.CifarDataSetService;
import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.eval.Evaluation;

@Slf4j
public class CnnExample {

    public static void main(String... args) {
        CnnModel network = new CnnModel(new CifarDataSetService(), new CnnModelProperties());

        network.train();
        Evaluation evaluation = network.evaluate();

        log.info(evaluation.stats());
    }
}
