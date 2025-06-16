package com.baeldung.javaparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class AnalyzeUnitTest {
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
    void getPackageName() {
        CompilationUnit compilationUnit = StaticJavaParser.parse(code);

        assertTrue(compilationUnit.getPackageDeclaration().isPresent());

        PackageDeclaration packageDeclaration = compilationUnit.getPackageDeclaration().get();
        assertEquals("com.baeldung.javaparser", packageDeclaration.getName().asString());
    }

    @Test
    void getKnownClass() {
        CompilationUnit compilationUnit = StaticJavaParser.parse(code);

        assertTrue(compilationUnit.getClassByName("TestClass").isPresent());

        ClassOrInterfaceDeclaration testClass = compilationUnit.getClassByName("TestClass").get();
        assertEquals("TestClass", testClass.getName().asString());
    }

    @Test
    void getImports() {
        CompilationUnit compilationUnit = StaticJavaParser.parse(code);

        NodeList<ImportDeclaration> imports = compilationUnit.getImports();

        assertEquals(1, imports.size());
        assertEquals("java.util.List", imports.get(0).getName().asString());
    }

    @Test
    void visitLoggingMethods() {
        CompilationUnit compilationUnit = StaticJavaParser.parse(code);

        compilationUnit.accept(new VoidVisitorAdapter<Object>() {
            @Override
            public void visit(MethodDeclaration n, Object arg) {
                super.visit(n, arg);

                System.out.println("Method: " + n.getName());
            }
        }, null);
    }

    @Test
    void visitListMethodNames() {
        CompilationUnit compilationUnit = StaticJavaParser.parse(code);

        List<String> allMethods = compilationUnit.accept(new GenericListVisitorAdapter<String, Object>() {
            @Override
            public List<String> visit(MethodDeclaration n, Object arg) {
                List<String> result = super.visit(n, arg);
                result.add(n.getName().asString());
                return result;
            }
        }, null);

        assertEquals(2, allMethods.size());
        assertEquals("doSomething", allMethods.get(0));
        assertEquals("other", allMethods.get(1));
    }
}
