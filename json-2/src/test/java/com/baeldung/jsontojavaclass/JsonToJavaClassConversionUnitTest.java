package com.baeldung.jsontojavaclass;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JsonToJavaClassConversionUnitTest {

    private JsonToJavaClassConversion jsonToJavaConversion = new JsonToJavaClassConversion();

    @Test
    void whenProvideInputJSON_thenGenerateJavaClass() throws MalformedURLException, IOException {

        String packageName = "com.baeldung.jsontojavaclass.pojo";

        // load input JSON file
        String jsonPath = "src/test/resources/";
        File inputJson = new File(jsonPath + "sample_input.json");

        // create the local directory for generating the Java Class file
        String outputPath = "src/main/java/";
        File outputJavaClassDirectory = new File(outputPath);
        outputJavaClassDirectory.mkdirs();

        String className = "SamplePojo";

        Object object = jsonToJavaConversion.convertJsonToJavaClass(inputJson.toURI()
            .toURL(), outputJavaClassDirectory, packageName, className);
        System.out.println(object);

        Assertions.assertNotNull(object);

    }

}
