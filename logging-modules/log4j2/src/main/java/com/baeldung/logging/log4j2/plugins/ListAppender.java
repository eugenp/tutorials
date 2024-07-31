/**
 *
 */
package com.baeldung.logging.log4j2.plugins;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.synchronizedList;

@Plugin(name = "ListAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class ListAppender extends AbstractAppender {

    private List<LogEvent> logList;

    protected ListAppender(String name, Filter filter) {
        super(name, filter, null);
        logList = synchronizedList(new ArrayList<>());
    }

    @PluginFactory
    public static ListAppender createAppender(@PluginAttribute("name") String name, @PluginElement("Filter") final Filter filter) {
        return new ListAppender(name, filter);
    }

    @Override
    public void append(LogEvent event) {
        if (event.getLevel()
                .isLessSpecificThan(Level.WARN)) {
            error("Unable to log less than WARN level.");
            return;
        }
        logList.add(event);
    }

}
