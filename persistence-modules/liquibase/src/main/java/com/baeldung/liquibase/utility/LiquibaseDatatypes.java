package com.baeldung.liquibase.utility;

import java.util.List;

import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.DerbyDatabase;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.H2Database;
import liquibase.database.core.HsqlDatabase;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SQLiteDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.database.core.SybaseDatabase;
import liquibase.database.core.UnsupportedDatabase;
import liquibase.datatype.LiquibaseDataType;
import liquibase.datatype.core.BigIntType;
import liquibase.datatype.core.BlobType;
import liquibase.datatype.core.BooleanType;
import liquibase.datatype.core.CharType;
import liquibase.datatype.core.ClobType;
import liquibase.datatype.core.CurrencyType;
import liquibase.datatype.core.DateTimeType;
import liquibase.datatype.core.DateType;
import liquibase.datatype.core.DecimalType;
import liquibase.datatype.core.DoubleType;
import liquibase.datatype.core.FloatType;
import liquibase.datatype.core.IntType;
import liquibase.datatype.core.MediumIntType;
import liquibase.datatype.core.NCharType;
import liquibase.datatype.core.NVarcharType;
import liquibase.datatype.core.NumberType;
import liquibase.datatype.core.TimeType;
import liquibase.datatype.core.TimestampType;
import liquibase.datatype.core.TinyIntType;
import liquibase.datatype.core.UUIDType;
import liquibase.datatype.core.VarcharType;

public class LiquibaseDatatypes {
    public static void main(String[] args) {
        List<LiquibaseDataType> dataTypes = getDataTypes();
        List<AbstractJdbcDatabase> databases = getDatabases();

        for (LiquibaseDataType dataTypeInstance : dataTypes) {
            try {
                LiquibaseDataType dataType = dataTypeInstance;
                dataType.finishInitialization("");
                System.out.println(dataType.getName());

                for (AbstractJdbcDatabase databaseInstance : databases) {
                    try {
                        Database database = databaseInstance;
                        String databaseType = dataType.toDatabaseDataType(database)
                          .toString();
                        System.out.println(databaseInstance.getName() + ": " + databaseType);
                    } catch (Exception e) {
                        System.err.println("Error initializing database class " + databaseInstance.getName() + ": " + e.getMessage());
                    }
                }
                System.out.println();
            } catch (Exception e) {
                System.err.println("Error initializing data type class " + dataTypeInstance.getName() + ": " + e.getMessage());
            }
        }
    }

    private static List<LiquibaseDataType> getDataTypes() {
        return List.of(
          new BooleanType(),
          new TinyIntType(),
          new IntType(),
          new MediumIntType(),
          new BigIntType(),
          new FloatType(),
          new DoubleType(),
          new DecimalType(),
          new NumberType(),
          new BlobType(),
          new DateTimeType(),
          new TimeType(),
          new TimestampType(),
          new DateType(),
          new CharType(),
          new VarcharType(),
          new NCharType(),
          new NVarcharType(),
          new ClobType(),
          new CurrencyType(),
          new UUIDType());
    }

    private static List<AbstractJdbcDatabase> getDatabases() {
        return List.of(
          new MySQLDatabase(),
          new SQLiteDatabase(),
          new H2Database(),
          new PostgresDatabase(),
          new DB2Database(),
          new MSSQLDatabase(),
          new OracleDatabase(),
          new HsqlDatabase(),
          new FirebirdDatabase(),
          new DerbyDatabase(),
          new InformixDatabase(),
          new SybaseDatabase(),
          new SybaseASADatabase());
    }
}

