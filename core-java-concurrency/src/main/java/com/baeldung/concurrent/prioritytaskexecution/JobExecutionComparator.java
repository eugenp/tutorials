package com.baeldung.concurrent.prioritytaskexecution;

import java.util.Comparator;

public class JobExecutionComparator<T extends Job> implements Comparator<T> {

    public int compare(Job job1, Job job2) {
        return job1.getJobPriority().compareTo(job2.getJobPriority());
    }
}
