package com.baeldung.examples.r2dbc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.netty.util.internal.StringUtil;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.ConnectionFactoryOptions.Builder;
import reactor.core.publisher.Flux;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;


@Configuration
public class DatasourceConfig {
    
    @Bean
    public ConnectionFactory connectionFactory(R2DBCConfigurationProperties properties) {
        
        ConnectionFactoryOptions baseOptions = ConnectionFactoryOptions.parse(properties.getUrl());
        Builder ob = ConnectionFactoryOptions.builder().from(baseOptions);
        if ( !StringUtil.isNullOrEmpty(properties.getUser())) {
            ob = ob.option(USER, properties.getUser());
        }

        if ( !StringUtil.isNullOrEmpty(properties.getPassword())) {
            ob = ob.option(PASSWORD, properties.getPassword());
        }

        return ConnectionFactories.get(ob.build());
    }
    

    @Bean
    public CommandLineRunner initDatabase(ConnectionFactory cf) {
    
        return (args) ->
          Flux.from(cf.create())
              .flatMap(c -> 
                Flux.from(c.createBatch()
                  .add("drop table if exists Account")
                  .add("create table Account(" +
                    "id IDENTITY(1,1)," +
                    "iban varchar(80) not null," +
                    "balance DECIMAL(18,2) not null)")
                  .add("insert into Account(iban,balance)" +
                    "values('BR430120980198201982',100.00)")
                  .add("insert into Account(iban,balance)" +
                    "values('BR430120998729871000',250.00)")
                  .execute())
                .doFinally((st) -> c.close())
              )
              .log()
              .blockLast();
    }


}

