package com.baeldung.teavm;

import org.teavm.tooling.TeaVMTool;
import org.teavm.tooling.TeaVMTargetType;
import org.teavm.vm.TeaVMOptimizationLevel;

import java.io.File;

public class TeaVMRunner {
    public static void main(String[] args) throws Exception {
        TeaVMTool tool = new TeaVMTool();
        tool.setTargetDirectory(new File("target/teavm"));
        tool.setTargetFileName("calculator.js");
        tool.setMainClass("com.baeldung.teavm.Calculator");
        tool.setTargetType(TeaVMTargetType.JAVASCRIPT);
        tool.setOptimizationLevel(TeaVMOptimizationLevel.ADVANCED);
        tool.setDebugInformationGenerated(false);
        tool.setIncremental(false);
        tool.setObfuscated(true);
        tool.generate();
    }
}