package com.baeldung.testdatafactory;

import lombok.Data;

import java.util.List;

@Data
public class Paragraph {
    public enum Style { NORMAL, HEADING };

    private List<Sentence> sentences;
    private Style style = Style.NORMAL;
}
