package com.baeldung.javaparser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ManipulateUnitTest {
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
    void capitalizeMethods() {
        CompilationUnit compilationUnit = StaticJavaParser.parse(code);

        compilationUnit.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodDeclaration n, Object arg) {
                super.visit(n, arg);
                
                n.setName(n.getName().asString().toUpperCase());
            }
        }, null);

        String formatted = compilationUnit.toString();
        assertEquals("package com.baeldung.javaparser;\n" +
                "\n" +
                "import java.util.List;\n" +
                "\n" +
                "class TestClass {\n" +
                "\n" +
                "    private List<String> DOSOMETHING() {\n" +
                "    }\n" +
                "\n" +
                "    private class Inner {\n" +
                "\n" +
                "        private String OTHER() {\n" +
                "        }\n" +
                "    }\n" +
                "}\n", formatted);
    }
}
