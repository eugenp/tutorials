package com.baeldung.examples.asm.instrumentation;

import com.baeldung.examples.asm.CustomClassWriter;

import java.lang.instrument.Instrumentation;

public class Premain {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer((l, name, c, d, b) -> {

            if (name.equals("java/lang/Integer")) {
                CustomClassWriter cr = new CustomClassWriter(b);
                return cr.addField();
            }
            return b;
        });
    }
}
