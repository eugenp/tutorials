package com.baeldung.jsoup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupTableParser {

    public Document loadFromString(String html) {
        return Jsoup.parse(html);
    }

    public Document loadFromURL(String url) throws IOException {
        Document doc = Jsoup.connect(url)
            .get();
        return doc;
    }

    public Document loadFromFile(String filePath) {
        Document doc = null;
        try {
            File input = new File(JsoupTableParser.class.getClassLoader()
                .getResource(filePath)
                .getFile());
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public List<Map<String, String>> parseTable(Document doc, int tableOrder) {
        Element table = doc.select("table")
            .get(tableOrder);
        Element tbody = table.select("tbody")
            .get(0);
        Elements dataRows = tbody.select("tr");
        Elements headerRow = table.select("tr")
            .get(0)
            .select("th,td");

        List<String> headers = new ArrayList<String>();
        for (Element header : headerRow) {
            headers.add(header.text());
        }

        List<Map<String, String>> parsedDataRows = new ArrayList<Map<String, String>>();
        for (int row = 0; row < dataRows.size(); row++) {
            Elements colVals = dataRows.get(row)
                .select("th,td");

            int colCount = 0;
            Map<String, String> dataRow = new HashMap<String, String>();
            for (Element colVal : colVals) {
                dataRow.put(headers.get(colCount++), colVal.text());
            }
            parsedDataRows.add(dataRow);
        }
        return parsedDataRows;
    }

    public void updateTableData(Document doc, int tableOrder, String updateValue) {
        Element table = doc.select("table")
            .get(tableOrder);
        Element tbody = table.select("tbody")
            .get(0);
        Elements dataRows = tbody.select("tr");

        for (int row = 0; row < dataRows.size(); row++) {
            Elements colVals = dataRows.get(row)
                .select("th,td");

            for (int colCount = 0; colCount < colVals.size(); colCount++) {
                colVals.get(colCount)
                    .text(updateValue);
            }
        }
    }

    public void addRowToTable(Document doc, int tableOrder) {
        Element table = doc.select("table")
            .get(tableOrder);
        Element tbody = table.select("tbody")
            .get(0);

        Elements rows = table.select("tr");
        Elements headerCols = rows.get(0)
            .select("th,td");
        int numCols = headerCols.size();

        Elements colVals = new Elements(numCols);
        for (int colCount = 0; colCount < numCols; colCount++) {
            Element colVal = new Element("td");
            colVal.text("11");
            colVals.add(colVal);
        }
        Elements dataRows = tbody.select("tr");
        Element newDataRow = new Element("tr");
        newDataRow.appendChildren(colVals);
        dataRows.add(newDataRow);
        tbody.html(dataRows.toString());
    }

    public void deleteRowFromTable(Document doc, int tableOrder, int rowNumber) {
        Element table = doc.select("table")
            .get(tableOrder);
        Element tbody = table.select("tbody")
            .get(0);
        Elements dataRows = tbody.select("tr");
        if (rowNumber < dataRows.size()) {
            dataRows.remove(rowNumber);
        }
    }
}
