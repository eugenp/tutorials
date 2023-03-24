package com.baeldung.reinitializebean.controller;

import com.baeldung.reinitializebean.cache.ConfigManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${config.file.path}")
    private String filePath;
    private final ApplicationContext applicationContext;
    private final ConfigManager configManager;

    public ConfigController(ApplicationContext applicationContext, ConfigManager configManager) {
        this.applicationContext = applicationContext;
        this.configManager = configManager;
    }

    @GetMapping("/reinitializeConfig")
    public void reinitializeConfig() {
        configManager.reinitializeConfig();
    }

    @GetMapping("/reinitializeBean")
    public void reinitializeBean() {
        DefaultSingletonBeanRegistry registry = (DefaultSingletonBeanRegistry) applicationContext.getAutowireCapableBeanFactory();
        registry.destroySingleton("ConfigManager");
        registry.registerSingleton("ConfigManager", new ConfigManager(filePath));
    }

    @GetMapping("/destroyBean")
    public void destroyBean() {
        DefaultSingletonBeanRegistry registry = (DefaultSingletonBeanRegistry) applicationContext.getAutowireCapableBeanFactory();
        registry.destroySingleton("ConfigManager");
    }

    @GetMapping("/{key}")
    public Object get(@PathVariable String key) {
        return configManager.getConfig(key);
    }

    @GetMapping("/context/{key}")
    public Object getFromContext(@PathVariable String key) {
        ConfigManager dynamicConfigManager = applicationContext.getBean(ConfigManager.class);
        return dynamicConfigManager.getConfig(key);
    }

}
