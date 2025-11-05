package com.baeldung.jfrevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.AccessController;
import java.security.PrivilegedAction;

@RestController
public class DeprecatedApiDemo {
    @Autowired
    LegacyClass legacyClass;

    @GetMapping("/deprecated")
    public String triggerDeprecated() {
        AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
            System.setProperty("demo.log", "true");
            return null;
        });

        Boolean b = new Boolean("true");
        System.out.println("Boolean value: " + b);

        BigDecimal roundedPositive = new BigDecimal("2.345").setScale(2, BigDecimal.ROUND_CEILING);
        System.out.println(roundedPositive);

        return "Done";
    }

    @GetMapping("/deprecated2")
    public String triggerDeprecated2() {
        legacyClass.oldMethod();

        return "Completed";
    }

    @GetMapping("/deprecated3")
    public String triggerDeprecated3() {
        legacyClass.wrapperCall();

        return "Finished";
    }
}
