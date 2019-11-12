package com.baeldung.cnn

import org.datavec.api.records.reader.impl.collection.ListStringRecordReader
import org.datavec.api.split.ListStringSplit
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator
import org.deeplearning4j.eval.Evaluation
import org.deeplearning4j.nn.conf.NeuralNetConfiguration
import org.deeplearning4j.nn.conf.inputs.InputType
import org.deeplearning4j.nn.conf.layers.*
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.learning.config.Adam
import org.nd4j.linalg.lossfunctions.LossFunctions

object ConvolutionalNeuralNetwork {

    @JvmStatic
    fun main(args: Array<String>) {
        val dataset = ZalandoMNISTDataSet().load()
        dataset.shuffle()
        val trainDatasetIterator = createDatasetIterator(dataset.subList(0, 50_000))
        val testDatasetIterator = createDatasetIterator(dataset.subList(50_000, 60_000))

        val cnn = buildCNN()
        learning(cnn, trainDatasetIterator)
        testing(cnn, testDatasetIterator)
    }

    private fun createDatasetIterator(dataset: MutableList<List<String>>): RecordReaderDataSetIterator {
        val listStringRecordReader = ListStringRecordReader()
        listStringRecordReader.initialize(ListStringSplit(dataset))
        return RecordReaderDataSetIterator(listStringRecordReader, 128, 28 * 28, 10)
    }

    private fun buildCNN(): MultiLayerNetwork {
        val multiLayerNetwork = MultiLayerNetwork(NeuralNetConfiguration.Builder()
                .seed(123)
                .l2(0.0005)
                .updater(Adam())
                .weightInit(WeightInit.XAVIER)
                .list()
                .layer(0, buildInitialConvolutionLayer())
                .layer(1, buildBatchNormalizationLayer())
                .layer(2, buildPoolingLayer())
                .layer(3, buildConvolutionLayer())
                .layer(4, buildBatchNormalizationLayer())
                .layer(5, buildPoolingLayer())
                .layer(6, buildDenseLayer())
                .layer(7, buildBatchNormalizationLayer())
                .layer(8, buildDenseLayer())
                .layer(9, buildOutputLayer())
                .setInputType(InputType.convolutionalFlat(28, 28, 1))
                .backprop(true)
                .build())
        multiLayerNetwork.init()
        return multiLayerNetwork
    }

    private fun buildOutputLayer(): OutputLayer? {
        return OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                .nOut(10)
                .activation(Activation.SOFTMAX)
                .build()
    }

    private fun buildDenseLayer(): DenseLayer? {
        return DenseLayer.Builder().activation(Activation.RELU)
                .nOut(500)
                .dropOut(0.5)
                .build()
    }

    private fun buildPoolingLayer(): SubsamplingLayer? {
        return SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                .kernelSize(2, 2)
                .stride(2, 2)
                .build()
    }

    private fun buildBatchNormalizationLayer() = BatchNormalization.Builder().build()

    private fun buildConvolutionLayer(): ConvolutionLayer? {
        return ConvolutionLayer.Builder(5, 5)
                .stride(1, 1) // nIn need not specified in later layers
                .nOut(50)
                .activation(Activation.IDENTITY)
                .build()
    }

    private fun buildInitialConvolutionLayer(): ConvolutionLayer? {
        return ConvolutionLayer.Builder(5, 5)
                .nIn(1)
                .stride(1, 1)
                .nOut(20)
                .activation(Activation.IDENTITY)
                .build()
    }

    private fun learning(cnn: MultiLayerNetwork, trainSet: RecordReaderDataSetIterator) {
        for (i in 0 until 10) {
            cnn.fit(trainSet)
        }
    }

    private fun testing(cnn: MultiLayerNetwork, testSet: RecordReaderDataSetIterator) {
        val evaluation = Evaluation(10)
        while (testSet.hasNext()) {
            val next = testSet.next()
            val output = cnn.output(next.features)
            evaluation.eval(next.labels, output)
        }

        println(evaluation.stats())
        println(evaluation.confusionToString())
    }
}