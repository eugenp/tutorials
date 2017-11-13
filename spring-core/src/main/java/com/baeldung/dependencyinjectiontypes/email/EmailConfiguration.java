package com.baeldung.dependencyinjectiontypes.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
class EmailConfiguration {

    @Bean
    EmailService emailService() {
        return new EmailService();
    }

    @Bean
    ViaConstructorEmailExample viaConstructorEmailExample(EmailService emailService){
        return new ViaConstructorEmailExample(emailService);
    }

    @Bean
    ViaSetterEmailExample viaSetterEmailExample(){
        ViaSetterEmailExample viaSetterEmailExample = new ViaSetterEmailExample();
        viaSetterEmailExample.setEmailService(emailService());
        return viaSetterEmailExample;
    }

}
