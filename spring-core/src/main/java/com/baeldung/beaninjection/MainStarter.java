package com.baeldung.beaninjection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainStarter {

        public static void main(String[] args) {
                startXml();
                startAnnotation();
                startAutoDiscovery();
                startJavaConfiguration();
                startFullJavaConfiguration();
        }

        private static void startXml() {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com.baeldung.beaninjection-xml-config.xml");
                ManagedBean managedBean = applicationContext.getBean(ManagedBean.class);
        }

        private static void startAnnotation() {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com.baeldung.beaninjection-annotation-config.xml");
                AnnotationManagedBean annotationManagedBean = applicationContext.getBean(AnnotationManagedBean.class);
        }

        private static void startAutoDiscovery() {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com.baeldung.beaninjection-autodiscovery-config.xml");
                AutoDiscoveryManagedBean autoDiscoveryManagedBean = applicationContext.getBean(AutoDiscoveryManagedBean.class);
        }

        private static void startJavaConfiguration() {
                ApplicationContext applicationContext = new ClassPathXmlApplicationContext("com.baeldung.beaninjection-java-config.xml");
                ManagedBean managedBean = applicationContext.getBean(ManagedBean.class);
        }

        private static void startFullJavaConfiguration() {
                ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfiguration.class);
                ManagedBean managedBean = applicationContext.getBean(ManagedBean.class);
        }
}
