package com.baeldung.antlr;

import com.baeldung.antlr.java.JavaMethodDeclarationWalker;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JavaParserTest {

    @Test
    public void whenOneMethodStartsWithUpperCase_thenOneErrorReturned() throws Exception{
        Java8Lexer java8Lexer = new Java8Lexer(
                TestingUtils.loadResourceAsCharStream("SampleClass.java"));
        CommonTokenStream tokens = new CommonTokenStream(java8Lexer);
        Java8Parser java8Parser = new Java8Parser(tokens);
        ParseTree tree = java8Parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        JavaMethodDeclarationWalker javaMethodDeclarationWalker = new JavaMethodDeclarationWalker();
        walker.walk(javaMethodDeclarationWalker, tree);

        assertThat(javaMethodDeclarationWalker.getErrors().size(), is(1));
        assertThat(javaMethodDeclarationWalker.getErrors().get(0),
                is("Method DoSomethingElse starts with uppercase!"));
    }
}
