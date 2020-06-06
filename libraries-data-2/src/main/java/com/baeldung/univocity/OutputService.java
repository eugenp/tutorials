package com.baeldung.univocity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.univocity.model.Product;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import com.univocity.parsers.fixed.FixedWidthFields;
import com.univocity.parsers.fixed.FixedWidthWriter;
import com.univocity.parsers.fixed.FixedWidthWriterSettings;
import com.univocity.parsers.tsv.TsvWriter;
import com.univocity.parsers.tsv.TsvWriterSettings;

public class OutputService {
    private Logger logger = LoggerFactory.getLogger(ParsingService.class);

    public enum OutputType {
        CSV, TSV, FIXED_WIDTH
    };

    public boolean writeData(List<Object[]> products, OutputType outputType, String outputPath) {
        try (Writer outputWriter = new OutputStreamWriter(new FileOutputStream(new File(outputPath)), "UTF-8")) {
            switch (outputType) {
            case CSV: {
                CsvWriter writer = new CsvWriter(outputWriter, new CsvWriterSettings());
                writer.writeRowsAndClose(products);
            }
                break;
            case TSV: {
                TsvWriter writer = new TsvWriter(outputWriter, new TsvWriterSettings());
                writer.writeRowsAndClose(products);
            }
                break;
            case FIXED_WIDTH: {
                FixedWidthFields fieldLengths = new FixedWidthFields(8, 30, 10);
                FixedWidthWriterSettings settings = new FixedWidthWriterSettings(fieldLengths);
                FixedWidthWriter writer = new FixedWidthWriter(outputWriter, settings);
                writer.writeRowsAndClose(products);
            }
                break;
            default:
                logger.warn("Invalid OutputType: " + outputType);
                return false;
            }
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean writeBeanToFixedWidthFile(List<Product> products, String outputPath) {
        try (Writer outputWriter = new OutputStreamWriter(new FileOutputStream(new File(outputPath)), "UTF-8")) {
            BeanWriterProcessor<Product> rowProcessor = new BeanWriterProcessor<Product>(Product.class);
            FixedWidthFields fieldLengths = new FixedWidthFields(8, 30, 10);
            FixedWidthWriterSettings settings = new FixedWidthWriterSettings(fieldLengths);
            settings.setHeaders("product_no", "description", "unit_price");
            settings.setRowWriterProcessor(rowProcessor);
            FixedWidthWriter writer = new FixedWidthWriter(outputWriter, settings);
            writer.writeHeaders();
            for (Product product : products) {
                writer.processRecord(product);
            }
            writer.close();
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
