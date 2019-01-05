package com.baeldung.data.pipeline;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.Function3;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.State;
import org.apache.spark.streaming.StateSpec;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaMapWithStateDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import scala.Tuple2;

public class WordCountingAppWithCheckpoint {

    public static JavaSparkContext sparkContext;

    @SuppressWarnings("serial")
    public static void main(String[] args) throws InterruptedException {

        Logger.getLogger("org")
            .setLevel(Level.OFF);
        Logger.getLogger("akka")
            .setLevel(Level.OFF);

        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "use_a_separate_group_id_for_each_stream");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics = Arrays.asList("messages");

        SparkConf sparkConf = new SparkConf();
        sparkConf.setMaster("local[2]");
        sparkConf.setAppName("WordCountingAppWithCheckpoint");
        sparkConf.set("spark.cassandra.connection.host", "127.0.0.1");

        JavaStreamingContext streamingContext = new JavaStreamingContext(sparkConf, Durations.seconds(1));

        sparkContext = streamingContext.sparkContext();

        streamingContext.checkpoint("./.checkpoint");

        JavaInputDStream<ConsumerRecord<String, String>> messages = KafkaUtils.createDirectStream(streamingContext, LocationStrategies.PreferConsistent(), ConsumerStrategies.<String, String> Subscribe(topics, kafkaParams));

        JavaPairDStream<String, String> results = messages.mapToPair(new PairFunction<ConsumerRecord<String, String>, String, String>() {
            @Override
            public Tuple2<String, String> call(ConsumerRecord<String, String> record) {
                return new Tuple2<>(record.key(), record.value());
            }
        });

        JavaDStream<String> lines = results.map(new Function<Tuple2<String, String>, String>() {
            @Override
            public String call(Tuple2<String, String> tuple2) {
                return tuple2._2();
            }
        });

        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String x) {
                return Arrays.asList(x.split("\\s+"))
                    .iterator();
            }
        });

        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) {
                return new Tuple2<>(s, 1);
            }
        })
            .reduceByKey(new Function2<Integer, Integer, Integer>() {
                @Override
                public Integer call(Integer i1, Integer i2) {
                    return i1 + i2;
                }
            });

        Function3<String, Optional<Integer>, State<Integer>, Tuple2<String, Integer>> mappingFunc = (word, one, state) -> {
            int sum = one.orElse(0) + (state.exists() ? state.get() : 0);
            Tuple2<String, Integer> output = new Tuple2<>(word, sum);
            state.update(sum);
            return output;
        };

        JavaPairRDD<String, Integer> initialRDD = JavaPairRDD.fromJavaRDD(sparkContext.emptyRDD());

        JavaMapWithStateDStream<String, Integer, Integer, Tuple2<String, Integer>> cumulativeWordCounts = wordCounts.mapWithState(StateSpec.function(mappingFunc)
            .initialState(initialRDD));

        cumulativeWordCounts.foreachRDD(new VoidFunction<JavaRDD<Tuple2<String, Integer>>>() {
            @Override
            public void call(JavaRDD<Tuple2<String, Integer>> javaRdd) throws Exception {
                List<Tuple2<String, Integer>> wordCountList = javaRdd.collect();
                for (Tuple2<String, Integer> tuple : wordCountList) {
                    List<Word> words = Arrays.asList(new Word(tuple._1, tuple._2));
                    JavaRDD<Word> rdd = sparkContext.parallelize(words);
                    javaFunctions(rdd).writerBuilder("vocabulary", "words", mapToRow(Word.class))
                        .saveToCassandra();
                }
            }
        });

        streamingContext.start();
        streamingContext.awaitTermination();
    }
}