package com.baeldung.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

public class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

  @Override
  public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
    String[] tokens = value.toLowerCase().split("\\W+");
    
    for (String token : tokens) {
      if (token.length() > 0) {
        out.collect(new Tuple2<>(token, 1));
      }
    }
  }
}