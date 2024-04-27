package com.baeldung.openapi.generators.camelclient;

import java.io.File;
import java.net.URL;

import org.junit.Test;
import org.openapitools.codegen.ClientOptInput;
import org.openapitools.codegen.DefaultGenerator;
import org.openapitools.codegen.config.CodegenConfigurator;

/***
 * This test allows you to easily launch your code generation software under a debugger.
 * Then run this test under debug mode.  You will be able to step through your java code
 * and then see the results in the out directory.
 *
 * To experiment with debugging your code generator:
 * 1) Set a break point in JavaCamelClientGenerator.java in the postProcessOperationsWithModels() method.
 * 2) To launch this test in Eclipse: right-click | Debug As | JUnit Test
 *
 */
public class JavaCamelClientGeneratorUnitTest {

  @Test
  public void whenLaunchCodeGenerator_thenSuccess() throws Exception {

    // to understand how the 'openapi-generator-cli' module is using 'CodegenConfigurator', have a look at the 'Generate' class:
    // https://github.com/OpenAPITools/openapi-generator/blob/master/modules/openapi-generator-cli/src/main/java/org/openapitools/codegen/cmd/Generate.java


    final CodegenConfigurator configurator = new CodegenConfigurator()
      .setGeneratorName("java-camel-client") // use this codegen library
      .setInputSpec("petstore.yaml") // NOTICE: This will look both for a file or a classpath resource
      .setOutputDir("target/out/java-camel-client"); // output directory

    final ClientOptInput clientOptInput = configurator.toClientOptInput();
    DefaultGenerator generator = new DefaultGenerator();
    generator.opts(clientOptInput).generate();
  }
}