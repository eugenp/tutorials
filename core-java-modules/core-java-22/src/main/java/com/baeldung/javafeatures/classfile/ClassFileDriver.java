package com.baeldung.javafeatures.classfile;

import java.io.IOException;
import java.lang.classfile.ClassElement;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassModel;
import java.lang.classfile.MethodModel;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClassFileDriver {
    public Path updateClass() throws IOException {
        final String PREFIX = "test_";
        final Path PATH = Path.of("target/classes/com/baeldung/javafeatures/classfile/ClassFileExample.class");
        ClassFile cf = ClassFile.of();
        ClassModel classModel = cf.parse(PATH);
        byte[] newBytes = cf.build(classModel.thisClass().asSymbol(), classBuilder -> {
            for (ClassElement ce : classModel) {
                if (!(ce instanceof MethodModel mm && mm.methodName()
                  .stringValue()
                  .startsWith(PREFIX))) {
                    classBuilder.with(ce);
                }
            }
        });
        return Files.write(PATH, newBytes);
    }
}
