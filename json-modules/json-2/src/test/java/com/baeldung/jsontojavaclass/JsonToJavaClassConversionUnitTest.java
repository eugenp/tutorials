package com.baeldung.jsontojavaclass;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;

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
        String outputPath = "src/test/resources/";
        File outputJavaClassDirectory = new File(outputPath);

        String javaClassName = "SamplePojo";

        jsonToJavaConversion.convertJsonToJavaClass(inputJson.toURI()
                .toURL(), outputJavaClassDirectory, packageName, javaClassName);

        File outputJavaClassPath = new File(outputPath + packageName.replace(".", "/"));
        Assertions.assertTrue(Arrays.stream(outputJavaClassPath.listFiles()).peek(System.out::println).anyMatch(file -> (javaClassName+".java").equalsIgnoreCase(file.getName())));

    }

}
