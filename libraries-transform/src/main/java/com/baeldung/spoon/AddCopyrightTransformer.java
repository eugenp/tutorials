package com.baeldung.spoon;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtComment.CommentType;
import spoon.reflect.declaration.CtClass;

public class AddCopyrightTransformer {
    
    public void addCopyright(String source) {

        SpoonAPI spoon = new Launcher();
        spoon.addInputResource(source);
        spoon.getEnvironment().setLevel("DEBUG");
        CtModel model = spoon.buildModel();
        
        model.filterChildren((el) -> el instanceof CtClass<?>)
          .forEach((CtClass<?> cl) -> {
              CtComment comment = cl.getFactory()
                .createComment("Copyright(c) 2023 etc", CommentType.JAVADOC);
              cl.addComment(comment);                                  
          });
        
        spoon.setSourceOutputDirectory("./target");
        spoon.prettyprint();        
    }
    
    
}
