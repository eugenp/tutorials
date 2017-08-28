package com.baeldung.beaninjectiontypes.config;

import com.baeldung.beaninjectiontypes.beans.api.Processor;
import com.baeldung.beaninjectiontypes.beans.api.VideoGpu;
import com.baeldung.beaninjectiontypes.beans.ProcessorImpl;
import com.baeldung.beaninjectiontypes.beans.VideoGpuImpl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.beaninjectiontypes.beans")
public class SpringConfig {

    @Bean(name = "nvdiaGtx")
    @Qualifier("nvdiaGtx")
    public VideoGpu getNvdiaGtx() {
        return new VideoGpuImpl("Nvdia GTX 1080");
    }

    @Bean(name = "intelOnboardGpu")
    @Qualifier("intelOnboardGpu")
    public VideoGpu getIntelGpu() {
        return new VideoGpuImpl("Intel Onboard");
    }

    @Bean
    @Qualifier("intel")
    public Processor getProcessor() {
        //the name of the cpu, and the clock speed
        return new ProcessorImpl("Intel Core i7");
    }
}
