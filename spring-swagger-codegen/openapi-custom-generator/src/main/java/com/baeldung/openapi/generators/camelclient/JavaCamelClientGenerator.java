package com.baeldung.openapi.generators.camelclient;

import org.openapitools.codegen.*;
import org.openapitools.codegen.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.models.properties.*;

import java.util.*;
import java.io.File;

import com.baeldung.openapi.generators.camelclient.helper.JavaConstantLambda;
import com.baeldung.openapi.generators.camelclient.helper.PathLambda;
import com.google.common.collect.ImmutableMap;
import com.samskivert.mustache.Mustache;

public class JavaCamelClientGenerator extends DefaultCodegen  {

    private static final Logger log = LoggerFactory.getLogger(JavaCamelClientGenerator.class);

    // source folder where to write the files
    protected String sourceFolder = "src";
    protected String apiVersion = "1.0.0";

    /**
     * Configures the type of generator.
     *
     * @return the CodegenType for this generator
     * @see     org.openapitools.codegen.CodegenType
     */
    public CodegenType getTag() {
        return CodegenType.CLIENT;
    }

    /**
     * Configures a friendly name for the generator.  This will be used by the generator
     * to select the library with the -g flag.
     *
     * @return the friendly name for the generator
     */
    public String getName() {
        return "java-camel-client";
    }

    /**
     * Provides an opportunity to inspect and modify operation data before the code is generated.
     */
    @Override
    public OperationsMap postProcessOperationsWithModels(OperationsMap objs, List<ModelMap> allModels) {

        // to try debugging your code generator:
        // set a break point on the next line.
        // then debug the JUnit test called LaunchGeneratorInDebugger

        OperationsMap results = super.postProcessOperationsWithModels(objs, allModels);

        OperationMap ops = results.getOperations();
        List<CodegenOperation> opList = ops.getOperation();

        // iterate over the operation and perhaps modify something
        for (CodegenOperation co : opList) {
            // example:
            // co.httpMethod = co.httpMethod.toLowerCase();
        }

        return results;
    }

    /**
     * Returns human-friendly help for the generator.  Provide the consumer with help
     * tips, parameters here
     *
     * @return A string value for the help message
     */
    public String getHelp() {
        return "Generates  Camel routes to invoke the API's operatations.";
    }

    public JavaCamelClientGenerator() {
        super();

        // set the output folder here
        outputFolder = "generated-code/java-camel-client";

        /**
         * Models.  You can write model files using the modelTemplateFiles map.
         * if you want to create one template for file, you can do so here.
         * for multiple files for model, just put another entry in the `modelTemplateFiles` with
         * a different extension
         */
        modelTemplateFiles.put("model.mustache", // the template to use
          ".sample");       // the extension for each file to write

        /**
         * Api classes.  You can write classes for each Api file with the apiTemplateFiles map.
         * as with models, add multiple entries with different extensions for multiple files per
         * class
         */
        apiTemplateFiles.put("camel-producer.mustache",   // the template to use
          ".java");       // the extension for each file to write

        /**
         * Template Location.  This is the location which templates will be read from.  The generator
         * will use the resource stream to attempt to read the templates.
         */
        templateDir = "java-camel-client";

        /**
         * Api Package.  Optional, if needed, this can be used in templates
         */
        apiPackage = "org.openapitools.api";

        /**
         * Model Package.  Optional, if needed, this can be used in templates
         */
        modelPackage = "org.openapitools.model";

        /**
         * Source code location, relative to the output folder
         */
        sourceFolder = "src/main/java";

        /**
         * Reserved words.  Override this with reserved words specific to your language
         */
        reservedWords = new HashSet<String>(
          //      Arrays.asList(
          //        "sample1",  // replace with static values
          //        "sample2")
        );

        /**
         * Additional Properties.  These values can be passed to the templates and
         * are available in models, apis, and supporting files
         */
        additionalProperties.put("apiVersion", apiVersion);

        /**
         * Supporting Files.  You can write single files for the generator with the
         * entire object tree available.  If the input file has a suffix of `.mustache
         * it will be processed by the template engine.  Otherwise, it will be copied
         */
        //supportingFiles.add(new SupportingFile("myFile.mustache",   // the input template or file
        //  "",                                                       // the destination folder, relative `outputFolder`
        //  "myFile.sample")                                          // the output file
        //);

        /**
         * Language Specific Primitives.  These types will not trigger imports by
         * the client generator
         */
        languageSpecificPrimitives = new HashSet<String>(
          //      Arrays.asList(
          //        "Type1",      // replace these with your types
          //        "Type2")
        );

        // Config options. Whenever possible, we should reuse one of the 'well-know' options
        cliOptions.add(new CliOption(CodegenConstants.MODEL_PACKAGE, CodegenConstants.MODEL_PACKAGE_DESC).defaultValue(modelPackage));
        cliOptions.add(new CliOption(CodegenConstants.API_PACKAGE, CodegenConstants.API_PACKAGE_DESC).defaultValue(apiPackage));
        cliOptions.add(new CliOption(CodegenConstants.SOURCE_FOLDER, CodegenConstants.SOURCE_FOLDER_DESC).defaultValue(sourceFolder));

    }

    @Override
    public void processOpts() {
        super.processOpts();

        // The base clasee defines sourceFolder, but doesn't expose it as a configOption
        if (additionalProperties.containsKey(CodegenConstants.SOURCE_FOLDER)) {
            sourceFolder = ((String) additionalProperties.get(CodegenConstants.SOURCE_FOLDER));
        }
        additionalProperties.put(CodegenConstants.SOURCE_FOLDER, sourceFolder);

    }

    /**
     * Escapes a reserved word as defined in the `reservedWords` array. Handle escaping
     * those terms here.  This logic is only called if a variable matches the reserved words
     *
     * @return the escaped term
     */
    @Override
    public String escapeReservedWord(String name) {
        return "_" + name;  // add an underscore to the name
    }

    /**
     * Location to write model files.  You can use the modelPackage() as defined when the class is
     * instantiated
     */
    public String modelFileFolder() {
        return outputFolder + "/" + sourceFolder + "/" + modelPackage().replace('.', File.separatorChar);
    }

    /**
     * Location to write api files.  You can use the apiPackage() as defined when the class is
     * instantiated
     */
    @Override
    public String apiFileFolder() {
        return outputFolder + "/" + sourceFolder + "/" + apiPackage().replace('.', File.separatorChar);
    }

    /**
     * override with any special text escaping logic to handle unsafe
     * characters so as to avoid code injection
     *
     * @param input String to be cleaned up
     * @return string with unsafe characters removed or escaped
     */
    @Override
    public String escapeUnsafeCharacters(String input) {
        //TODO: check that this logic is safe to escape unsafe characters to avoid code injection
        return input;
    }

    /**
     * Escape single and/or double quote to avoid code injection
     *
     * @param input String to be cleaned up
     * @return string with quotation mark removed or escaped
     */
    public String escapeQuotationMark(String input) {
        //TODO: check that this logic is safe to escape quotation mark to avoid code injection
        return input.replace("\"", "\\\"");
    }

    /**
     * Use this callback to extend the standard set of lambdas available to templates.
     * @return
     */
    @Override
    protected ImmutableMap.Builder<String, Mustache.Lambda> addMustacheLambdas() {
        // Start with parent lambdas
        ImmutableMap.Builder<String, Mustache.Lambda> builder = super.addMustacheLambdas();

        // Add custom lambda to convert operationIds in suitable java constants
        return builder
          .put("javaconstant", new JavaConstantLambda())
          .put("path", new PathLambda());
    }
}
