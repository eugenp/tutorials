package com.baeldung.dataframes;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.column;
import static org.apache.spark.sql.functions.concat;
import static org.apache.spark.sql.functions.lit;

import java.util.Properties;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

public class CustomerDataAggregationPipeline {
    private static final SparkSession SPARK_SESSION = SparkDriver.getSparkSession();

    private final Properties dbProperties;

    public CustomerDataAggregationPipeline(Properties properties) {
        dbProperties = properties;
    }

    public static void main(String[] args) {
        // replace with actual DB properties
        Properties dbProps = new Properties();
        dbProps.setProperty("connectionURL", "jdbc:postgresql://localhost:5432/customerdb");
        dbProps.setProperty("driver", "org.postgresql.Driver");
        dbProps.setProperty("user", "postgres");
        dbProps.setProperty("password", "postgres");

        new CustomerDataAggregationPipeline(dbProps).run();
    }

    public void run() {
        Dataset<Row> ebayDFRaw = ingestCustomerDataFromEbay();
        Dataset<Row> ebayDf = normalizeCustomerDataFromEbay(ebayDFRaw);

        Dataset<Row> amazonDFRaw = ingestCustomerDataFromAmazon();
        Dataset<Row> amazonDf = normalizeCustomerDataFromAmazon(amazonDFRaw);

        Dataset<Row> combineDataframes = combineDataframes(ebayDf, amazonDf);

        Dataset<Row> rowDataset = aggregateYearlySalesByGender(combineDataframes);

        exportData(rowDataset);
    }

    private static Dataset<Row> ingestCustomerDataFromAmazon() {
        return SPARK_SESSION.read()
            .format("csv")
            .option("header", "true")
            .schema(SchemaFactory.customerSchema())
            .option("dateFormat", "m/d/YYYY")
            .load("data/customerData.csv");
    }

    private static Dataset<Row> ingestCustomerDataFromEbay() {
        return SPARK_SESSION.read()
            .format("org.apache.spark.sql.execution.datasources.json.JsonFileFormat")
            .option("multiline", true)
            .load("data/customerData.json");
    }

    private static Dataset<Row> combineDataframes(Dataset<Row> df1, Dataset<Row> df2) {
        return df1.unionByName(df2);
    }

    private static Dataset<Row> normalizeCustomerDataFromEbay(Dataset<Row> rawDataset) {
        Dataset<Row> transformedDF = rawDataset.withColumn("id", concat(rawDataset.col("zoneId"), lit("-"), rawDataset.col("customerId")))
            .drop(column("customerId"))
            .withColumn("source", lit("ebay"))
            .withColumn("city", rawDataset.col("contact.customer_city"))
            .drop(column("contact"))
            .drop(column("zoneId"))
            .withColumn("year", functions.year(col("transaction_date")))
            .drop("transaction_date")
            .withColumn("firstName", functions.split(column("name"), " ")
                .getItem(0))
            .withColumn("lastName", functions.split(column("name"), " ")
                .getItem(1))
            .drop(column("name"));

        print(transformedDF);
        return transformedDF;
    }

    private static Dataset<Row> normalizeCustomerDataFromAmazon(Dataset<Row> rawDataset) {

        Dataset<Row> transformedDF = rawDataset.withColumn("id", concat(rawDataset.col("zoneId"), lit("-"), rawDataset.col("id")))
            .withColumn("source", lit("amazon"))
            .withColumnRenamed("CITY", "city")
            .withColumnRenamed("PHONE_NO", "contactNo")
            .withColumnRenamed("POSTCODE", "postCode")
            .withColumnRenamed("FIRST_NAME", "firstName")
            .drop(column("MIDDLE_NAME"))
            .drop(column("zoneId"))
            .withColumnRenamed("LAST_NAME", "lastName")
            .withColumn("year", functions.year(col("transaction_date")))
            .drop("transaction_date");

        print(transformedDF);
        return transformedDF;
    }

    private static Dataset<Row> aggregateYearlySalesByGender(Dataset<Row> dataset) {

        Dataset<Row> aggDF = dataset.groupBy(column("year"), column("source"), column("gender"))
            .sum("transaction_amount")
            .withColumnRenamed("sum(transaction_amount)", "annual_spending")
            .orderBy(col("year").asc(), col("annual_spending").desc());

        print(aggDF);
        return aggDF;
    }

    private static void print(Dataset<Row> aggDs) {
        aggDs.show();
        aggDs.printSchema();
    }

    private void exportData(Dataset<Row> dataset) {
        String connectionURL = dbProperties.getProperty("connectionURL");
        dataset.write()
            .mode(SaveMode.Overwrite)
            .jdbc(connectionURL, "customer", dbProperties);
    }
}
