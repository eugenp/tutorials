package com.baeldung.differences.rdd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class TransformationsUnitTest {

    public static final String COMMA_DELIMITER = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

    private static JavaSparkContext sc;
    private static JavaRDD<String> tourists;

    @BeforeClass
    public static void init() {
        SparkConf conf = new SparkConf().setAppName("uppercaseCountries")
            .setMaster("local[*]");
        sc = new JavaSparkContext(conf);
        tourists = sc.textFile("data/Tourist.csv")
            .filter(line -> !line.startsWith("Region")); //filter header row

        // delete previous output dir and files
        FileUtils.deleteQuietly(new File("data/output"));
    }

    @AfterClass
    public static void cleanup() {
        sc.close();
    }

    @Test
    public void whenMapUpperCase_thenCountryNameUppercased() {
        JavaRDD<String> upperCaseCountries = tourists.map(line -> {
            String[] columns = line.split(COMMA_DELIMITER);
            return columns[1].toUpperCase();
        })
            .distinct();

        upperCaseCountries.saveAsTextFile("data/output/uppercase.txt");

        upperCaseCountries.foreach(country -> {
            //replace non alphanumerical characters
            country = country.replaceAll("[^a-zA-Z]", "");
            assertTrue(StringUtils.isAllUpperCase(country));
        });
    }

    @Test
    public void whenFilterByCountry_thenShowRequestedCountryRecords() {
        JavaRDD<String> touristsInMexico = tourists.filter(line -> line.split(COMMA_DELIMITER)[1].equals("Mexico"));

        touristsInMexico.saveAsTextFile("data/output/touristInMexico.txt");

        touristsInMexico.foreach(record -> {
            assertEquals("Mexico", record.split(COMMA_DELIMITER)[1]);
        });
    }

}
