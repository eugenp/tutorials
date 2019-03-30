package com.baeldung.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.BadBytecode;
import javassist.bytecode.Bytecode;
import javassist.bytecode.ClassFile;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.MethodInfo;
import javassist.bytecode.Mnemonic;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JavasisstUnitTest {
    @Test
    public void givenJavasisstAPI_whenConstructClass_thenGenerateAClassFile() throws CannotCompileException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        // given
        String classNameWithPackage = "com.baeldung.JavassistGeneratedClass";
        ClassFile cf = new ClassFile(false, classNameWithPackage, null);
        cf.setInterfaces(new String[] { "java.lang.Cloneable" });

        FieldInfo f = new FieldInfo(cf.getConstPool(), "id", "I");
        f.setAccessFlags(AccessFlag.PUBLIC);
        cf.addField(f);

        // when
        String className = "JavassistGeneratedClass.class";
        cf.write(new DataOutputStream(new FileOutputStream(className)));

        // then
        ClassPool classPool = ClassPool.getDefault();
        Field[] fields = classPool.makeClass(cf).toClass().getFields();
        assertEquals(fields[0].getName(), "id");

        String classContent = new String(Files.readAllBytes(Paths.get(className)));
        assertTrue(classContent.contains("java/lang/Cloneable"));
    }

    @Test
    public void givenJavaClass_whenLoadAtByJavassist_thenTraversWholeClass() throws NotFoundException, CannotCompileException, BadBytecode {
        // given
        ClassPool cp = ClassPool.getDefault();
        ClassFile cf = cp.get("com.baeldung.javasisst.Point").getClassFile();
        MethodInfo minfo = cf.getMethod("move");
        CodeAttribute ca = minfo.getCodeAttribute();
        CodeIterator ci = ca.iterator();

        // when
        List<String> operations = new LinkedList<>();
        while (ci.hasNext()) {
            int index = ci.next();
            int op = ci.byteAt(index);
            operations.add(Mnemonic.OPCODE[op]);
        }

        // then
        assertEquals(operations, Arrays.asList("aload_0", "iload_1", "putfield", "aload_0", "iload_2", "putfield", "return"));

    }

    @Test
    public void givenTableOfInstructions_whenAddNewInstruction_thenShouldConstructProperSequence() throws NotFoundException, BadBytecode, CannotCompileException, IllegalAccessException, InstantiationException {
        // given
        ClassFile cf = ClassPool.getDefault().get("com.baeldung.javasisst.ThreeDimensionalPoint").getClassFile();

        // when
        FieldInfo f = new FieldInfo(cf.getConstPool(), "id", "I");
        f.setAccessFlags(AccessFlag.PUBLIC);
        cf.addField(f);

        ClassPool classPool = ClassPool.getDefault();
        Field[] fields = classPool.makeClass(cf).toClass().getFields();
        List<String> fieldsList = Stream.of(fields).map(Field::getName).collect(Collectors.toList());
        assertTrue(fieldsList.contains("id"));

    }

    @Test
    public void givenLoadedClass_whenAddConstructorToClass_shouldCreateClassWithConstructor() throws NotFoundException, CannotCompileException, BadBytecode {
        // given
        ClassFile cf = ClassPool.getDefault().get("com.baeldung.javasisst.Point").getClassFile();
        Bytecode code = new Bytecode(cf.getConstPool());
        code.addAload(0);
        code.addInvokespecial("java/lang/Object", MethodInfo.nameInit, "()V");
        code.addReturn(null);

        // when
        MethodInfo minfo = new MethodInfo(cf.getConstPool(), MethodInfo.nameInit, "()V");
        minfo.setCodeAttribute(code.toCodeAttribute());
        cf.addMethod(minfo);

        // then
        CodeIterator ci = code.toCodeAttribute().iterator();
        List<String> operations = new LinkedList<>();
        while (ci.hasNext()) {
            int index = ci.next();
            int op = ci.byteAt(index);
            operations.add(Mnemonic.OPCODE[op]);
        }

        assertEquals(operations, Arrays.asList("aload_0", "invokespecial", "return"));

    }
}
