package com.baeldung.differenttypesdi.explicit;

import com.baeldung.differenttypesdi.explicit.domain.ControlSignalReceptor;
import com.baeldung.differenttypesdi.explicit.domain.RemoteControl;
import com.baeldung.differenttypesdi.explicit.domain.Television;
import com.baeldung.differenttypesdi.shared.Control;
import com.baeldung.differenttypesdi.shared.Processor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExplicitJavaConfig {

    @Bean
    public Television television() {
        return new Television(processor(), controlSignalReceptor());
    }

    @Bean
    public Control remoteControl() {
        return new RemoteControl();
    }

    @Bean
    public ControlSignalReceptor controlSignalReceptor() {
        return new ControlSignalReceptor(processor());
    }

    @Bean
    public Processor processor() {
        return new Processor(2);
    }

}
