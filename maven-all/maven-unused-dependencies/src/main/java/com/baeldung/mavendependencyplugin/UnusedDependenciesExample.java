package com.baeldung.mavendependencyplugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnusedDependenciesExample {

    /**
     * When the Maven dependency analyzer analyzes the code, it will see that the slf4j dependency is being used in this method. 
     * 
     * @return the slf4j {@link Logger}.
     */
    public Logger getLogger() {
        return LoggerFactory.getLogger(UnusedDependenciesExample.class);
    }

}
