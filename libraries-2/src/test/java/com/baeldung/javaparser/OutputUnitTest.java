package com.baeldung.javaparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.DefaultPrettyPrinterVisitor;
import com.github.javaparser.printer.configuration.DefaultConfigurationOption;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import com.github.javaparser.printer.configuration.Indentation;

public class OutputUnitTest {
    private final String code = String.join("\n", Arrays.asList(
            "package com.baeldung.javaparser;",
            "import java.util.List;",
            "class TestClass {",
            "private List<String> doSomething()  {}",
            "private class Inner {",
            "private String other() {}",
            "}",
            "}"
    ));

    @Test
    void withToString() {
        CompilationUnit compilationUnit = StaticJavaParser.parse(code);

        String formatted = compilationUnit.toString();
        assertEquals("package com.baeldung.javaparser;\n" +
                "\n" +
                "import java.util.List;\n" +
                "\n" +
                "class TestClass {\n" +
                "\n" +
                "    private List<String> doSomething() {\n" +
                "    }\n" +
                "\n" +
                "    private class Inner {\n" +
                "\n" +
                "        private String other() {\n" +
                "        }\n" +
                "    }\n" +
                "}\n", formatted);
    }

    @Test
    void withVisitor() {
        CompilationUnit compilationUnit = StaticJavaParser.parse(code);

        DefaultPrinterConfiguration printerConfiguration = new DefaultPrinterConfiguration();
        printerConfiguration.addOption(new DefaultConfigurationOption(DefaultPrinterConfiguration.ConfigOption.INDENTATION,
                new Indentation(Indentation.IndentType.SPACES, 2)));
        DefaultPrettyPrinterVisitor visitor = new DefaultPrettyPrinterVisitor(printerConfiguration);

        compilationUnit.accept(visitor, null);
        String formatted = visitor.toString();

        assertEquals("package com.baeldung.javaparser;\n" +
                "\n" +
                "import java.util.List;\n" +
                "\n" +
                "class TestClass {\n" +
                "\n" +
                "  private List<String> doSomething() {\n" +
                "  }\n" +
                "\n" +
                "  private class Inner {\n" +
                "\n" +
                "    private String other() {\n" +
                "    }\n" +
                "  }\n" +
                "}\n", formatted);
    }
}
