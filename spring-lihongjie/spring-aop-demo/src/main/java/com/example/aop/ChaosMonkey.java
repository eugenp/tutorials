package com.example.aop;

import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 来自: https://www.programcreek.com/java-api-examples/?code=MrBW/resilient-transport-service/resilient-transport-service-master/service-library/src/main/java/de/codecentric/resilient/chaosmonkey/ChaosMonkey.java#
 */
@Aspect
@Component
@Profile("chaos")
public class ChaosMonkey {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChaosMonkey.class);

    private DynamicBooleanProperty chaosMonkey =
            DynamicPropertyFactory.getInstance().getBooleanProperty("chaos.monkey.active", true);

    private DynamicIntProperty chaosMonkeyLevel =
            DynamicPropertyFactory.getInstance().getIntProperty("chaos.monkey.level", 5);

    public ChaosMonkey() {

        String chaosMonkeyStatus = chaosMonkey.get() ? "bad mood or evil" : "Eats bananas or sleeps" ;

        LOGGER.info("\n\n---       Chaos Monkey       ---\n            __,__\n" +
                "   .--.  .-\"     \"-.  .--.\n" +
                "  / .. \\/  .-. .-.  \\/ .. \\\n" +
                " | |  '|  /   Y   \\  |'  | |\n" +
                " | \\   \\  \\ 0 | 0 /  /   / |\n" +
                "  \\ '- ,\\.-\"`` ``\"-./, -' /\n" +
                "   `'-' /_   ^ ^   _\\ '-'`\n" +
                "       |  \\._   _./  |\n" +
                "       \\   \\ `~` /   /\n" +
                "        '._ '-=-' _.'\n" +
                "           '~---~'\n" +
                " Status: " + chaosMonkeyStatus +
                "\n------------------------------------ -\n");
    }

    @Around("execution(* de.codecentric.resilient..*.*Service.*(..))")
    public Object createConnoteHystrix(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.debug(LOGGER.isDebugEnabled() ? "After Connote Service Call: createConnoteChaos()" : null);

        chaosMonkey();

        return pjp.proceed();
    }

    private void chaosMonkey() {
        if (chaosMonkey.get()) {
            // Trouble?
            int troubleRand = RandomUtils.nextInt(0, 10);
            int exceptionRand = RandomUtils.nextInt(0, 10);

            if (troubleRand > chaosMonkeyLevel.get()) {
                LOGGER.debug("Chaos Monkey - generates trouble");
                // Timeout or Exception?
                if (exceptionRand < 7) {
                    LOGGER.debug("Chaos Monkey - timeout");
                    // Timeout
                    generateTimeout();
                } else {
                    LOGGER.debug("Chaos Monkey - exception");
                    // Exception
                    throw new RuntimeException("Chaos Monkey - RuntimeException");
                }
            }
        }
    }

    /***
     * Generates a timeout exception, 3000ms
     */
    private void generateTimeout() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // do nothing, hystrix tries to interrupt
        }
    }

}
