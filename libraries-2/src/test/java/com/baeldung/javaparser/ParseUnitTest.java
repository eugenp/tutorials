package com.baeldung.javaparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.Statement;

public class ParseUnitTest {
    @Test
    void parseEmptyClass() {
        CompilationUnit parsed = StaticJavaParser.parse("class TestClass {}");
        System.out.println(parsed);
    }

    @Test
    void parseMalformedClass() {
        ParseProblemException parseProblemException = assertThrows(ParseProblemException.class,
                () -> StaticJavaParser.parse("class TestClass"));

        assertEquals(1, parseProblemException.getProblems().size());
        assertEquals("Parse error. Found <EOF>, expected one of  \"<\" \"extends\" \"implements\" \"permits\" \"{\"",
                parseProblemException.getProblems().get(0).getMessage());
    }

    @Test
    void parseVariable() {
        Statement parsed = StaticJavaParser.parseStatement("final int answer = 42;");
        System.out.println(parsed);
    }

    @Test
    void parseAnnotation() {
        AnnotationExpr parsed = StaticJavaParser.parseAnnotation("@Override");
        System.out.println(parsed);
    }


    @Test
    void parseBlock() {
        Statement parsed = StaticJavaParser.parseBlock("{ final int answer = 42; answer = 43; }");
        System.out.println(parsed);
    }
}
