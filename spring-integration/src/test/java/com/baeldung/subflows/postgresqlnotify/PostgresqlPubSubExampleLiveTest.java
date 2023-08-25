package com.baeldung.subflows.postgresqlnotify;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.baeldung.domain.Order;
import com.baeldung.domain.OrderType;
import com.baeldung.subflows.postgresqlnotify.PostgresqlPubSubExample.OrdersGateway;

@SpringJUnitConfig(classes = {PostgresqlPubSubExample.class})
public class PostgresqlPubSubExampleLiveTest {
    
    @Autowired
    PostgresqlPubSubExample processor;
    
    @Autowired
    OrdersGateway ordersGateway;

    @Test
    void whenPublishOrder_thenSuccess() throws Exception{
        
        Order o = new Order(1l,"BAEL", OrderType.BUY, BigDecimal.valueOf(2.0), BigDecimal.valueOf(5.0));
        ordersGateway.publish(o);
        
        assertThat(processor.awaitNextMessage(10, TimeUnit.SECONDS)).isTrue();
        
        BigDecimal total = processor.getTotalBySymbol("BAEL");
        assertThat(total).isEqualTo(BigDecimal.valueOf(10));
    }

}
