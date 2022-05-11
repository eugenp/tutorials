package com.baeldung.differences.rdd;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

public class ActionsUnitTest {

    public static final Logger LOG = LoggerFactory.getLogger(ActionsUnitTest.class);

    private static JavaRDD<String> tourists;
    private static JavaSparkContext sc;
    public static final String COMMA_DELIMITER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    @BeforeClass
    public static void init() {
        SparkConf conf = new SparkConf().setAppName("reduce")
            .setMaster("local[*]");
        sc = new JavaSparkContext(conf);
        tourists = sc.textFile("data/Tourist.csv").filter(line -> !line.startsWith("Region"));
    }

    @AfterClass
    public static void cleanup() {
        sc.close();
    }

    @Test
    public void whenDistinctCount_thenReturnDistinctNumRecords() {
        JavaRDD<String> countries = tourists.map(line -> {
            String[] columns = line.split(COMMA_DELIMITER);
            return columns[1];
        })
            .distinct();
        Long numberOfCountries = countries.count();
        LOG.debug("Count: {}", numberOfCountries);

        assertEquals(Long.valueOf(220), numberOfCountries);
    }

    @Test
    public void whenReduceByKeySum_thenTotalValuePerKey() {
        JavaRDD<String> touristsExpenditure = tourists.filter(line -> line.split(COMMA_DELIMITER)[3].contains("expenditure"));

        JavaPairRDD<String, Double> expenditurePairRdd = touristsExpenditure.mapToPair(line -> {
            String[] columns = line.split(COMMA_DELIMITER);
            return new Tuple2<>(columns[1], Double.valueOf(columns[6]));
        });
        List<Tuple2<String, Double>> totalByCountry = expenditurePairRdd
          .reduceByKey(Double::sum)
          .collect();

        LOG.debug("Total per Country: {}", totalByCountry);

        for(Tuple2<String, Double> tuple : totalByCountry) {
            if (tuple._1.equals("Mexico")) {
                assertEquals(Double.valueOf(99164), tuple._2);
            }
        }
    }

}
