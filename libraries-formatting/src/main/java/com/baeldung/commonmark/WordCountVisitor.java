package com.baeldung.commonmark;

import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Text;

public class WordCountVisitor extends AbstractVisitor {

    int wordCount = 0;

    @Override
    public void visit(Text text) {
        wordCount += text.getLiteral().split("\\w+").length;
        visitChildren(text);
    }



}
