package com.baeldung.openapi.generators.camelclient;

import org.openapitools.codegen.*;
import org.openapitools.codegen.meta.GeneratorMetadata;
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

public class JavaCamelClientGenerator extends DefaultCodegen {

    private static final Logger log = LoggerFactory.getLogger(JavaCamelClientGenerator.class);

    // source folder where to write the files, relative to the output folder
    protected String sourceFolder;
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
     * Returns human-friendly help for the generator.  Provide the consumer with help
     * tips, parameters here
     *
     * @return A string value for the help message
     */
    public String getHelp() {
        return "Generates  Camel routes to invoke the API's operatations.";
    }

    /**
     * Returns metadata about the generator.
     *
     * @return A provided {@link GeneratorMetadata} instance
     */
    @Override
    public GeneratorMetadata getGeneratorMetadata() {
        return super.getGeneratorMetadata();
    }

    public JavaCamelClientGenerator() {
        super();

        // default output folder
        setOutputDir("generated-code/java-camel-client");

        /**
         * Api classes.  You can write classes for each Api file with the apiTemplateFiles map.
         * as with models, add multiple entries with different extensions for multiple files per
         * class.
         */
        apiTemplateFiles().put("camel-producer.mustache",   // the template to use
          ".java");       // the extension for each file to write

        /**
         * Template Location.  This is the location which templates will be read from.  The generator
         * will use the resource stream to attempt to read the templates.
         */
        setTemplateDir("java-camel-client");

        // default Api Package.  Optional, if needed, this can be used in templates
        setApiPackage("org.openapitools.api");

        /**
         * Model Package.  Optional, if needed, this can be used in templates
         */
        setModelPackage("org.openapitools.model");

        //default source code location, relative to the output folder
        sourceFolder = "src/main/java";

        /**
         * Reserved words.  Override this with reserved words specific to your language
         */
        reservedWords().addAll(JAVA_RESERVED_WORDS);

        /**
         * Additional Properties.  These values can be passed to the templates and
         * are available in models, apis, and supporting files
         */
        additionalProperties().put("apiVersion", apiVersion);

        // Config options. Whenever possible, we should reuse one of the 'well-know' options
        cliOptions().add(new CliOption(CodegenConstants.MODEL_PACKAGE, CodegenConstants.MODEL_PACKAGE_DESC).defaultValue(modelPackage));
        cliOptions().add(new CliOption(CodegenConstants.API_PACKAGE, CodegenConstants.API_PACKAGE_DESC).defaultValue(apiPackage));
        cliOptions().add(new CliOption(CodegenConstants.SOURCE_FOLDER, CodegenConstants.SOURCE_FOLDER_DESC).defaultValue(sourceFolder));

    }

    @Override
    public void processOpts() {
        super.processOpts();

        // Handle sourceFolder config option
        if (additionalProperties().containsKey(CodegenConstants.SOURCE_FOLDER)) {
            sourceFolder = ((String) additionalProperties().get(CodegenConstants.SOURCE_FOLDER));
        }
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
        return outputFolder() + File.separator + sourceFolder + File.separator + modelPackage().replace('.', File.separatorChar);
    }

    /**
     * Location to write api files.  You can use the apiPackage() as defined when the class is
     * instantiated
     */
    @Override
    public String apiFileFolder() {
        return outputFolder() + File.separator + sourceFolder + File.separator + apiPackage().replace('.', File.separatorChar);
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
        return builder.put("javaconstant", new JavaConstantLambda())
          .put("path", new PathLambda());
    }

    static final List<String> JAVA_RESERVED_WORDS = Arrays.asList("abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "continue", "const", "default", "do", "double", "else", "enum", "exports", "extends", "final", "finally",
      "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "module", "native", "new", "package", "private", "protected", "public", "requires", "return", "short", "static", "strictfp", "super", "switch",
      "synchronized", "this", "throw", "throws", "transient", "try", "var", "void", "volatile", "while");
}
