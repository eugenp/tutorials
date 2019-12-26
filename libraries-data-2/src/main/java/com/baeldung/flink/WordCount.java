package com.baeldung.flink;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.aggregation.Aggregations;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.List;

public class WordCount {

    public static DataSet<Tuple2<String, Integer>> startWordCount(ExecutionEnvironment env, List<String> lines) throws Exception {
        DataSet<String> text = env.fromCollection(lines);

        return text.flatMap(new LineSplitter()).groupBy(0).aggregate(Aggregations.SUM, 1);

    }
}