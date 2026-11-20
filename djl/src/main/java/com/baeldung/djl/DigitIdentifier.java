package com.baeldung.djl;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ai.djl.Application;
import ai.djl.MalformedModelException;
import ai.djl.engine.Engine;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;

public class DigitIdentifier {
    final static Logger logger = LoggerFactory.getLogger(DigitIdentifier.class);

    public String identifyDigit(String imagePath)
            throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {
        Criteria<Image, Classifications> criteria = Criteria.builder()
            .optApplication(Application.CV.IMAGE_CLASSIFICATION)
            .setTypes(Image.class, Classifications.class)
            .optFilter("dataset", "mnist")
            .build();

        ZooModel<Image, Classifications> model = criteria.loadModel();

        logger.info("Using Engine:{} ", Engine.getInstance().getEngineName());
        Classifications classifications = null;
        try (Predictor<Image, Classifications> predictor = model.newPredictor()) {
            classifications = predictor.predict(this.loadImage(imagePath));
        }

        return classifications.best().getClassName();
    }

    private Image loadImage(String imagePathString) throws IOException {
        ClassLoader classLoader = DigitIdentifier.class.getClassLoader();
        URL url = classLoader.getResource(imagePathString);

        Path imagePath = Paths.get(url.getPath()); // 28x28 handwritten digit

        return ImageFactory.getInstance().fromFile(imagePath);
    }
}