package com.baeldung.selenium.generatepdf;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Pdf;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.print.PageMargin;
import org.openqa.selenium.print.PageSize;
import org.openqa.selenium.print.PrintOptions;

public class GeneratePDFsUnitTests {

    @Test
    public void whenNavigatingToBaeldung_thenPDFIsGenerated() throws IOException {
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://www.baeldung.com/library/java-web-weekly");
        Pdf pdf = driver.print(new PrintOptions());
        byte[] pdfContent = Base64.getDecoder()
            .decode(pdf.getContent());
        Files.write(Paths.get("./Baeldung_Weekly.pdf"), pdfContent);
        assertTrue(Files.exists(Paths.get("./Baeldung_Weekly.pdf")), "PDF file should be created");
        driver.quit();
    }

    @Test
    public void whenCustomizingPDFOutput_thenAdvancedCustomPDFIsGenerated() throws IOException {
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://www.baeldung.com/library/java-web-weekly");

        PrintOptions options = new PrintOptions();

        options.setOrientation(PrintOptions.Orientation.LANDSCAPE);
        options.setScale(1.5);
        options.setPageSize(new PageSize(100, 100));
        options.setPageMargin(new PageMargin(2, 2, 2, 2));

        Pdf pdf = driver.print(options);

        byte[] pdfContent = Base64.getDecoder().decode(pdf.getContent());
        Files.write(Paths.get("./Custom_Baeldung_Weekly.pdf"), pdfContent);
        assertTrue(Files.exists(Paths.get("./Custom_Baeldung_Weekly.pdf")), "Custom PDF file should be created");
        driver.quit();
    }

    @Test
    public void whenRunningInHeadlessMode_thenPDFIsGenerated() throws IOException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        ChromeDriver driver = new ChromeDriver(options);
        driver.get("https://www.baeldung.com/library/java-web-weekly");
        Pdf pdf = driver.print(new PrintOptions());
        byte[] pdfContent = Base64.getDecoder()
            .decode(pdf.getContent());
        Files.write(Paths.get("./Headless_Baeldung_Weekly.pdf"), pdfContent);
        assertTrue(Files.exists(Paths.get("./Headless_Baeldung_Weekly.pdf")), "Headless PDF file should be created");
        driver.quit();
    }

    @Test
    public void whenNavigatingToBaeldungWithFirefox_thenPDFIsGenerated() throws IOException {
        FirefoxDriver driver = new FirefoxDriver(new FirefoxOptions());
        driver.get("https://www.baeldung.com/library/java-web-weekly");
        Pdf pdf = driver.print(new PrintOptions());
        byte[] pdfContent = Base64.getDecoder()
            .decode(pdf.getContent());
        Files.write(Paths.get("./Firefox_Weekly.pdf"), pdfContent);
        assertTrue(Files.exists(Paths.get("./Firefox_Weekly.pdf")), "PDF file should be created");
        driver.quit();
    }


}
