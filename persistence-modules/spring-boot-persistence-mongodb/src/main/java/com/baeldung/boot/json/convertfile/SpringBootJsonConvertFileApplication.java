package com.baeldung.boot.json.convertfile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.baeldung.boot.json.convertfile.service.ImportJsonService;

@SpringBootApplication
public class SpringBootJsonConvertFileApplication implements ApplicationRunner {
    private Logger log = LogManager.getLogger(this.getClass());
    private static final String RESOURCE_PREFIX = "classpath:";

    @Autowired
    private ImportJsonService importService;

    public static void main(String ... args) {
        SpringApplication.run(SpringBootJsonConvertFileApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption("import")) {
            if (!args.containsOption("collection"))
                throw new IllegalArgumentException("required option: --collection with collection name when using --import");

            String collection = args.getOptionValues("collection")
                .get(0);

            List<String> sources = args.getOptionValues("import");
            for (String source : sources) {
                List<String> jsonLines = new ArrayList<>();
                if (source.startsWith(RESOURCE_PREFIX)) {
                    String resource = source.substring(RESOURCE_PREFIX.length());
                    jsonLines = ImportUtils.linesFromResource(resource);
                } else {
                    jsonLines = ImportUtils.lines(new File(source));
                }

                if (jsonLines == null || jsonLines.isEmpty()) {
                    log.warn(source + " - no input to import");
                } else {
                    // ImportJsonService importService = context.getBean(ImportJsonService.class);
                    String result = importService.importTo(collection, jsonLines);
                    log.info(source + " - import result: " + result);
                }
            }
        }
    }
}
