package com.baeldung.batch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.baeldung.batch.model.Book;
import com.baeldung.batch.model.BookDetails;
import com.baeldung.batch.model.BookRecord;

@RunWith(SpringRunner.class)
@SpringBatchTest
@ContextConfiguration(classes = {SpringBatchApplication.class/*, BatchTestConfiguration.class*/})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class })
//@TestPropertySource("classpath:application-test.properties")
public class SpringBatchStepIntegrationTest {
    @Autowired
    private JsonFileItemWriter<Book> jsonItemWriter;

    @Autowired
    private ListItemWriter<BookDetails> listItemWriter;

    @Autowired
    private FlatFileItemReader<BookRecord> itemReader;

    @Test
    public void givenMockedStep_whenReaderCalled_thenSuccess() throws Exception {
        Properties properties = System.getProperties();
        properties.setProperty("file.input", "src/test/resources/test-input-one.csv");
        StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution("step1", 1l);
        // stepExecution.getExecutionContext()
        // .put("file.input", new FileSystemResource("src/test/resources/test-input-one.csv"));
        StepScopeTestUtils.doInStepScope(stepExecution, () -> {
            BookRecord read;
            // itemReader.setResource((FileSystemResource) stepExecution.getExecutionContext()
            // .get("file.input"));
            itemReader.open(stepExecution.getExecutionContext());
            while ((read = itemReader.read()) != null) {
                assertThat(read.getBookName(), is("Foundation"));
            }
            itemReader.close();
            return null;
        });
    }
}
