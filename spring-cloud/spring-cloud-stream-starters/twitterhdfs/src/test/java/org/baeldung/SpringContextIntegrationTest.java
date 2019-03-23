package org.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.twitterhdfs.aggregate.AggregateApp;
import com.baeldung.twitterhdfs.processor.ProcessorApp;
import com.baeldung.twitterhdfs.sink.SinkApp;
import com.baeldung.twitterhdfs.source.SourceApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AggregateApp.class, ProcessorApp.class, SinkApp.class, SourceApp.class})
public class SpringContextIntegrationTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}
