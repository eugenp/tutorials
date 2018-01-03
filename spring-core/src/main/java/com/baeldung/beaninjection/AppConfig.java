package com.baeldung.beaninjection;

import com.baeldung.beaninjection.domain.AlloyWheel;
import com.baeldung.beaninjection.domain.Audio;
import com.baeldung.beaninjection.domain.Engine;
import com.baeldung.beaninjection.domain.InlineFourEngine;
import com.baeldung.beaninjection.domain.SonyAudio;
import com.baeldung.beaninjection.domain.Wheel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "com.baeldung.beaninjection.domain",
        "com.baeldung.beaninjection.service"
})
public class AppConfig {

    @Bean
    public Engine selectEngine() {
        return new InlineFourEngine();
    }

    @Bean
    public Wheel pickWheel() {
        Wheel wheel = new AlloyWheel();
        wheel.setWidth(150);
        return wheel;
    }

    @Bean
    public Audio setupAudio() {
        return new SonyAudio(true);
    }

}
