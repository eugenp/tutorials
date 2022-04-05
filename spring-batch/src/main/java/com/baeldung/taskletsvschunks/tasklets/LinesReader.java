package com.baeldung.taskletsvschunks.tasklets;

import com.baeldung.taskletsvschunks.model.Line;
import com.baeldung.taskletsvschunks.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.List;

public class LinesReader implements Tasklet, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(LinesReader.class);

    private List<Line> lines;
    private FileUtils fu;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        lines = new ArrayList<Line>();
        fu = new FileUtils("taskletsvschunks/input/tasklets-vs-chunks.csv");
        logger.debug("Lines Reader initialized.");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Line line = fu.readLine();
        while (line != null) {
            lines.add(line);
            logger.debug("Read line: " + line.toString());
            line = fu.readLine();
        }
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        fu.closeReader();
        stepExecution
          .getJobExecution()
          .getExecutionContext()
          .put("lines", this.lines);
        logger.debug("Lines Reader ended.");
        return ExitStatus.COMPLETED;
    }
}
