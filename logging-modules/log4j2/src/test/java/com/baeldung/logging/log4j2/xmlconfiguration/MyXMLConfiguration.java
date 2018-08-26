/**
This class demonstrates on overriding the configuration loaded through xml
as defined in section 4.4 of "Programmatic Configuration with Log4j 2"
**/

package com.baeldung.logging.log4j2.xmlconfiguration;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.xml.XmlConfiguration;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class MyXMLConfiguration extends XmlConfiguration {
  public MyXMLConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
      super(loggerContext, source);
  }

  @Override
  protected void doConfigure() {
      super.doConfigure();
      final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
      Configuration config = ctx.getConfiguration();
      LoggerConfig loggerConfig = config.getLoggerConfig("com");
      final Layout layout = PatternLayout.createLayout("[%-5level] %d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %c{1} - %msg%n", null, config, null, null, false, false, null, null);
      Appender appender = FileAppender.createAppender("target/test.log", "false", "false", "File", "true", "false", "false", "4000", layout, null, "false", null, config);
      loggerConfig.addAppender(appender, Level.DEBUG, null);
      addAppender(appender);
  }
}