//package com.baeldung.apache.avro;
//
//import org.apache.avro.Schema;
//import org.apache.avro.reflect.ReflectData;
//
//public class ConverterApp {
//
//
//    public class BankAccount {
//        private String bankAccountNumber;
//    }
//
//    public static void main(String[] args) {
//        ReflectData reflectData = ReflectData.get();
//        Schema bankAccountSchema = reflectData.getSchema(BankAccount.class);
//
//        String bankAccountSchemaJson = bankAccountSchema.toString(true);
//        System.out.println(bankAccountSchemaJson);
//    }
//
//}
