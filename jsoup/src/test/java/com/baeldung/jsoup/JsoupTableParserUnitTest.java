package com.baeldung.jsoup;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.junit.Test;

public class JsoupTableParserUnitTest {

    @Test
    public void whenDocumentTableParsed_thenTableDataReturned() {
        JsoupTableParser jsoParser = new JsoupTableParser();
        Document doc = jsoParser.loadFromFile("Students.html");
        List<Map<String, String>> tableData = jsoParser.parseTable(doc, 0);
        assertEquals("90", tableData.get(0).get("Maths")); 
    }

    @Test
    public void whenTableUpdated_thenUpdatedDataReturned() {
        JsoupTableParser jsoParser = new JsoupTableParser();
        Document doc = jsoParser.loadFromFile("Students.html");
        jsoParser.updateTableData(doc, 0, "50");
        List<Map<String, String>> tableData = jsoParser.parseTable(doc, 0);
        assertEquals("50", tableData.get(2)
            .get("Maths"));
    }

    @Test
    public void whenTableRowAdded_thenRowCountIncreased() {
        JsoupTableParser jsoParser = new JsoupTableParser();
        Document doc = jsoParser.loadFromFile("Students.html");
        List<Map<String, String>> tableData = jsoParser.parseTable(doc, 0);
        int countBeforeAdd = tableData.size();
        jsoParser.addRowToTable(doc, 0);
        tableData = jsoParser.parseTable(doc, 0);
        assertEquals(countBeforeAdd + 1, tableData.size());
    }

    @Test
    public void whenTableRowDeleted_thenRowCountDecreased() {
        JsoupTableParser jsoParser = new JsoupTableParser();
        Document doc = jsoParser.loadFromFile("Students.html");
        List<Map<String, String>> tableData = jsoParser.parseTable(doc, 0);
        int countBeforeDel = tableData.size();
        jsoParser.deleteRowFromTable(doc, 0, 2);
        tableData = jsoParser.parseTable(doc, 0);
        assertEquals(countBeforeDel - 1, tableData.size());
    }
}
