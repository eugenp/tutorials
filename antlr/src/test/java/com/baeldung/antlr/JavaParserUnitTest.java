package com.baeldung.antlr;

import com.baeldung.antlr.java.UppercaseMethodListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JavaParserUnitTest {

    @Test
    public void whenOneMethodStartsWithUpperCase_thenOneErrorReturned() throws Exception{

        String javaClassContent = "public class SampleClass { void DoSomething(){} }";
        Java8Lexer java8Lexer = new Java8Lexer(CharStreams.fromString(javaClassContent));
        CommonTokenStream tokens = new CommonTokenStream(java8Lexer);
        Java8Parser java8Parser = new Java8Parser(tokens);
        ParseTree tree = java8Parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        UppercaseMethodListener uppercaseMethodListener = new UppercaseMethodListener();
        walker.walk(uppercaseMethodListener, tree);

        assertThat(uppercaseMethodListener.getErrors().size(), is(1));
        assertThat(uppercaseMethodListener.getErrors().get(0),
                is("Method DoSomething is uppercased!"));
    }
}
