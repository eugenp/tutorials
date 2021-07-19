package com.baeldung.reduce.benchmarks;

import com.baeldung.reduce.entities.User;
import java.util.ArrayList;
import java.util.List;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
public class JMHStreamReduceBenchMark {
    
    private final List<User> userList = createUsers();

    public static void main(String[] args) throws RunnerException {
        
        Options options = new OptionsBuilder()
                .include(JMHStreamReduceBenchMark.class.getSimpleName()).threads(1)
                .forks(1).shouldFailOnError(true).shouldDoGC(true)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }
    
    private List<User> createUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i <= 1000000; i++) {
            users.add(new User("John" + i, i));
        }
        return users;
    }

    @Benchmark
    public Integer executeReduceOnParallelizedStream() {
        return this.userList
                .parallelStream()
                .reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
    }

    @Benchmark
    public Integer executeReduceOnSequentialStream() {
        return this.userList
                .stream()
                .reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
    }
}
