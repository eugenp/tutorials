package com.baeldung.dataframes;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class SchemaFactory {

    public static StructType customerSchema() {
        return DataTypes.createStructType(
            new StructField[] { DataTypes.createStructField("id", DataTypes.IntegerType, false),
                DataTypes.createStructField("zoneId", DataTypes.StringType, false),
                DataTypes.createStructField("FIRST_NAME", DataTypes.StringType, false),
                DataTypes.createStructField("MIDDLE_NAME", DataTypes.StringType, false),
                DataTypes.createStructField("LAST_NAME", DataTypes.StringType, false),
                DataTypes.createStructField("CITY", DataTypes.StringType, false),
                DataTypes.createStructField("gender", DataTypes.StringType, false),
                DataTypes.createStructField("transaction_date", DataTypes.DateType, false),
                DataTypes.createStructField("transaction_amount", DataTypes.IntegerType, false)
            });
    }

    public static StructType minimumCustomerDataSchema() {
        return DataTypes.createStructType(new StructField[] {
            DataTypes.createStructField("id", DataTypes.StringType, true),
            DataTypes.createStructField("name", DataTypes.StringType, true),
            DataTypes.createStructField("gender", DataTypes.StringType, true),
            DataTypes.createStructField("transaction_amount", DataTypes.IntegerType, true)
        });
    }
}
