package com.baeldung.architecture.hexagonal;

import com.baeldung.architecture.hexagonal.component.Component;
import com.baeldung.architecture.hexagonal.dependencies.Dependency1;
import com.baeldung.architecture.hexagonal.dependencies.Dependency1Adapter;
import com.baeldung.architecture.hexagonal.dependencies.Dependency2;
import com.baeldung.architecture.hexagonal.dependencies.Dependency2Adapter;
import com.baeldung.architecture.hexagonal.callers.Caller1;
import com.baeldung.architecture.hexagonal.callers.Caller1Adapter;
import com.baeldung.architecture.hexagonal.callers.Caller2;
import com.baeldung.architecture.hexagonal.callers.Caller2Adapter;

public class HexagonalApplication {
    public static void main(String[] args) {
        Dependency1 dependency1 = new Dependency1();
        Dependency2 dependency2 = new Dependency2();

        Component component = new Component(new Dependency1Adapter(dependency1), new Dependency2Adapter(dependency2));

        Caller1 caller1 = new Caller1(new Caller1Adapter(component));
        Caller2 caller2 = new Caller2(new Caller2Adapter(component));

        if (args.length == 0) {
            caller1.call();
        } else {
            caller2.call(args);
        }
    }
}
