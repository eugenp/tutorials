package com.baeldung.log4j2.plugins;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name = "MyConsoleAppender", category = "Core", elementType = "appender")
public final class MyConsoleAppenderImpl extends AbstractAppender {

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();

    public MyConsoleAppenderImpl(String name, Filter filter, Layout<? extends Serializable> layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    public void append(LogEvent event) {
        readLock.lock();
        try {
            final byte[] bytes = getLayout().toByteArray(event);
            System.out.write(bytes);
        } catch (Exception ex) {
            if (!ignoreExceptions()) {
                throw new AppenderLoggingException(ex);
            }
        } finally {
            readLock.unlock();
        }
    }

    @PluginFactory
    public static MyConsoleAppenderImpl createAppender(@PluginAttribute("name") String name, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filter") final Filter filter,
        @PluginAttribute("otherAttribute") String otherAttribute) {
        if (name == null) {
            LOGGER.error("No name provided for MyConsoleAppenderImpl");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new MyConsoleAppenderImpl(name, filter, layout, true);
    }
}
