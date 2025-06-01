package com.baeldung.tribuo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.tribuo.DataSource;
import org.tribuo.Model;
import org.tribuo.MutableDataset;
import org.tribuo.data.csv.CSVIterator;
import org.tribuo.data.csv.CSVLoader;
import org.tribuo.evaluation.TrainTestSplitter;
import org.tribuo.regression.RegressionFactory;
import org.tribuo.regression.Regressor;

class WineQualityRegressionUnitTest {

    private static final String DATASET_PATH = "src/main/resources/dataset/winequality-red.csv";
    private static final String MODEL_PATH = "src/main/resources/model/winequality-red-regressor.ser";

    @Test
    void givenDataset_whenSplittingIntoTrainAndTestSets_thenCorrectSizesAndFeatures() throws IOException {
        RegressionFactory factory = new RegressionFactory();
        CSVLoader<Regressor> loader = new CSVLoader<>(';', CSVIterator.QUOTE, factory);
        DataSource<Regressor> dataSource = loader.loadDataSource(Paths.get(DATASET_PATH), "quality");
        TrainTestSplitter<Regressor> splitter = new TrainTestSplitter<>(dataSource, 0.7, 1L);
        MutableDataset<Regressor> trainSet = new MutableDataset<>(splitter.getTrain());
        MutableDataset<Regressor> testSet = new MutableDataset<>(splitter.getTest());
        assertEquals(1119, trainSet.size(), "Expected ~70% of 1599 instances in training set");
        assertEquals(480, testSet.size(), "Expected ~30% of 1599 instances in test set");
        assertEquals(11, trainSet.getFeatureMap()
            .size(), "Training set should have 11 features");
        assertEquals(11, testSet.getFeatureMap()
            .size(), "Test set should have 11 features");
    }

    @Test
    void givenATrainModel_whenLoadedFromFile_thenModelIsNotNull() throws Exception {
        WineQualityRegression wineQualityRegression = new WineQualityRegression();

        wineQualityRegression.createDatasets();
        wineQualityRegression.createTrainer();
        wineQualityRegression.evaluateModels();
        wineQualityRegression.saveModel();

        Model<Regressor> loadedModel = null;
        File modelFile = new File(MODEL_PATH);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(modelFile))) {
            loadedModel = (Model<Regressor>) objectInputStream.readObject();
        }

        assertNotNull(loadedModel, "Loaded model should not be null");
    }

}