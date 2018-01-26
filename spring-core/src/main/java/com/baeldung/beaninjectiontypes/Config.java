package com.baeldung.beaninjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjectiontypes.domian.GraphicsCard;
import com.baeldung.beaninjectiontypes.domian.Processor;

@Configuration
@ComponentScan("com.baeldung.beaninjectiontypes")
public class Config {

    @Bean
    public Processor getProcessor() {
        return new Processor(2700, "i7");
    }

    @Bean
    public GraphicsCard getGraphicsCard() {
        return new GraphicsCard(2048, "AMD");
    }

}
