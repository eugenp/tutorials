/**
  This class demonstrates on modifying the loaded xml configuration by
  extending XMLConfigurationFactory as defined in section 4.4 of 
  "Programmatic Configuration with Log4j 2"
**/
package com.baeldung.log4j2.config;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.xml.XmlConfigurationFactory;

@Plugin(name = "CustomConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(50)
public class CustomXMLConfigurationFactory extends XmlConfigurationFactory {

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
        return new MyXMLConfiguration(loggerContext, source);
    }

    @Override
    public String[] getSupportedTypes() {
        return new String[] { ".xml", "*" };
    }
}
