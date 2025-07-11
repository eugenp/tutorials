package com.baeldung.kafka.partitions;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.kafka.processor.AtLeastOnceProcessor;
import com.baeldung.kafka.processor.ExactlyOnceProcessor;
import com.baeldung.kafka.producer.ECommerceProducer;
import com.baeldung.kafka.services.ConsumerGroupDemo;
import com.baeldung.kafka.services.RebalanceDemo;

public class KafkaPartitionsDemo {

    private static final Logger logger = LoggerFactory.getLogger(KafkaPartitionsDemo.class);

    public static void main(String[] args) {
        logger.info("=== Kafka Partitions Demo - Article Examples ===");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        demonstrateKeyBasedPartitioning();
                        break;
                    case 2:
                        demonstrateStickyPartitioning();
                        break;
                    case 3:
                        demonstrateOrderingGuarantees();
                        break;
                    case 4:
                        demonstrateConsumerGroups();
                        break;
                    case 5:
                        demonstrateRebalancing();
                        break;
                    case 6:
                        demonstrateAtLeastOnceSemantics();
                        break;
                    case 7:
                        demonstrateExactlyOnceSemantics();
                        break;
                    case 0:
                        logger.info("Exiting demo...");
                        System.exit(0);
                        break;
                    default:
                        logger.warn("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                logger.error("Error running demo: {}", e.getMessage(), e);
            }

            logger.info("\nPress Enter to continue...");
            scanner.nextLine();
            scanner.nextLine();
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Article Examples ===");
        System.out.println("1. Key-based Partitioning (Section 2.1)");
        System.out.println("2. Sticky Partitioning (Section 2.2)");
        System.out.println("3. Ordering Guarantees (Section 3)");
        System.out.println("4. Consumer Groups (Section 4)");
        System.out.println("5. Rebalancing (Section 5)");
        System.out.println("6. At-Least-Once Semantics (Section 6.1)");
        System.out.println("7. Exactly-Once Semantics (Section 6.2)");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void demonstrateKeyBasedPartitioning() {
        logger.info("=== Section 2.1: Key-based Partitioning ===");
        ECommerceProducer producer = new ECommerceProducer();
        producer.processCustomerEvents();
        producer.close();
    }

    private static void demonstrateStickyPartitioning() {
        logger.info("=== Section 2.2: Sticky Partitioning ===");
        ECommerceProducer producer = new ECommerceProducer();
        producer.demonstrateStickyPartitioning();
        producer.close();
    }

    private static void demonstrateOrderingGuarantees() {
        logger.info("=== Section 3: Ordering Guarantees ===");
        ECommerceProducer producer = new ECommerceProducer();

        logger.info("Demonstrating within-partition ordering...");
        producer.processOrderLifecycle();

        logger.info("Demonstrating cross-partition ordering challenges...");
        producer.demonstrateComplexEventFlow();

        producer.close();
    }

    private static void demonstrateConsumerGroups() {
        logger.info("=== Section 4: Consumer Groups ===");
        ConsumerGroupDemo demo = new ConsumerGroupDemo();
        demo.startMultipleGroups();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread()
                .interrupt();
        }

        demo.shutdown();
    }

    private static void demonstrateRebalancing() {
        logger.info("=== Section 5: Rebalancing ===");
        RebalanceDemo demo = new RebalanceDemo();

        for (int i = 0; i < 3; i++) {
            final int consumerId = i;
            Thread consumerThread = new Thread(() -> demo.runCooperativeConsumer("cooperative-consumer-" + consumerId));
            consumerThread.start();
        }

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread()
                .interrupt();
        }

        demo.shutdown();
    }

    private static void demonstrateAtLeastOnceSemantics() {
        logger.info("=== Section 6.1: At-Least-Once Semantics ===");

        ECommerceProducer producer = new ECommerceProducer();
        producer.processOrderLifecycle();
        producer.close();

        AtLeastOnceProcessor processor = new AtLeastOnceProcessor();
        Thread processorThread = new Thread(processor::processOrdersWithGuarantees);
        processorThread.start();

        try {
            Thread.sleep(10000);
            processorThread.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread()
                .interrupt();
        }
    }

    private static void demonstrateExactlyOnceSemantics() {
        logger.info("=== Section 6.2: Exactly-Once Semantics ===");
        ECommerceProducer producer = new ECommerceProducer();
        producer.processOrderLifecycle();
        producer.close();

        ExactlyOnceProcessor processor = new ExactlyOnceProcessor();
        Thread processorThread = new Thread(processor::processOrdersExactlyOnce);
        processorThread.start();

        try {
            Thread.sleep(10000);
            processorThread.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread()
                .interrupt();
        }
    }
}