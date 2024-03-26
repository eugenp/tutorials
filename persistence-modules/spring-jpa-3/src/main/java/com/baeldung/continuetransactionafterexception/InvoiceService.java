package com.baeldung.continuetransactionafterexception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.continuetransactionafterexception.model.InvoiceEntity;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository repository;
    @Transactional
    public void saveInvoice(InvoiceEntity invoice) {
        repository.save(invoice);
        sendNotification();
    }
    @Transactional(noRollbackFor = NotificationSendingException.class)
    public void saveInvoiceWithoutRollback(InvoiceEntity entity) {
        repository.save(entity);
        sendNotification();
    }

    private void sendNotification() {
        throw new NotificationSendingException("Notification sending is failed");
    }
}
