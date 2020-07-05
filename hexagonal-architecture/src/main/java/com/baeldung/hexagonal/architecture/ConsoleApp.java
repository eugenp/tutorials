package com.baeldung.hexagonal.architecture;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;

import com.baeldung.hexagonal.architecture.model.Product;
import com.baeldung.hexagonal.architecture.service.ProductService;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * @author AshwiniKeshri
 *
 */

@SpringBootApplication(exclude = { EmbeddedServletContainerAutoConfiguration.class, WebMvcAutoConfiguration.class })
public class ConsoleApp implements CommandLineRunner {
    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String filePath = "";
        if (args != null && args.length == 2 && "Product".equalsIgnoreCase(args[0]) && (filePath = args[1]).length() > 0) {
            File sourceFile = new File(filePath);
            if (sourceFile.exists()) {
                CsvMapper mapper = new CsvMapper();
                List<Product> products = mapper.readerFor(Product.class)
                    .with(CsvSchema.emptySchema()
                        .withHeader())
                    .<Product> readValues(sourceFile)
                    .readAll();
                productService.saveAll(products);
            }

        }

    }
}
