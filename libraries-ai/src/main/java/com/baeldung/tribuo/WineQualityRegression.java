package com.baeldung.tribuo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tribuo.DataSource;
import org.tribuo.Dataset;
import org.tribuo.Model;
import org.tribuo.MutableDataset;
import org.tribuo.Trainer;
import org.tribuo.common.tree.AbstractCARTTrainer;
import org.tribuo.common.tree.RandomForestTrainer;
import org.tribuo.data.csv.CSVIterator;
import org.tribuo.data.csv.CSVLoader;
import org.tribuo.evaluation.TrainTestSplitter;
import org.tribuo.regression.RegressionFactory;
import org.tribuo.regression.Regressor;
import org.tribuo.regression.ensemble.AveragingCombiner;
import org.tribuo.regression.evaluation.RegressionEvaluation;
import org.tribuo.regression.evaluation.RegressionEvaluator;
import org.tribuo.regression.rtree.CARTRegressionTrainer;
import org.tribuo.regression.rtree.impurity.MeanSquaredError;

import com.oracle.labs.mlrg.olcut.provenance.ProvenanceUtil;

public class WineQualityRegression {

    public static final Logger log = LoggerFactory.getLogger(WineQualityRegression.class);

    public static final String DATASET_PATH = "src/main/resources/dataset/winequality-red.csv";
    public static final String MODEL_PATH = "src/main/resources/model/winequality-red-regressor.ser";

    public Model<Regressor> model;
    public Trainer<Regressor> trainer;
    public Dataset<Regressor> trainSet;
    public Dataset<Regressor> testSet;

    public static void main(String[] args) throws Exception {
        WineQualityRegression wineQualityRegression = new WineQualityRegression();

        wineQualityRegression.createDatasets();
        wineQualityRegression.createTrainer();
        wineQualityRegression.evaluateModels();
        wineQualityRegression.saveModel();
    }

    public void createTrainer() {
        CARTRegressionTrainer subsamplingTree = new CARTRegressionTrainer(Integer.MAX_VALUE, AbstractCARTTrainer.MIN_EXAMPLES, 0.001f, 0.7f,
            new MeanSquaredError(), Trainer.DEFAULT_SEED);

        trainer = new RandomForestTrainer<>(subsamplingTree, new AveragingCombiner(), 10);
        model = trainer.train(trainSet);
    }

    public void createDatasets() throws Exception {
        RegressionFactory regressionFactory = new RegressionFactory();
        CSVLoader<Regressor> csvLoader = new CSVLoader<>(';', CSVIterator.QUOTE, regressionFactory);
        DataSource<Regressor> dataSource = csvLoader.loadDataSource(Paths.get(DATASET_PATH), "quality");

        TrainTestSplitter<Regressor> dataSplitter = new TrainTestSplitter<>(dataSource, 0.7, 1L);

        trainSet = new MutableDataset<>(dataSplitter.getTrain());
        log.info(String.format("Train set size = %d, num of features = %d", trainSet.size(), trainSet.getFeatureMap()
            .size()));

        testSet = new MutableDataset<>(dataSplitter.getTest());
        log.info(String.format("Test set size = %d, num of features = %d", testSet.size(), testSet.getFeatureMap()
            .size()));
    }

    public void evaluateModels() throws Exception {
        log.info("Training model");
        evaluate(model, "trainSet", trainSet);

        log.info("Testing model");
        evaluate(model, "testSet", testSet);

        log.info("Dataset Provenance: --------------------");
        log.info(ProvenanceUtil.formattedProvenanceString(model.getProvenance()
            .getDatasetProvenance()));
        log.info("Trainer Provenance: --------------------");
        log.info(ProvenanceUtil.formattedProvenanceString(model.getProvenance()
            .getTrainerProvenance()));
    }

    public void evaluate(Model<Regressor> model, String datasetName, Dataset<Regressor> dataset) {
        log.info("Results for " + datasetName + "---------------------");
        RegressionEvaluator evaluator = new RegressionEvaluator();
        RegressionEvaluation evaluation = evaluator.evaluate(model, dataset);

        Regressor dimension0 = new Regressor("DIM-0", Double.NaN);

        log.info("MAE: " + evaluation.mae(dimension0));
        log.info("RMSE: " + evaluation.rmse(dimension0));
        log.info("R^2: " + evaluation.r2(dimension0));
    }

    public void saveModel() throws Exception {
        File modelFile = new File(MODEL_PATH);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(modelFile))) {
            objectOutputStream.writeObject(model);
        }
    }
}