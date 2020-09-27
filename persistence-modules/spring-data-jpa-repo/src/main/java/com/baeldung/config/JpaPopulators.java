package com.baeldung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.data.repository.init.UnmarshallerRepositoryPopulatorFactoryBean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.baeldung.entity.Fruit;

@Configuration
public class JpaPopulators {

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator() throws Exception {
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[] { new ClassPathResource("fruit-data.json") });
        return factory;
    }

    @Bean
    public UnmarshallerRepositoryPopulatorFactoryBean repositoryPopulator() {

        Jaxb2Marshaller unmarshaller = new Jaxb2Marshaller();
        unmarshaller.setClassesToBeBound(Fruit.class);

        UnmarshallerRepositoryPopulatorFactoryBean factory = new UnmarshallerRepositoryPopulatorFactoryBean();
        factory.setUnmarshaller(unmarshaller);
        factory.setResources(new Resource[] { new ClassPathResource("apple-fruit-data.xml"), new ClassPathResource("guava-fruit-data.xml") });
        return factory;
    }

}
