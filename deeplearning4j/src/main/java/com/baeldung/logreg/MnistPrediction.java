package com.baeldung.logreg;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MnistPrediction {
    private static final Logger logger = LoggerFactory.getLogger(MnistPrediction.class);
    private static final File modelPath = new File(System.getProperty("java.io.tmpdir") + "mnist" + File.separator + "mnist-model.zip");
    private static final int height = 28;
    private static final int width = 28;
    private static final int channels = 1;

    /**
     * Opens a popup that allows to select a file from the filesystem.
     * @return
     */
    public static String fileChose() {
        JFileChooser fc = new JFileChooser();
        int ret = fc.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        if (!modelPath.exists()) {
            logger.info("The model not found. Have you trained it?");
            return;
        }
        MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(modelPath);
        String path = fileChose();
        File file = new File(path);

        INDArray image = new NativeImageLoader(height, width, channels).asMatrix(file);
        new ImagePreProcessingScaler(0, 1).transform(image);

        // Pass through to neural Net
        INDArray output = model.output(image);

        logger.info("File: {}", path);
        logger.info("Probabilities: {}", output);
    }

}
