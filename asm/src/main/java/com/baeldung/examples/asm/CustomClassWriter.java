package com.baeldung.examples.asm;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.V1_5;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.TraceClassVisitor;

/**
 *
 * @author baeldung
 * @param <String>
 */
public class CustomClassWriter {

    ClassReader reader;
    ClassWriter writer;
    AddFieldAdapter addFieldAdapter;
    AddInterfaceAdapter addInterfaceAdapter;
    PublicizeMethodAdapter pubMethAdapter;
    final static String CLASSNAME = "java.lang.Integer";
    final static String CLONEABLE = "java/lang/Cloneable";

    public CustomClassWriter() {

        try {
            reader = new ClassReader(CLASSNAME);
            writer = new ClassWriter(reader, 0);

        } catch (IOException ex) {
            Logger.getLogger(CustomClassWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CustomClassWriter(byte[] contents) {
        reader = new ClassReader(contents);
        writer = new ClassWriter(reader, 0);
    }

    public static void main(String[] args) {
        CustomClassWriter ccw = new CustomClassWriter();
        ccw.publicizeMethod();
    }

    public byte[] addField() {
        addFieldAdapter = new AddFieldAdapter("aNewBooleanField", org.objectweb.asm.Opcodes.ACC_PUBLIC, writer);
        reader.accept(addFieldAdapter, 0);
        return writer.toByteArray();
    }

    public byte[] publicizeMethod() {
        pubMethAdapter = new PublicizeMethodAdapter(writer);
        reader.accept(pubMethAdapter, 0);
        return writer.toByteArray();
    }

    public byte[] addInterface() {
        addInterfaceAdapter = new AddInterfaceAdapter(writer);
        reader.accept(addInterfaceAdapter, 0);
        return writer.toByteArray();
    }

    public class AddInterfaceAdapter extends ClassVisitor {

        public AddInterfaceAdapter(ClassVisitor cv) {
            super(ASM4, cv);
        }

        @Override
        public void visit(int version, int access, String name,
                String signature, String superName, String[] interfaces) {
            String[] holding = new String[interfaces.length + 1];
            holding[holding.length - 1] = CLONEABLE;
            System.arraycopy(interfaces, 0, holding, 0, interfaces.length);

            cv.visit(V1_5, access, name, signature, superName, holding);
        }

    }

    public class PublicizeMethodAdapter extends ClassVisitor {

        final Logger logger = Logger.getLogger("PublicizeMethodAdapter");
        TraceClassVisitor tracer;
        PrintWriter pw = new PrintWriter(System.out);

        public PublicizeMethodAdapter(ClassVisitor cv) {
            super(ASM4, cv);
            this.cv = cv;
            tracer = new TraceClassVisitor(cv, pw);
        }

        @Override
        public MethodVisitor visitMethod(int access,
                String name,
                String desc,
                String signature,
                String[] exceptions) {

            if (name.equals("toUnsignedString0")) {
                logger.info("Visiting unsigned method");
                return tracer.visitMethod(ACC_PUBLIC + ACC_STATIC, name, desc, signature, exceptions);
            }
            return tracer.visitMethod(access, name, desc, signature, exceptions);

        }

        public void visitEnd() {
            tracer.visitEnd();
            System.out.println(tracer.p.getText());
        }

    }

    public class AddFieldAdapter extends ClassVisitor {

        String fieldName;
        int access;
        boolean isFieldPresent;

        public AddFieldAdapter(String fieldName, int access, ClassVisitor cv) {
            super(ASM4, cv);
            this.cv = cv;
            this.access = access;
            this.fieldName = fieldName;
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc,
                String signature, Object value) {
            if (name.equals(fieldName)) {
                isFieldPresent = true;
            }
            return cv.visitField(access, name, desc, signature, value);
        }

        @Override
        public void visitEnd() {
            if (!isFieldPresent) {
                FieldVisitor fv = cv.visitField(access, fieldName, Type.BOOLEAN_TYPE.toString(), null, null);
                if (fv != null) {
                    fv.visitEnd();
                }
            }
            cv.visitEnd();
        }

    }

}
