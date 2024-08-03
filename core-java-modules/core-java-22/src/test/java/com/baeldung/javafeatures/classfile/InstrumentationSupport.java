package com.baeldung.javafeatures.classfile;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.instrument.Instrumentation;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

public class InstrumentationSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstrumentationSupport.class);

    public static final String REQUIRED_VM_ARGS = "Dynamic Attachment in java 22+ requires VM initialization with -Djdk.attach.allowAttachSelf=true";

    private static Instrumentation instrumentation;

    public static void agentmain(final String args, final Instrumentation inst) {
        instrumentation = inst;
    }

    static String createTempJar() throws IOException {
        var path = Files.createTempFile("agent", ".jar");

        try (var zout = new ZipOutputStream(Files.newOutputStream(path)); //
            var writer = new PrintWriter(new OutputStreamWriter(zout))) {
            zout.putNextEntry(new ZipEntry("META-INF/MANIFEST.MF"));

            writer.print("Agent-Class: ");
            writer.println(InstrumentationSupport.class.getName());
            writer.print("Premain-Class: ");
            writer.println(InstrumentationSupport.class.getName());
            writer.println("Can-Redefine-Classes: true");
            writer.println("Can-Retransform-Classes: true");
        }

        return path.toAbsolutePath().toString();
    }

    public static Instrumentation getInstrumentation() {
        Instrumentation ret = instrumentation;
        if (ret == null) {
            try {
                selfInstall();
            } catch (final Exception e) {
                throw e instanceof RuntimeException re ? re : new RuntimeException("Unable to install java-agent", e);
            }
            ret = instrumentation;
        }
        return ret;
    }

    static VirtualMachine getVM() throws Exception {
        var pid = Long.toString(ProcessHandle.current().pid());

        VirtualMachine vm;

        try {
            vm = VirtualMachine.attach(pid);
        } catch (final AttachNotSupportedException | IOException e) {
            LOGGER.warn("Attachment Failed: ({}). To avoid this, run with -Djdk.attach.allowAttachSelf=true.", e.getMessage());

            throw e instanceof RuntimeException re ? re : new RuntimeException("Unable to attach", e);
        }

        return vm;
    }

    public static void premain(/* agent arg */ String ignore, final Instrumentation inst) {
        instrumentation = inst;
    }

    static void selfInstall() throws Exception {
        if (instrumentation == null) {
            final VirtualMachine vm = getVM();
            final String jar = createTempJar();
            vm.loadAgent(jar);
            LOGGER.info("Installing saving agent at vm {}", vm.id());
            vm.detach();
        }
    }

}
