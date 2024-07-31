package com.baeldung.subflows.postgresqlnotify;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import com.baeldung.domain.Order;
import com.baeldung.domain.OrderType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@EnableIntegration
@IntegrationComponentScan
@PropertySource(value = "classpath:/database.properties", ignoreResourceNotFound = false)
public class PostgresqlPubSubExample {
    
    private static final Logger log = LoggerFactory.getLogger(PostgresqlPubSubExample.class);
    
    private Map<String,BigDecimal> orderSummary = new HashMap<>();
    
    private final ObjectMapper om = new ObjectMapper();
    private final Semaphore orderSemaphore = new Semaphore(0);
    
    
    @MessagingGateway
    public interface OrdersGateway {
        
        @Gateway(requestChannel = "orders")
        void publish(Order order);
    }
    
    
    @Bean
    static SubscribableChannel orders(@Value("${db.url}") String url,@Value("${db.username}")  String username, @Value("${db.password}")String password) {
        
        // Connection supplier
        SingleConnectionDataSource ds = new SingleConnectionDataSource(url, username, password, true);      
        Supplier<Connection> connectionSupplier = () -> {
            try {
                return ds.getConnection();
            }
            catch(SQLException ex) {
                throw new RuntimeException(ex);
            }
        };
        
        // DataSource
        PGSimpleDataSource pgds = new PGSimpleDataSource();
        pgds.setUrl(url);
        pgds.setUser(username);
        pgds.setPassword(password);
        
        return new PostgresSubscribableChannel("orders", connectionSupplier, pgds, new ObjectMapper());
    }
    
    @Transformer(inputChannel = "orders" , outputChannel = "orderProcessor" )
    Order validatedOrders(Message<?> orderMessage)  throws JsonProcessingException {
        ObjectNode on = (ObjectNode)orderMessage.getPayload();
        Order order = om.treeToValue(on, Order.class);
        return order;
    }
    
    
    @ServiceActivator(inputChannel = "orderProcessor")
    void processOrder(Order order){
        
        log.info("Processing order: id={}, symbol={}, qty={}, price={}",
           order.getId(),
           order.getSymbol(),
           order.getQuantity(),
           order.getPrice());
        
        BigDecimal orderTotal = order.getQuantity().multiply(order.getPrice());        
        if ( order.getOrderType() == OrderType.SELL) {
            orderTotal = orderTotal.negate();
        }
        
        BigDecimal sum = orderSummary.get(order.getSymbol());
        if ( sum == null) {
            sum = orderTotal;
        }
        else {
            sum = sum.add(orderTotal);
        }
        
        orderSummary.put(order.getSymbol(), sum);
        orderSemaphore.release();
        
    }
    
    
    public BigDecimal getTotalBySymbol(String symbol) {
        return orderSummary.get(symbol);
    }
    
    public boolean awaitNextMessage(long time, TimeUnit unit) throws InterruptedException {    
        return orderSemaphore.tryAcquire(time, unit);
    }

}
