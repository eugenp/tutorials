package com.baeldung;

import io.questdb.client.Sender;


public class InsertData {
    static final String SENSORS_TABLE_NAME = "sensors";

    public static void main(String[] args) {
        try (Sender sender = Sender.builder().address("localhost:9009").build()) {
            sender.table(SENSORS_TABLE_NAME)
                    .stringColumn("id", "KTC")
                    .stringColumn("name", "Kitchen temperature (Celsius)")
                    .doubleColumn("currentValue", 20)
                    .atNow();
            sender.table(SENSORS_TABLE_NAME)
                    .stringColumn("id", "SMT")
                    .stringColumn("name", "Smart Garden temperature (Celsius)")
                    .doubleColumn("currentValue", 28.5)
                    .atNow();
            sender.table(SENSORS_TABLE_NAME)
                    .stringColumn("id", "RM1")
                    .stringColumn("name", "Room 1")
                    .doubleColumn("currentValue", 19.5)
                    .doubleColumn("idealValue", 18.5)
                    .atNow();
        }
    }
}