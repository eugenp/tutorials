package com.baeldung.spring.core.di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StockMain {

    public static void main(String[] args) {
        System.out.println("Loading beans from annotations....");
        initializeAnnotationConfiguration();
        System.out.println("Loading beans from XML configuration....");
        initializeXMLConfiguration();
    }

    public static void initializeAnnotationConfiguration() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        StockService stockService = context.getBean(StockService.class);
        StockController stockController = context.getBean(StockController.class);
        StockConfig stockConfig = context.getBean(StockConfig.class);
        StockConfigBySetter stockConfigBySetter = context.getBean(StockConfigBySetter.class);
        stockConfig.displayStockConfig();
        stockConfigBySetter.displayStockConfig();
        stockService.addStock(new Stock(1, "Engine Oil", 10, 98.1f));
        stockController.displayStockDetails(1);
    }

    public static void initializeXMLConfiguration() {
        ApplicationContext context = new ClassPathXmlApplicationContext("injectiontypes.xml");
        StockService stockService = context.getBean(StockService.class);
        StockController stockController = context.getBean(StockController.class);
        StockConfig stockConfigByType = context.getBean("stockConfigByType", StockConfig.class);
        stockConfigByType.displayStockConfig();
        StockConfig stockConfigByIndex = context.getBean("stockConfigByIndex", StockConfig.class);
        stockConfigByIndex.displayStockConfig();
        StockConfig stockConfigByName = context.getBean("stockConfigByName", StockConfig.class);
        stockConfigByName.displayStockConfig();
        StockConfigBySetter stockConfigBySetter = context.getBean(StockConfigBySetter.class);
        stockConfigBySetter.displayStockConfig();
        stockService.addStock(new Stock(1, "Engine Oil", 10, 98.1f));
        stockController.displayStockDetails(1);
    }
}
