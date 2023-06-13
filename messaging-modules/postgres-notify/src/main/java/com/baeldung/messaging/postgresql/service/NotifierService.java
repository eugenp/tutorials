package com.baeldung.messaging.postgresql.service;

import java.sql.Connection;
import java.util.function.Consumer;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.messaging.postgresql.domain.Order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class NotifierService {
    private static final String ORDERS_CHANNEL = "orders";
    private final JdbcTemplate tpl;
    
   
    @Transactional
    public void notifyOrderCreated(Order order) {
        tpl.execute("NOTIFY " + ORDERS_CHANNEL + ", '" + order.getId() + "'");
    }
    
    public Runnable createNotificationHandler(Consumer<PGNotification> consumer) {
        
        return () -> {
            tpl.execute((Connection c) -> {
                log.info("notificationHandler: sending LISTEN command...");
                c.createStatement().execute("LISTEN " + ORDERS_CHANNEL);
                
                PGConnection pgconn = c.unwrap(PGConnection.class); 
                
                while(!Thread.currentThread().isInterrupted()) {
                    PGNotification[] nts = pgconn.getNotifications(10000);
                    if ( nts == null || nts.length == 0 ) {
                        continue;
                    }
                    
                    for( PGNotification nt : nts) {
                        consumer.accept(nt);
                    }
                }
                
                return 0;
            });
                
        };
    }
}
