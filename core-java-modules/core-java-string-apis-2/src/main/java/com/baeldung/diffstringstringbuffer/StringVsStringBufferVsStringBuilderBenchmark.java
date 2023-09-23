package com.baeldung.diffstringstringbuffer;

import org.openjdk.jmh.annotations.Benchmark;

public class StringVsStringBufferVsStringBuilderBenchmark {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public void stringBenchmark() {
        String[] userNameParts = {"Coder", "Cat", "Down", "Top", "Town"};
        String userName = userNameParts[2] + userNameParts[4] + userNameParts[0];
        userName = userNameParts[0] + userNameParts[2] + userNameParts[4];
    }

    @Benchmark
    public void stringBufferBenchmark() {
        String[] userNameParts = {"Coder", "Cat", "Down", "Top", "Town"};
        StringBuffer userName = new StringBuffer();
        userName.append(userNameParts[2]).append(userNameParts[4]).append(userNameParts[0]);
        userName.delete(8, userName.length()).insert(0, "Coder");
    }

    @Benchmark
    public void stringBuilderBenchmark() {
        String[] userNameParts = {"Coder", "Cat", "Down", "Top", "Town"};
        StringBuilder userName = new StringBuilder();
        userName.append(userNameParts[2]).append(userNameParts[4]).append(userNameParts[0]);
        userName.delete(8, userName.length()).insert(0, "Coder");
    }
}
