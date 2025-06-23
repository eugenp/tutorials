package com.baeldung.testdatafactory;

import lombok.Data;

import java.util.List;

@Data
public class Document {
    private List<Paragraph> paragraphs;
}
