package com.baeldung.taskletsvschunks.chunks;

import com.baeldung.taskletsvschunks.model.Line;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LineProcessor implements ItemProcessor<Line, Line>, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(LineProcessor.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("Line Processor initialized.");
    }

    @Override
    public Line process(Line line) throws Exception {
        long age = ChronoUnit.YEARS.between(line.getDob(), LocalDate.now());
        logger.debug("Calculated age " + age + " for line " + line.toString());
        line.setAge(age);
        return line;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("Line Processor ended.");
        return ExitStatus.COMPLETED;
    }
}
