package org.baeldung.examples.r2dbc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;


@Configuration
public class DatasourceConfig {
    
    @Bean
    public ConnectionFactory connectionFactory(R2DBCConfigurationProperties properties) {
        
        final ConnectionFactoryOptions.Builder ob = ConnectionFactoryOptions.builder()
            .option(DRIVER, properties.getDriver());

        if ( !StringUtils.isEmpty(properties.getDatabase()) ) {
            ob.option(DATABASE, properties.getDatabase());
        }
        
        
        if ( !StringUtils.isEmpty(properties.getProtocol()) ) {
            ob.option(PROTOCOL, properties.getProtocol());
        }

        if ( !StringUtils.isEmpty(properties.getHost()) ) {
            ob.option(HOST, properties.getHost());
        }
        
        if ( properties.getPort() > 0 ) {
            ob.option(PORT, properties.getPort());
        }
        
        if ( !StringUtils.isEmpty(properties.getUser()) ) {
            ob.option(USER, properties.getUser());
        }
        
        if ( !StringUtils.isEmpty(properties.getPassword()) ) {
            ob.option(PASSWORD, properties.getPassword());
        }
        
        if ( properties.isSsl()) {
            ob.option(SSL, true);
        }
        
        if ( properties.getOptions() != null ) {
            properties.getOptions().forEach((k,v) -> {
                ob.option(Option.valueOf(k), v);
            });
        }
        
        ConnectionFactory cf = ConnectionFactories.get(ob.build());
        return cf;
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

