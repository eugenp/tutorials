package com.baeldung.cnn;

import org.datavec.api.records.reader.impl.collection.ListStringRecordReader;
import org.datavec.api.split.ListStringSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.nio.file.Files.readAllBytes;
import static java.util.stream.Collectors.toList;

class MNISTDataSet {
    public static final String TRAIN_LABELS = "src/main/resources/mnist/train-labels.idx1-ubyte";
    public static final String TRAIN_IMAGES = "src/main/resources/mnist/train-images.idx3-ubyte";
    public static final String TEST_LABELS = "src/main/resources/mnist/t10k-labels.idx1-ubyte";
    public static final String TEST_IMAGES = "src/main/resources/mnist/t10k-images.idx3-ubyte";
    private static int OFFSET_SIZE = 4;
    private static int NUM_ITEMS_OFFSET = 4;
    private static int ITEMS_SIZE = 4;
    private static int ROWS = 28;
    private static int COLUMNS = 28;
    private static int IMAGE_OFFSET = 16;
    private static int IMAGE_SIZE = ROWS * COLUMNS;

    public static RecordReaderDataSetIterator loadTest() throws IOException, InterruptedException {
        return load(new File(TEST_LABELS), new File(TEST_IMAGES));
    }

    public static RecordReaderDataSetIterator loadTraining() throws IOException, InterruptedException {
        return load(new File(TRAIN_LABELS), new File(TRAIN_IMAGES));
    }

    public static RecordReaderDataSetIterator load(File labelsFile, File imagesFile) throws IOException, InterruptedException {
        byte[] labelBytes = readAllBytes(labelsFile.toPath());
        byte[] imageBytes = readAllBytes(imagesFile.toPath());

        byte[] byteLabelCount = Arrays.copyOfRange(labelBytes, NUM_ITEMS_OFFSET, NUM_ITEMS_OFFSET + ITEMS_SIZE);
        int numberOfLabels = ByteBuffer.wrap(byteLabelCount).getInt();

        List<List<String>> list = new ArrayList<>();

        for (int i = 0; i < numberOfLabels; i++) {
            byte label = labelBytes[OFFSET_SIZE + ITEMS_SIZE + i];
            int startBoundary = i * IMAGE_SIZE + IMAGE_OFFSET;
            int endBoundary = i * IMAGE_SIZE + IMAGE_OFFSET + IMAGE_SIZE;
            byte[] imageData = Arrays.copyOfRange(imageBytes, startBoundary, endBoundary);

            List<String> imageDataList = intStream(imageData).boxed().map(String::valueOf).collect(toList());

            imageDataList.add(Byte.toString(label));
            list.add(imageDataList);
        }

        ListStringRecordReader listStringRecordReader = new ListStringRecordReader();
        listStringRecordReader.initialize(new ListStringSplit(list));
        return new RecordReaderDataSetIterator(listStringRecordReader, 128, 28 * 28, 10);
    }

    public static IntStream intStream(byte[] array) {
        return IntStream.range(0, array.length).map(idx -> array[idx]);
    }
}
