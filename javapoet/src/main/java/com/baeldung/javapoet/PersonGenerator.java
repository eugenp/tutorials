package com.baeldung.javapoet;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.IOException;

public class PersonGenerator {

    public void generate() throws IOException {
        FieldSpec name = FieldSpec.builder(String.class, "name")
                .addModifiers(Modifier.PRIVATE)
                .build();

        MethodSpec getName = MethodSpec.methodBuilder("getName")
                .addModifiers(Modifier.PUBLIC)
                .returns(String.class)
                .addStatement("return $L", "name")
                .build();

        TypeSpec helloWorld = TypeSpec
                .classBuilder("Person")
                .addField(name)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(getName)
                .build();

        JavaFile javaFile = JavaFile
                .builder("com.baeldung.javapoet.helloworld", helloWorld)
                .build();

        javaFile.writeTo(System.out);
    }

}
