package com.baeldung.classfiles;

import static java.lang.constant.ConstantDescs.CD_String;
import static java.lang.constant.ConstantDescs.CD_double;

import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassFileBuilder;
import java.lang.classfile.ClassTransform;
import java.lang.classfile.CodeTransform;
import java.lang.classfile.Label;
import java.lang.classfile.MethodBuilder;
import java.lang.classfile.MethodTransform;
import java.lang.classfile.instruction.InvokeInstruction;
import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.lang.reflect.AccessFlag;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public class CodeGenerator {

    public static void generate() throws IOException {

        Consumer<MethodBuilder> calculateAnnualBonusBuilder = methodBuilder -> methodBuilder.withCode(codeBuilder -> {
            Label notSales = codeBuilder.newLabel();
            Label notEngineer = codeBuilder.newLabel();
            ClassDesc stringClass = ClassDesc.of("java.lang.String");

            codeBuilder.aload(3)
                .ldc("sales")
                .invokevirtual(stringClass, "equals", MethodTypeDesc.of(ClassDesc.of("Z"), stringClass))
                .ifeq(notSales)
                .dload(1)
                .ldc(0.35)
                .dmul()
                .dreturn()
                .labelBinding(notSales)
                .aload(3)
                .ldc("engineer")
                .invokevirtual(stringClass, "equals", MethodTypeDesc.of(ClassDesc.of("Z"), stringClass))
                .ifeq(notEngineer)
                .dload(1)
                .ldc(0.25)
                .dmul()
                .dreturn()
                .labelBinding(notEngineer)
                .dload(1)
                .ldc(0.15)
                .dmul()
                .dreturn();
        });

        var classBuilder = ClassFile.of()
            .build(ClassDesc.of("EmployeeSalaryCalculator"),
                cb -> cb.withMethod("calculateAnnualBonus", MethodTypeDesc.of(CD_double, CD_double, CD_String), AccessFlag.PUBLIC.mask(),
                    calculateAnnualBonusBuilder));

        Files.write(Path.of("EmployeeSalaryCalculator.class"), classBuilder);
    }

    public static void transform() throws IOException {
        var basePath = Files.readAllBytes(Path.of("EmployeeSalaryCalculator.class"));

        CodeTransform codeTransform = ClassFileBuilder::accept;

        MethodTransform methodTransform = MethodTransform.transformingCode(codeTransform);
        ClassTransform classTransform = ClassTransform.transformingMethods(methodTransform);

        ClassFile classFile = ClassFile.of();
        byte[] transformedClass = classFile.transformClass(classFile.parse(basePath), classTransform);
        Files.write(Path.of("TransformedEmployeeSalaryCalculator.class"), transformedClass);
    }

    public static void main(String[] args) throws IOException {
        generate();
        transform();
    }
}
