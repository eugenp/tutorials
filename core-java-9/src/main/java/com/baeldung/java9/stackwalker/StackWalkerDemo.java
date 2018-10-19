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
        List<StackFrame> stackTrace = StackWalker.getInstance()
            .walk(this::walkExample);

        printStackTrace(stackTrace);

        System.out.println("---------------------------------------------");

        stackTrace = StackWalker.getInstance()
            .walk(this::walkExample2);

        printStackTrace(stackTrace);

        System.out.println("---------------------------------------------");

        String line = StackWalker.getInstance().walk(this::walkExample3);
        System.out.println(line);

        System.out.println("---------------------------------------------");
        
        stackTrace = StackWalker.getInstance(StackWalker.Option.SHOW_REFLECT_FRAMES)
            .walk(this::walkExample);

        printStackTrace(stackTrace);
        
        System.out.println("---------------------------------------------");
        
        Runnable r = () -> {
            List<StackFrame> stackTrace2 = StackWalker.getInstance(StackWalker.Option.SHOW_HIDDEN_FRAMES)
                .walk(this::walkExample);
            printStackTrace(stackTrace2);
        };
        r.run();
    }

    public List<StackFrame> walkExample(Stream<StackFrame> stackFrameStream) {
        return stackFrameStream.collect(Collectors.toList());
    }

    public List<StackFrame> walkExample2(Stream<StackFrame> stackFrameStream) {
        return stackFrameStream.filter(frame -> frame.getClassName()
            .contains("com.baeldung"))
            .collect(Collectors.toList());
    }

    public String walkExample3(Stream<StackFrame> stackFrameStream) {
        return stackFrameStream.filter(frame -> frame.getClassName()
            .contains("com.baeldung")
            && frame.getClassName()
            .endsWith("Test"))
            .findFirst()
            .map(frame -> frame.getClassName() + "#" + frame.getMethodName() + ", Line " + frame.getLineNumber())
            .orElse("Unknown caller");
    }
    
    public void findCaller() {
        Class<?> caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
        System.out.println(caller.getCanonicalName());
    }

    public void printStackTrace(List<StackFrame> stackTrace) {
        for (StackFrame stackFrame : stackTrace) {
            System.out.println(stackFrame.getClassName()
                .toString() + "#" + stackFrame.getMethodName() + ", Line " + stackFrame.getLineNumber());
        }
    }
}
