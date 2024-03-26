package com.baeldung.com.baeldung.continuetransactionafterexception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baeldung.continuetransactionafterexception.InvoiceRepository;
import com.baeldung.continuetransactionafterexception.InvoiceService;
import com.baeldung.continuetransactionafterexception.NotificationSendingException;
import com.baeldung.continuetransactionafterexception.model.InvoiceEntity;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
  ContinueWithTransactionAfterExceptionIntegrationConfiguration.class,
  InvoiceRepository.class, InvoiceService.class})
@DirtiesContext
@EnableTransactionManagement
class ContinueWithTransactionAfterExceptionIntegrationTest {

    @Autowired
    private InvoiceRepository repository;
    @Autowired
    private InvoiceService service;

    @Test
    void givenInvoiceService_whenExceptionOccursDuringNotificationSending_thenNoDataShouldBeSaved() {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setSerialNumber("#1");
        invoiceEntity.setDescription("First invoice");

        assertThrows(
            NotificationSendingException.class,
            () -> service.saveInvoice(invoiceEntity)
        );

        List<InvoiceEntity> entityList = repository.findAll();
        Assertions.assertTrue(entityList.isEmpty());
    }

    @Test
    void givenInvoiceRepository_whenExceptionOccursDuringBatchSavingInternally_thenNoDataShouldBeSaved() {

        List<InvoiceEntity> testEntities = new ArrayList<>();

        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setSerialNumber("#1");
        invoiceEntity.setDescription("First invoice");
        testEntities.add(invoiceEntity);

        InvoiceEntity invoiceEntity2 = new InvoiceEntity();
        invoiceEntity2.setSerialNumber("#1");
        invoiceEntity.setDescription("First invoice (duplicated)");
        testEntities.add(invoiceEntity2);

        InvoiceEntity invoiceEntity3 = new InvoiceEntity();
        invoiceEntity3.setSerialNumber("#2");
        invoiceEntity.setDescription("Second invoice");
        testEntities.add(invoiceEntity3);

        UnexpectedRollbackException exception = assertThrows(UnexpectedRollbackException.class,
          () -> repository.saveBatch(testEntities));
        assertEquals("Transaction silently rolled back because it has been marked as rollback-only",
          exception.getMessage());

        List<InvoiceEntity> entityList = repository.findAll();
        Assertions.assertTrue(entityList.isEmpty());
    }

    @Test
    void givenInvoiceService_whenNotificationSendingExceptionOccurs_thenTheInvoiceBeSaved() {
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setSerialNumber("#1");
        invoiceEntity.setDescription("We want to save this invoice anyway");

        assertThrows(
            NotificationSendingException.class,
            () -> service.saveInvoiceWithoutRollback(invoiceEntity)
        );

        List<InvoiceEntity> entityList = repository.findAll();
        Assertions.assertTrue(entityList.contains(invoiceEntity));
    }

    @Test
    void givenInvoiceRepository_whenExceptionOccursDuringBatchSavingInternally_thenDataShouldBeSavedInSeparateTransaction() {

        List<InvoiceEntity> testEntities = new ArrayList<>();

        InvoiceEntity invoiceEntity1 = new InvoiceEntity();
        invoiceEntity1.setSerialNumber("#1");
        invoiceEntity1.setDescription("First invoice");
        testEntities.add(invoiceEntity1);

        InvoiceEntity invoiceEntity2 = new InvoiceEntity();
        invoiceEntity2.setSerialNumber("#1");
        invoiceEntity1.setDescription("First invoice (duplicated)");
        testEntities.add(invoiceEntity2);

        InvoiceEntity invoiceEntity3 = new InvoiceEntity();
        invoiceEntity3.setSerialNumber("#2");
        invoiceEntity1.setDescription("Second invoice");
        testEntities.add(invoiceEntity3);

        repository.saveBatchUsingManualTransaction(testEntities);

        List<InvoiceEntity> entityList = repository.findAll();
        Assertions.assertTrue(entityList.contains(invoiceEntity1));
        Assertions.assertTrue(entityList.contains(invoiceEntity3));
    }

    @Test
    void givenInvoiceRepository_whenExceptionOccursDuringBatchSaving_thenDataShouldBeSavedUsingSaveMethod() {

        List<InvoiceEntity> testEntities = new ArrayList<>();

        InvoiceEntity invoiceEntity1 = new InvoiceEntity();
        invoiceEntity1.setSerialNumber("#1");
        invoiceEntity1.setDescription("First invoice");
        testEntities.add(invoiceEntity1);

        InvoiceEntity invoiceEntity2 = new InvoiceEntity();
        invoiceEntity2.setSerialNumber("#1");
        invoiceEntity1.setDescription("First invoice (duplicated)");
        testEntities.add(invoiceEntity2);

        InvoiceEntity invoiceEntity3 = new InvoiceEntity();
        invoiceEntity3.setSerialNumber("#2");
        invoiceEntity1.setDescription("Second invoice");
        testEntities.add(invoiceEntity3);

        try {
            repository.saveBatchOnly(testEntities);
        } catch (Exception e) {
            testEntities.forEach(t -> {
                try {
                    repository.save(t);
                } catch (Exception e2) {
                    System.err.println(e2.getMessage());
                }
            });
        }

        List<InvoiceEntity> entityList = repository.findAll();
        Assertions.assertTrue(entityList.contains(invoiceEntity1));
        Assertions.assertTrue(entityList.contains(invoiceEntity3));
    }

    @AfterEach
    void clean() {
        repository.deleteAll();
    }
}
