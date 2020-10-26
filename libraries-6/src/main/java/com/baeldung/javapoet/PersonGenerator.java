package com.baeldung.javapoet;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class PersonGenerator {

    private static final String FOUR_WHITESPACES = "    ";
    private static final String PERSON_PACKAGE_NAME = "com.baeldung.javapoet.test.person";

    private File outputFile;

    public PersonGenerator() {
        outputFile = new File(getOutputPath().toUri());
    }

    public static String getPersonPackageName() {
        return PERSON_PACKAGE_NAME;
    }

    public Path getOutputPath() {
        return Paths.get(new File(".").getAbsolutePath() + "/gensrc");
    }

    public FieldSpec getDefaultNameField() {
        return FieldSpec
          .builder(String.class, "DEFAULT_NAME")
          .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
          .initializer("$S", "Alice")
          .build();
    }

    public MethodSpec getSortByLengthMethod() {
        return MethodSpec
          .methodBuilder("sortByLength")
          .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
          .addParameter(ParameterSpec
            .builder(ParameterizedTypeName.get(ClassName.get(List.class), TypeName.get(String.class)), "strings")
            .build())
          .addStatement("$T.sort($N, $L)", Collections.class, "strings", getComparatorAnonymousClass())
          .build();
    }

    public MethodSpec getPrintNameMultipleTimesMethod() {
        return MethodSpec
          .methodBuilder("printNameMultipleTimes")
          .addModifiers(Modifier.PUBLIC)
          .addCode(getPrintNameMultipleTimesLambdaImpl())
          .build();
    }

    public CodeBlock getPrintNameMultipleTimesImpl() {
        return CodeBlock
          .builder()
          .beginControlFlow("for (int i = $L; i < $L; i++)")
          .addStatement("System.out.println(name)")
          .endControlFlow()
          .build();
    }

    public CodeBlock getPrintNameMultipleTimesLambdaImpl() {
        return CodeBlock
          .builder()
          .addStatement("$T<$T> names = new $T<>()", List.class, String.class, ArrayList.class)
          .addStatement("$T.range($L, $L).forEach(i -> names.add(name))", IntStream.class, 0, 10)
          .addStatement("names.forEach(System.out::println)")
          .build();
    }

    public TypeSpec getGenderEnum() {
        return TypeSpec
          .enumBuilder("Gender")
          .addModifiers(Modifier.PUBLIC)
          .addEnumConstant("MALE")
          .addEnumConstant("FEMALE")
          .addEnumConstant("UNSPECIFIED")
          .build();
    }

    public TypeSpec getPersonInterface() {
        return TypeSpec
          .interfaceBuilder("Person")
          .addModifiers(Modifier.PUBLIC)
          .addField(getDefaultNameField())
          .addMethod(MethodSpec
            .methodBuilder("getName")
            .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
            .returns(String.class)
            .build())
          .addMethod(MethodSpec
            .methodBuilder("getDefaultName")
            .addModifiers(Modifier.PUBLIC, Modifier.DEFAULT)
            .returns(String.class)
            .addCode(CodeBlock
              .builder()
              .addStatement("return DEFAULT_NAME")
              .build())
            .build())
          .build();
    }

    public TypeSpec getStudentClass() {
        return TypeSpec
          .classBuilder("Student")
          .addSuperinterface(ClassName.get(PERSON_PACKAGE_NAME, "Person"))
          .addModifiers(Modifier.PUBLIC)
          .addField(FieldSpec
            .builder(String.class, "name")
            .addModifiers(Modifier.PRIVATE)
            .build())
          .addMethod(MethodSpec
            .methodBuilder("getName")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(String.class)
            .addStatement("return this.name")
            .build())
          .addMethod(MethodSpec
            .methodBuilder("setName")
            .addParameter(String.class, "name")
            .addModifiers(Modifier.PUBLIC)
            .addStatement("this.name = name")
            .build())
          .addMethod(getPrintNameMultipleTimesMethod())
          .addMethod(getSortByLengthMethod())
          .build();
    }

    public TypeSpec getComparatorAnonymousClass() {
        return TypeSpec
          .anonymousClassBuilder("")
          .addSuperinterface(ParameterizedTypeName.get(Comparator.class, String.class))
          .addMethod(MethodSpec
            .methodBuilder("compare")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(Override.class)
            .addParameter(String.class, "a")
            .addParameter(String.class, "b")
            .returns(int.class)
            .addStatement("return a.length() - b.length()")
            .build())
          .build();
    }

    public void generateGenderEnum() throws IOException {
        writeToOutputFile(getPersonPackageName(), getGenderEnum());
    }

    public void generatePersonInterface() throws IOException {
        writeToOutputFile(getPersonPackageName(), getPersonInterface());
    }

    public void generateStudentClass() throws IOException {
        writeToOutputFile(getPersonPackageName(), getStudentClass());
    }

    private void writeToOutputFile(String packageName, TypeSpec typeSpec) throws IOException {
        JavaFile javaFile = JavaFile
          .builder(packageName, typeSpec)
          .indent(FOUR_WHITESPACES)
          .build();
        javaFile.writeTo(outputFile);
    }

}
