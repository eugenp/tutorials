package com.baeldung.classloader.internal;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.management.ManagementFactory;

import org.slf4j.LoggerFactory;

public class InternalJdkSupport {

    static final Class<?> BUILT_IN_CLASSLOADER;
    static final VarHandle UCP;

    static {
        var log = LoggerFactory.getLogger(InternalJdkSupport.class);

        Class<?> clazz = null;
        Class<?> ucpClazz = null;
        VarHandle ucp = null;

        try {
            ucpClazz = Class.forName("jdk.internal.loader.URLClassPath");
            clazz = Class.forName("jdk.internal.loader.BuiltinClassLoader");
            var lookup = MethodHandles.privateLookupIn(clazz, MethodHandles.lookup());
            ucp = lookup.findVarHandle(clazz, "ucp", ucpClazz);
        } catch (ClassNotFoundException e) {
            log.warn("JDK {} not supported => {} not available.", jdkInfo(), e.getMessage());
        } catch (NoSuchFieldException e) {
            log.warn("JDK {} not supported => BuiltinClassLoader.ucp not present", jdkInfo());
        } catch (IllegalAccessException e) {
            log.warn("BuiltinClassLoader.ucp requires --add-opens java.base/jdk.internal.loader=baeldung.classloader");
        }

        BUILT_IN_CLASSLOADER = clazz;
        UCP = ucp;
    }

    public static boolean available() {
        return UCP != null;
    }

    public static Object getURLClassPath(ClassLoader loader) {
        if (!isBuiltIn(loader)) {
            throw new UnsupportedOperationException("Loader not an instance of BuiltinClassLoader");
        }

        if (UCP == null) {
            throw new UnsupportedOperationException("Program must be initialized with --add-opens java.base/jdk.internal.loader=baeldung.classloader");
        }

        try {
            return UCP.get(loader);
        } catch (Exception e) {
            throw new InternalError(e);
        }
    }

    static boolean isBuiltIn(ClassLoader loader) {
        return BUILT_IN_CLASSLOADER != null && BUILT_IN_CLASSLOADER.isInstance(loader);
    }

    static String jdkInfo() {
        String name;
        String vendor;
        String version;

        try {
            var bean = ManagementFactory.getRuntimeMXBean();
            name = bean.getVmName();
            vendor = bean.getVmVendor();
            version = bean.getVmVersion();
        } catch (Exception e) {
            name = System.getProperty("java.vm.name");
            vendor = System.getProperty("java.vendor");
            version = System.getProperty("java.vendor");
        }
        return String.format("{name: %s, vendor: %s, version: %s}", name, vendor, version);
    }
}