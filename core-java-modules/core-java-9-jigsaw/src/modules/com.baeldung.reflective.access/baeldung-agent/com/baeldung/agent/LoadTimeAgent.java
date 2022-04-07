package com.baeldung.agent;

import java.lang.instrument.Instrumentation;
import java.util.*;

public class LoadTimeAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs: " + agentArgs);

        if (agentArgs != null) {
            addExportsAndOpensByClassName(inst, agentArgs);
        }
        else {
            addExportsAndOpensToUnnamedModule(inst);
        }
    }

    private static void addExportsAndOpensByClassName(Instrumentation inst, String agentArgs) {
        String[] array = agentArgs.split(",");
        try {
            String className1 = array[0];
            String className2 = array[1];
            Class<?> clazz1 = Class.forName(className1);
            Class<?> clazz2 = Class.forName(className2);

            Module srcModule = clazz1.getModule();
            Module targetModule = clazz2.getModule();
            redefineModule(inst, srcModule, targetModule);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void addExportsAndOpensToUnnamedModule(Instrumentation inst) {
        Module unnamedModule = ClassLoader.getSystemClassLoader().getUnnamedModule();
        Set<Module> modules = ModuleLayer.boot().modules();

        for (Module m : modules) {
            redefineModule(inst, m, unnamedModule);
        }
    }

    private static void redefineModule(Instrumentation inst, Module src, Module target) {
        // prepare extra reads
        Set<Module> extraReads = Collections.singleton(target);

        // prepare extra exports
        Set<String> packages = src.getPackages();
        Map<String, Set<Module>> extraExports = new HashMap<>();
        for (String pkg : packages) {
            extraExports.put(pkg, extraReads);
        }

        // prepare extra opens
        Map<String, Set<Module>> extraOpens = new HashMap<>();
        for (String pkg : packages) {
            extraOpens.put(pkg, extraReads);
        }

        // prepare extra uses
        Set<Class<?>> extraUses = Collections.emptySet();

        // prepare extra uses
        Map<Class<?>, List<Class<?>>> extraProvides = Collections.emptyMap();

        // redefine module
        inst.redefineModule(src, extraReads, extraExports, extraOpens, extraUses, extraProvides);
    }
}