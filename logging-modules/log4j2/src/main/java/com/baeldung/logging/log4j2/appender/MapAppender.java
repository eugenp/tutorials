/**
 * 
 */
package com.baeldung.logging.log4j2.appender;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "MapAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class MapAppender extends AbstractAppender {

    private ConcurrentMap<String, LogEvent> eventMap = new ConcurrentHashMap<>();

    protected MapAppender(String name, Filter filter) {
        super(name, filter, null);
    }

    @PluginFactory
    public static MapAppender createAppender(@PluginAttribute("name") String name, @PluginElement("Filter") final Filter filter) {
        return new MapAppender(name, filter);
    }

    @Override
    public void append(LogEvent event) {
        if (event.getLevel()
            .isLessSpecificThan(Level.WARN)) {
            error("Unable to log less than WARN level.");
            return;
        }
        eventMap.put(Instant.now()
            .toString(), event);
    }

    public ConcurrentMap<String, LogEvent> getEventMap() {
        return eventMap;
    }

    public void setEventMap(ConcurrentMap<String, LogEvent> eventMap) {
        this.eventMap = eventMap;
    }

}
