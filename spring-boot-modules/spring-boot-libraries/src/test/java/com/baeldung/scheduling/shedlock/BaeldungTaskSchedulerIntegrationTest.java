package com.baeldung.scheduling.shedlock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = "bucket4j.enabled=false")
public class BaeldungTaskSchedulerIntegrationTest {
    @Autowired
    private BaeldungTaskScheduler taskScheduler;

    @Test
    public void whenShedLockConfigCorrect_thenSpringCtxtStartsWithoutError() {
        // save the old out
        PrintStream old = System.out;

        // Create a stream to hold the output for test
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(consoleOutput);
        System.setOut(ps);
        //test
        taskScheduler.scheduledTask();
        System.out.flush();
        String expected = "Running ShedLock task";
        assertThat(consoleOutput.toString()).contains(expected);

        //restore the old out
        System.setOut(old);
    }

}