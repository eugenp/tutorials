package com.baeldung.spring.cloud;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.rdd.RDD;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PiApproximation {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("BaeldungPIApproximation").setMaster("local[2]");
        JavaSparkContext context = new JavaSparkContext(conf);
        int slices = args.length >= 1 ? Integer.valueOf(args[0]) : 2;
        int n = (100000L * slices) > Integer.MAX_VALUE ? Integer.MAX_VALUE : 100000 * slices;

        List<Integer> xs = IntStream.rangeClosed(0, n)
          .mapToObj(element -> Integer.valueOf(element))
          .collect(Collectors.toList());

        JavaRDD<Integer> dataSet = context.parallelize(xs, slices);

        JavaRDD<Integer> pointsInsideTheCircle = dataSet.map(integer -> {
           double x = Math.random() * 2 - 1;
           double y = Math.random() * 2 - 1;
           return (x*x + y*y ) < 1 ? 1: 0;
        });

        int count = pointsInsideTheCircle.reduce((integer, integer2) -> integer + integer2);

        System.out.println("The pi was estimated as:" + count / n);

        context.stop();
    }
}
