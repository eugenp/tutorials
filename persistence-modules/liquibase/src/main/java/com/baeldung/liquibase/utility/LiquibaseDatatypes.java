package com.baeldung.liquibase.utility;

import java.util.List;

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
import liquibase.datatype.core.DatabaseFunctionType;
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
import liquibase.datatype.core.UnknownType;
import liquibase.datatype.core.VarcharType;

public class LiquibaseDatatypes {
    public static void main(String[] args) {
        List<Class<? extends LiquibaseDataType>> dataTypes = getDataTypes();
        List<Class<? extends Database>> databases = getDatabases();

        for (Class<? extends LiquibaseDataType> dataTypeClass : dataTypes) {
            try {
                LiquibaseDataType dataType = dataTypeClass.getDeclaredConstructor()
                  .newInstance();
                dataType.finishInitialization("");
                System.out.println(dataType.getName());

                for (Class<? extends Database> databaseClass : databases) {
                    try {
                        Database database = databaseClass.getDeclaredConstructor()
                          .newInstance();
                        String databaseType = dataType.toDatabaseDataType(database)
                          .toString();
                        System.out.println(databaseClass.getSimpleName() + ": " + databaseType);
                    } catch (Exception e) {
                        System.err.println("Error initializing database class " + databaseClass.getSimpleName() + ": " + e.getMessage());
                    }
                }
                System.out.println();
            } catch (Exception e) {
                System.err.println("Error initializing data type class " + dataTypeClass.getSimpleName() + ": " + e.getMessage());
            }
        }
    }

    private static List<Class<? extends LiquibaseDataType>> getDataTypes() {
        return List.of(BooleanType.class, TinyIntType.class, IntType.class, MediumIntType.class, BigIntType.class, FloatType.class, DoubleType.class, DecimalType.class, NumberType.class, BlobType.class, DatabaseFunctionType.class, UnknownType.class,
          DateTimeType.class, TimeType.class, TimestampType.class, DateType.class, CharType.class, VarcharType.class, NCharType.class, NVarcharType.class, ClobType.class, CurrencyType.class, UUIDType.class);
    }

    private static List<Class<? extends Database>> getDatabases() {
        return List.of(MySQLDatabase.class, SQLiteDatabase.class, H2Database.class, PostgresDatabase.class, UnsupportedDatabase.class, DB2Database.class, MSSQLDatabase.class, OracleDatabase.class, HsqlDatabase.class, FirebirdDatabase.class,
          DerbyDatabase.class, InformixDatabase.class, SybaseDatabase.class, SybaseASADatabase.class);
    }
}

