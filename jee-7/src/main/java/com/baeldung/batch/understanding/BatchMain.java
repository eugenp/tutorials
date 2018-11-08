package com.baeldung.batch.understanding;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;

public class BatchMain {
    public static void main(String[] args) throws Exception {
        runningSimpleBatchLet();

    }

    public static void runningSimpleBatchLet() throws Exception {
        try {
            JobOperator jo = BatchRuntime.getJobOperator();
            long id = jo.start("simplebatchlet", null);
            System.out.println("Batchlet submitted: " + id);
            Thread.sleep(5000);

        } catch (Exception ex) {
            System.out.println("Error submitting Job! " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
