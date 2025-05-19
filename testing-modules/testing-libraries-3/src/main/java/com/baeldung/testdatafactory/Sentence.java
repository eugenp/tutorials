package com.baeldung.testdatafactory;

import lombok.Data;

import java.util.List;

@Data
public class Sentence {
    private List<String> tokens;
}
