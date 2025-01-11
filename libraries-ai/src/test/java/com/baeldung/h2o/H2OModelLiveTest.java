package com.baeldung.h2o;

import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class H2OModelLiveTest {

    Logger logger = LoggerFactory.getLogger(H2OModelLiveTest.class);

    @Test
    public void givenH2OTrainedModel_whenPredictTheIrisByFeatures_thenExpectedFlowerShouldBeReturned() throws IOException, PredictException {
        String mojoFilePath = "libs/mojo.zip";

        MojoModel mojoModel = MojoModel.load(mojoFilePath);
        EasyPredictModelWrapper model = new EasyPredictModelWrapper(mojoModel);

        RowData row = new RowData();
        row.put("sepal.length", 5.1);
        row.put("sepal.width", 3.4);
        row.put("petal.length", 4.6);
        row.put("petal.width", 1.2);

        MultinomialModelPrediction prediction = model.predictMultinomial(row);

        Assertions.assertEquals("Versicolor", prediction.label);

        logger.info("Class probabilities: ");
        for (int i = 0; i < prediction.classProbabilities.length; i++) {
            logger.info("Class " + i + ": " + prediction.classProbabilities[i]);
        }
    }

    @Test
    public void givenH2OTrainedAutoMLModel_whenPredictTheIrisByFeatures_thenExpectedFlowerShouldBeReturned() throws IOException, PredictException {
        String mojoFilePath = "libs/automl_model.zip";

        MojoModel mojoModel = MojoModel.load(mojoFilePath);
        EasyPredictModelWrapper model = new EasyPredictModelWrapper(mojoModel);

        RowData row = new RowData();
        row.put("sepal.length", 5.1);
        row.put("sepal.width", 3.4);
        row.put("petal.length", 4.6);
        row.put("petal.width", 1.2);

        MultinomialModelPrediction prediction = model.predictMultinomial(row);

        Assertions.assertEquals("Versicolor", prediction.label);

        logger.info("Class probabilities: ");
        for (int i = 0; i < prediction.classProbabilities.length; i++) {
            logger.info("Class " + i + ": " + prediction.classProbabilities[i]);
        }
    }
}
