package com.baeldung.javafeatures.classfile;

import java.io.Serializable;
import java.lang.classfile.ClassFile;
import java.lang.classfile.ClassTransform;
import java.lang.classfile.constantpool.ClassEntry;
import java.lang.constant.ClassDesc;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.reflect.AccessFlag;
import java.security.ProtectionDomain;
import java.util.Set;
import java.util.stream.Collectors;

public class SerializableTransformer implements ClassFileTransformer {

    // skip core jdk classes
    final Set<String> filtered;
    final ClassDesc serializable;

    public SerializableTransformer() {
        filtered = ModuleLayer.boot()
            .modules()
            .stream()
            .map(Module::getName)
            .filter(name -> name != null && (name.startsWith("java") || name.startsWith("jdk")))
            .collect(Collectors.toSet());

        serializable = ClassDesc.of(Serializable.class.getName());
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        var cf = ClassFile.of();

        var model = cf.parse(classfileBuffer);

        var flags = model.flags();

        // No need to transform abstract classes/interfaces
        if (flags.has(AccessFlag.ABSTRACT) || flags.has(AccessFlag.INTERFACE)) {
            return classfileBuffer;
        }

        var interfaces = model.interfaces().stream().map(ClassEntry::asSymbol).collect(Collectors.toList());

        // Already serializable
        if (interfaces.contains(serializable)) {
            return classfileBuffer;
        } else {
            interfaces.add(serializable);
        }

        var ct = ClassTransform.endHandler(cb -> cb.withInterfaceSymbols(interfaces));

        return cf.transform(model, ct);
    }

    @Override
    public byte[] transform(Module module, ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String moduleName;

        if (module != null && (moduleName = module.getName()) != null && filtered.contains(moduleName)) {
            return classfileBuffer;
        }

        return transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
    }
}
