package com.baeldung.tribuo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tribuo.Model;
import org.tribuo.Prediction;
import org.tribuo.impl.ArrayExample;
import org.tribuo.regression.Regressor;

public class WineQualityPredictor {

    private static final Logger log = LoggerFactory.getLogger(WineQualityPredictor.class);

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File modelFile = new File("src/main/resources/model/winequality-red-regressor.ser");
        Model<Regressor> loadedModel = null;

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(modelFile))) {
            loadedModel = (Model<Regressor>) objectInputStream.readObject();
        }

        ArrayExample<Regressor> wineAttribute = new ArrayExample<Regressor>(new Regressor("quality", Double.NaN));
        wineAttribute.add("fixed acidity", 7.4f);
        wineAttribute.add("volatile acidity", 0.7f);
        wineAttribute.add("citric acid", 0.47f);
        wineAttribute.add("residual sugar", 1.9f);
        wineAttribute.add("chlorides", 0.076f);
        wineAttribute.add("free sulfur dioxide", 11.0f);
        wineAttribute.add("total sulfur dioxide", 34.0f);
        wineAttribute.add("density", 0.9978f);
        wineAttribute.add("pH", 3.51f);
        wineAttribute.add("sulphates", 0.56f);
        wineAttribute.add("alcohol", 9.4f);

        Prediction<Regressor> prediction = loadedModel.predict(wineAttribute);
        double predictQuality = prediction.getOutput()
            .getValues()[0];
        log.info("Predicted wine quality: " + predictQuality);

    }

}
