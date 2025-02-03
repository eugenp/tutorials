package com.baeldung.bytecode;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.util.TraceClassVisitor;

import javassist.ClassPool;
import javassist.NotFoundException;
import javassist.bytecode.ClassFile;

public class ViewBytecodeUnitTest {
    
    @Test
    public void whenUsingASM_thenReadBytecode() throws IOException {
        ClassReader reader = new ClassReader("java.lang.Object");
        StringWriter sw = new StringWriter();
        TraceClassVisitor tcv = new TraceClassVisitor(new PrintWriter(sw));
        reader.accept(tcv, 0); 
        
        assertTrue(sw.toString().contains("public class java/lang/Object")); 
    }
    
    @Test
    public void whenUsingBCEL_thenReadBytecode() throws ClassNotFoundException {
        JavaClass objectClazz = Repository.lookupClass("java.lang.Object");
        
        assertEquals("java.lang.Object", objectClazz.getClassName());
        assertEquals(13, objectClazz.getMethods().length);
        assertTrue(objectClazz.toString().contains("public class java.lang.Object"));
    }
    
    @Test
    public void whenUsingJavassist_thenReadBytecode() throws NotFoundException {
        ClassPool cp = ClassPool.getDefault();
        ClassFile cf = cp.get("java.lang.Object").getClassFile();
        
        assertEquals("java.lang.Object", cf.getName());
        assertEquals(13, cf.getMethods().size());
    }
    
}


