package com.baeldung.java9.stackwalker;

import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StackWalkerDemo {

    public void methodOne() {
        this.methodTwo();
    }

    public void methodTwo() {
        this.methodThree();
    }

    public void methodThree() {
        List<StackFrame> stackTrace = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
            .walk(StackWalkerDemo::walkExample);

        printStackTrace(stackTrace);

        System.out.println("---------------------------------------------");

        stackTrace = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
            .walk(StackWalkerDemo::walkExample2);

        printStackTrace(stackTrace);

        System.out.println("---------------------------------------------");

        String line = StackWalker.getInstance()
            .walk(StackWalkerDemo::walkExample3);
        System.out.println(line);
    }

    private static List<StackFrame> walkExample(Stream<StackFrame> stackFrameStream) {
        return stackFrameStream.collect(Collectors.toList());
    }

    private static List<StackFrame> walkExample2(Stream<StackFrame> stackFrameStream) {
        return stackFrameStream.filter(frame -> frame.getClassName()
            .contains("com.baeldung"))
            .collect(Collectors.toList());
    }

    private static String walkExample3(Stream<StackFrame> stackFrameStream) {
        return stackFrameStream.filter(frame -> frame.getClassName()
            .contains("com.baeldung")
            && frame.getClassName()
                .endsWith("Test"))
            .findFirst()
            .map(frame -> frame.getClassName() + "#" + frame.getMethodName() + ", Line " + frame.getLineNumber())
            .orElse("Unknown caller");
    }

    private void printStackTrace(List<StackFrame> stackTrace) {
        for (StackFrame stackFrame : stackTrace) {
            System.out.println(stackFrame.getDeclaringClass()
                .toString() + "#" + stackFrame.getMethodName() + ", Line " + stackFrame.getLineNumber());
        }
    }
}
