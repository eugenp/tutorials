package org.baeldung.conditionalflow.step;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class NotifierTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.err.println("[" + chunkContext.getStepContext()
            .getJobName() + "] contains interesting data!!");
        return RepeatStatus.FINISHED;
    }
}
