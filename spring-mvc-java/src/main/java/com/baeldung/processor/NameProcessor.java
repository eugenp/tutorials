package com.baeldung.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractTextChildModifierAttrProcessor;

public class NameProcessor extends AbstractTextChildModifierAttrProcessor {

    public NameProcessor() {
        super("name");
    }

    @Override
    protected String getText(final Arguments arguements, final Element elements, final String attributeName) {
        return "Hello, " + elements.getAttributeValue(attributeName) + "!";
    }

    @Override
    public int getPrecedence() {
        return 1000;
    }

}
