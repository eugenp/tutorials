package com.baeldung.taskletsvschunks.chunks;

import com.baeldung.taskletsvschunks.model.Line;
import com.baeldung.taskletsvschunks.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

public class LineReader implements ItemReader<Line>, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(LineReader.class);
    private FileUtils fu;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        fu = new FileUtils("taskletsvschunks/input/tasklets-vs-chunks.csv");
        logger.debug("Line Reader initialized.");
    }

    @Override
    public Line read() throws Exception {
        Line line = fu.readLine();
        if (line != null) logger.debug("Read line: " + line.toString());
        return line;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        fu.closeReader();
        logger.debug("Line Reader ended.");
        return ExitStatus.COMPLETED;
    }
}
