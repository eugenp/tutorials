package com.baeldung.univocity;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import com.baeldung.univocity.model.Product;

public class ParsingServiceUnitTest {

    @After
    public void cleanup() {
        File csvFile = new File("src/test/resources/outputProductList.csv");
        csvFile.deleteOnExit();
        
        File textFile = new File("src/test/resources/outputProductList.txt");
        textFile.deleteOnExit();
    }
    
    @Test
    public void givenCsvFile_thenParsedResultsShouldBeReturned() {
        ParsingService parsingService = new ParsingService();
        List<String[]> productData = parsingService.parseCsvFile("src/test/resources/productList.csv");
        assertEquals(3, productData.size());
        assertEquals(3, productData.get(0).length);
        assertEquals("A8993-10", productData.get(0)[0]);
        assertEquals("Extra large widget", productData.get(0)[1]);
        assertEquals("35.42", productData.get(0)[2]);
        assertEquals("D-2938-1", productData.get(1)[0]);
        assertEquals("Winding widget \"Deluxe Model\"", productData.get(1)[1]);
        assertEquals("245.99", productData.get(1)[2]);
        assertEquals("R3212-32", productData.get(2)[0]);
        assertEquals("Standard widget", productData.get(2)[1]);
        assertEquals("2.34", productData.get(2)[2]);
    }

    @Test
    public void givenFixedWidthFile_thenParsedResultsShouldBeReturned() {
        ParsingService parsingService = new ParsingService();
        List<String[]> productData = parsingService.parseFixedWidthFile("src/test/resources/productList.txt");
        // Note: any extra spaces on the end will cause a null line to be added
        assertEquals(3, productData.size());
        assertEquals(3, productData.get(0).length);
        assertEquals("A8993-10", productData.get(0)[0]);
        assertEquals("Extra large widget", productData.get(0)[1]);
        assertEquals("35.42", productData.get(0)[2]);
        assertEquals("D-2938-1", productData.get(1)[0]);
        assertEquals("Winding widget \"Deluxe Model\"", productData.get(1)[1]);
        assertEquals("245.99", productData.get(1)[2]);
        assertEquals("R3212-32", productData.get(2)[0]);
        assertEquals("Standard widget", productData.get(2)[1]);
        assertEquals("2.34", productData.get(2)[2]);
    }

    @Test
    public void givenDataAndCsvOutputType_thenCsvFileProduced() {
        OutputService outputService = new OutputService();
        List<Object[]> productData = new ArrayList<>();
        productData.add(new Object[] { "1000-3-0", "Widget No. 96", "5.67" });
        productData.add(new Object[] { "G930-M-P", "1/4\" Wocket", ".67" });
        productData.add(new Object[] { "8080-0-M", "No. 54 Jumbo Widget", "35.74" });
        outputService.writeData(productData, OutputService.OutputType.CSV, "src/test/resources/outputProductList.csv");

        ParsingService parsingService = new ParsingService();
        List<String[]> writtenData = parsingService.parseCsvFile("src/test/resources/outputProductList.csv");
        assertEquals(3, writtenData.size());
        assertEquals(3, writtenData.get(0).length);
    }

    @Test
    public void givenDataAndFixedWidthOutputType_thenFixedWidthFileProduced() {
        OutputService outputService = new OutputService();
        List<Object[]> productData = new ArrayList<>();
        productData.add(new Object[] { "1000-3-0", "Widget No. 96", "5.67" });
        productData.add(new Object[] { "G930-M-P", "1/4\" Wocket", ".67" });
        productData.add(new Object[] { "8080-0-M", "No. 54 Jumbo Widget", "35.74" });
        outputService.writeData(productData, OutputService.OutputType.FIXED_WIDTH, "src/test/resources/outputProductList.txt");

        ParsingService parsingService = new ParsingService();
        List<String[]> writtenData = parsingService.parseFixedWidthFile("src/test/resources/outputProductList.txt");
        assertEquals(3, writtenData.size());
        assertEquals(3, writtenData.get(0).length);
    }

    @Test
    public void givenCsvFile_thenCsvFileParsedIntoBeans() {
        ParsingService parsingService = new ParsingService();
        List<Product> products = parsingService.parseCsvFileIntoBeans("src/test/resources/productListWithHeaders.csv");
        assertEquals(2, products.size());
        assertEquals("Product [Product Number: 99-378AG, Description: Wocket Widget #42, Unit Price: 3.56]", products.get(0)
            .toString());
        assertEquals("Product [Product Number: TB-333-0, Description: Turbine Widget replacement kit, Unit Price: 800.99]", products.get(1)
            .toString());
    }

    @Test
    public void givenLisOfProduct_thenWriteFixedWidthFile() {
        OutputService outputService = new OutputService();
        Product product = new Product();
        product.setProductNumber("007-PPG0");
        product.setDescription("3/8\" x 1\" Wocket");
        product.setUnitPrice(45.99f);
        List<Product> products = new ArrayList<>();
        products.add(product);
        outputService.writeBeanToFixedWidthFile(products, "src/test/resources/productListWithHeaders.txt");
        ParsingService parsingService = new ParsingService();
        List<String[]> writtenData = parsingService.parseFixedWidthFile("src/test/resources/productListWithHeaders.txt");
        assertEquals(2, writtenData.size());
        assertEquals(3, writtenData.get(0).length);
    }

    @Test
    public void givenLargeCsvFile_thenParsedDataShouldBeReturned() {
        ParsingService parsingService = new ParsingService();
        List<String[]> productData = parsingService.parseCsvFileInBatches("src/test/resources/largeProductList.csv");
        assertEquals(36, productData.size());
    }
}
