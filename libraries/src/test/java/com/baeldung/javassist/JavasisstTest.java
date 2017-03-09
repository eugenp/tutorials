package com.baeldung.javassist;


import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.bytecode.*;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JavasisstTest {
    @Test
    public void givenJavasisstAPI_whenConstructClass_thenGenerateAClassFile() throws CannotCompileException, IOException {
        //given
        ClassFile cf = new ClassFile(false, "com.baeldung.JavassistGeneratedClass", null);
        cf.setInterfaces(new String[]{"java.lang.Cloneable"});

        FieldInfo f = new FieldInfo(cf.getConstPool(), "id", "I");
        f.setAccessFlags(AccessFlag.PUBLIC);
        cf.addField(f);

        //when
        String className = "JavassistGeneratedClass.class";
        cf.write(new DataOutputStream(new FileOutputStream(className)));

        //then
        String classContent = new String(Files.readAllBytes(Paths.get(className)));
        assertTrue(classContent.contains("java/lang/Cloneable"));
    }

    @Test
    public void givenJavaClass_whenLoadAtByJavassist_thenTraversWholeClass() throws NotFoundException, CannotCompileException, BadBytecode {
        //given
        ClassPool cp = ClassPool.getDefault();
        ClassFile cf = cp.get("com.baeldung.javasisst.Point").getClassFile();
        MethodInfo minfo = cf.getMethod("move");
        CodeAttribute ca = minfo.getCodeAttribute();
        CodeIterator ci = ca.iterator();

        //when
        List<String> operations = new LinkedList<>();
        while (ci.hasNext()) {
            int index = ci.next();
            int op = ci.byteAt(index);
            operations.add(Mnemonic.OPCODE[op]);
        }

        //then
        assertEquals(operations,
                Arrays.asList("aload_0", "iload_1", "putfield", "aload_0", "iload_2", "putfield", "return"));

    }

    @Test
    public void givenTableOfInstructions_whenAddNewInstruction_thenShouldConstructProperSequence() throws NotFoundException, BadBytecode {
        //given
        ClassFile cf = ClassPool.getDefault().get("com.baeldung.javasisst.Point").getClassFile();
        ConstPool cp = cf.getConstPool();
        Bytecode b = new Bytecode(cp, 1, 0);
        b.addIconst(3);
        b.addReturn(CtClass.intType);
        CodeAttribute ca = b.toCodeAttribute();
        CodeIterator ci = ca.iterator();

        //when
        List<String> operations = new LinkedList<>();
        while (ci.hasNext()) {
            int index = ci.next();
            int op = ci.byteAt(index);
            operations.add(Mnemonic.OPCODE[op]);
        }

        //then
        assertEquals(operations,
                Arrays.asList("iconst_3", "ireturn"));
    }

    @Test
    public void givenLoadedClass_whenAddConstructorToClass_shouldCreateClassWithConstructor() throws NotFoundException, CannotCompileException, BadBytecode {
        //given
        ClassFile cf = ClassPool.getDefault().get("com.baeldung.javasisst.Point").getClassFile();
        Bytecode code = new Bytecode(cf.getConstPool());
        code.addAload(0);
        code.addInvokespecial("java/lang/Object", MethodInfo.nameInit, "()V");
        code.addReturn(null);
        code.setMaxLocals(1);

        //when
        MethodInfo minfo = new MethodInfo(cf.getConstPool(), MethodInfo.nameInit, "()V");
        minfo.setCodeAttribute(code.toCodeAttribute());
        cf.addMethod(minfo);

        //then
        CodeIterator ci = code.toCodeAttribute().iterator();
        List<String> operations = new LinkedList<>();
        while (ci.hasNext()) {
            int index = ci.next();
            int op = ci.byteAt(index);
            operations.add(Mnemonic.OPCODE[op]);
        }

        operations.contains("invokespecial");


    }
}
