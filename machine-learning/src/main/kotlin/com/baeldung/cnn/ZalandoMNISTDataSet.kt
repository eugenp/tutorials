package com.baeldung.cnn

import java.io.File
import java.nio.ByteBuffer
import java.util.*
import java.util.stream.Collectors
import kotlin.streams.asStream

class ZalandoMNISTDataSet {
    private val OFFSET_SIZE = 4 //in bytes
    private val NUM_ITEMS_OFFSET = 4
    private val ITEMS_SIZE = 4
    private val ROWS = 28
    private val COLUMNS = 28
    private val IMAGE_OFFSET = 16
    private val IMAGE_SIZE = ROWS * COLUMNS

    fun load(): MutableList<List<String>> {
        val labelsFile = File("machine-learning/src/main/resources/train-labels-idx1-ubyte")
        val imagesFile = File("machine-learning/src/main/resources/train-images-idx3-ubyte")

        val labelBytes = labelsFile.readBytes()
        val imageBytes = imagesFile.readBytes()

        val byteLabelCount = Arrays.copyOfRange(labelBytes, NUM_ITEMS_OFFSET, NUM_ITEMS_OFFSET + ITEMS_SIZE)
        val numberOfLabels = ByteBuffer.wrap(byteLabelCount).int

        val list = mutableListOf<List<String>>()

        for (i in 0 until numberOfLabels) {
            val label = labelBytes[OFFSET_SIZE + ITEMS_SIZE + i]
            val startBoundary = i * IMAGE_SIZE + IMAGE_OFFSET
            val endBoundary = i * IMAGE_SIZE + IMAGE_OFFSET + IMAGE_SIZE
            val imageData = Arrays.copyOfRange(imageBytes, startBoundary, endBoundary)

            val imageDataList = imageData.iterator()
                    .asSequence()
                    .asStream().map { b -> b.toString() }
                    .collect(Collectors.toList())
            imageDataList.add(label.toString())
            list.add(imageDataList)
        }
        return list
    }
}