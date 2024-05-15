package com.baeldung.spoon;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtComment.CommentType;
import spoon.reflect.declaration.CtClass;

public class AddCopyrightProcessor extends AbstractProcessor<CtClass<?>> {

    @Override
    public void process(CtClass<?> clazz) {
        CtComment comment = getFactory().createComment("Copyright(c) 2023 etc", CommentType.JAVADOC);
        clazz.addComment(comment);                    
    }    
}
