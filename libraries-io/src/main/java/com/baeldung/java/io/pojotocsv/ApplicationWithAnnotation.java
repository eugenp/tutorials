package com.baeldung.java.io.pojotocsv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public record ApplicationWithAnnotation(@CsvBindByName(column = "id", required = true) @CsvBindByPosition(position = 1) String id, @CsvBindByName(column = "name", required = true) @CsvBindByPosition(position = 0) String name,
                                        @CsvBindByName(column = "age", required = true) @CsvBindByPosition(position = 2) Integer age, @CsvBindByName(column = "position", required = true) @CsvBindByPosition(position = 3) String created_at) {
}