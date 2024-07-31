package com.baeldung.springaop.unittest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ExecutionTimeAspectIntegrationTest {

    @Autowired
    private ArraySorting arraySorting;

    private List<Integer> getRandomNumberList(int size) {
        List<Integer> numberList = new ArrayList<>();
        for (int n=0;n<size;n++) {
            numberList.add((int) Math.round(Math.random() * size));
        }
        return numberList;
    }

    @Test
    void whenSort_thenExecutionTimeIsPrinted() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(baos));

        arraySorting.sort(getRandomNumberList(10000));

        System.setOut(originalSystemOut);
        String logOutput = baos.toString();
        assertThat(logOutput).contains("Execution time=");
    }

}