package com.baeldung.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.filters.SimplePatternFileListFilter;
import org.springframework.integration.file.transformer.FileToStringTransformer;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.transaction.DefaultTransactionSynchronizationFactory;
import org.springframework.integration.transaction.ExpressionEvaluatingTransactionSynchronizationProcessor;
import org.springframework.integration.transaction.TransactionInterceptorBuilder;
import org.springframework.integration.transaction.TransactionSynchronizationFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.messaging.MessageChannel;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.io.File;
import java.util.Scanner;

@Configuration
@EnableIntegration
public class TxIntegrationConfig {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public final String INPUT_DIR = "/tmp/tx/";
    public final String FILE_PATTERN = "*.txt";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private TransactionSynchronizationFactory transactionSynchronizationFactory;

    @Autowired
    DataSourceTransactionManager txManager;

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    @InboundChannelAdapter(value = "inputChannel", poller = @Poller(value = "pollerMetadata"))
    public MessageSource<File> fileReadingMessageSource() {
        FileReadingMessageSource sourceReader = new FileReadingMessageSource();
        sourceReader.setDirectory(new File(INPUT_DIR));
        sourceReader.setFilter(new SimplePatternFileListFilter(FILE_PATTERN));
        return sourceReader;
    }

    @Bean
    public PollerMetadata pollerMetadata() {
        return Pollers
                .fixedDelay(5000)
                .advice(transactionInterceptor())
                .transactionSynchronizationFactory(transactionSynchronizationFactory)
                .get();
    }

    private TransactionInterceptor transactionInterceptor() {
        return new TransactionInterceptorBuilder()
                .transactionManager(txManager)
                .build();
    }

    @Bean
    public TransactionSynchronizationFactory transactionSynchronizationFactory(){
        ExpressionEvaluatingTransactionSynchronizationProcessor transactionSynchronizationProcessor =
            new ExpressionEvaluatingTransactionSynchronizationProcessor();
        transactionSynchronizationProcessor.setAfterCommitExpression(
            new LiteralExpression("payload.renameTo(new java.io.File(payload.absolutePath + '.PASSED'))"));
        transactionSynchronizationProcessor.setAfterRollbackExpression(
            new LiteralExpression("payload.renameTo(new java.io.File(payload.absolutePath + '.FAILED'))"));
        return new DefaultTransactionSynchronizationFactory(transactionSynchronizationProcessor);
    }

    @Bean
    @Transformer(inputChannel = "inputChannel", outputChannel = "toServiceChannel")
    public FileToStringTransformer fileToStringTransformer() {
        return new FileToStringTransformer();
    }

    @ServiceActivator(inputChannel = "toServiceChannel")
    public void serviceActivator(String payload) {

        jdbcTemplate.update("insert into STUDENT values(?)", payload);

        if (payload.toLowerCase().startsWith("fail")) {
            log.error("Service failure. Test result: {} ", payload);
            throw new RuntimeException("Service failure.");
        }

        log.info("Service success. Test result: {}", payload);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:table.sql")
                .build();
    }

    @Bean
    public DataSourceTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    public static void main(final String... args) {
        final AbstractApplicationContext context = new AnnotationConfigApplicationContext(TxIntegrationConfig.class);
        context.registerShutdownHook();

        final Scanner scanner = new Scanner(System.in);
        System.out.print("Integration flow is running. Type q + <enter> to quit ");
        while (true) {
            final String input = scanner.nextLine();
            if ("q".equals(input.trim())) {
                context.close();
                scanner.close();
                break;
            }
        }
        System.exit(0);
    }

}
    


