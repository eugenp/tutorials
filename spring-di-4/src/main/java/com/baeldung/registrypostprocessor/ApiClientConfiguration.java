package com.baeldung.registrypostprocessor;

import com.baeldung.registrypostprocessor.bean.ApiClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.List;

public class ApiClientConfiguration implements BeanDefinitionRegistryPostProcessor {
    private static final String API_CLIENT_BEAN_NAME = "apiClient_";
    List<ApiClient> clients;

    public ApiClientConfiguration(Environment environment) {
        Binder binder = Binder.get(environment);
        List<HashMap> properties = binder.bind("api.clients", Bindable.listOf(HashMap.class)).get();
        clients = properties.stream().map(client -> new ApiClient(String.valueOf(client.get("name")),
                String.valueOf(client.get("url")), String.valueOf(client.get("key")))).toList();
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        clients.forEach(client -> {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ApiClient.class);
            builder.addPropertyValue("name", client.getName());
            builder.addPropertyValue("url", client.getUrl());
            builder.addPropertyValue("key", client.getKey());
            registry.registerBeanDefinition(API_CLIENT_BEAN_NAME + client.getName(), builder.getBeanDefinition());
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
}