/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.juli.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * Hardcoded java.util.logging commons-logging implementation.
 * 
 * In addition, it curr 
 * 
 */
class DirectJDKLog implements Log {
    // no reason to hide this - but good reasons to not hide
    public Logger logger;
    
    /** Alternate config reader and console format 
     */
    private static final String SIMPLE_FMT="java.util.logging.SimpleFormatter";
    private static final String SIMPLE_CFG="org.apache.juli.JdkLoggerConfig"; //doesn't exist
    private static final String FORMATTER="org.apache.juli.formatter";

    static {
        if( System.getProperty("java.util.logging.config.class") ==null  &&
                System.getProperty("java.util.logging.config.file") ==null ) {
            // default configuration - it sucks. Let's override at least the 
            // formatter for the console
            try {
                Class.forName(SIMPLE_CFG).newInstance();                
            } catch( Throwable t ) {                
            }
            try {
                Formatter fmt=(Formatter)Class.forName(System.getProperty(FORMATTER, SIMPLE_FMT)).newInstance(); 
                // it is also possible that the user modified jre/lib/logging.properties - 
                // but that's really stupid in most cases
                Logger root=Logger.getLogger("");
                Handler handlers[]=root.getHandlers();
                for( int i=0; i< handlers.length; i++ ) {
                    // I only care about console - that's what's used in default config anyway
                    if( handlers[i] instanceof  ConsoleHandler ) {
                        handlers[i].setFormatter(fmt);
                    }
                }
            } catch( Throwable t ) {
                // maybe it wasn't included - the ugly default will be used.
            }
            
        }
    }
    
    public DirectJDKLog(String name ) {
        logger=Logger.getLogger(name);        
    }
    
    @Override
    public final boolean isErrorEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }
    
    @Override
    public final boolean isWarnEnabled() {
        return logger.isLoggable(Level.WARNING); 
    }
    
    @Override
    public final boolean isInfoEnabled() {
        return logger.isLoggable(Level.INFO);
    }
    
    @Override
    public final boolean isDebugEnabled() {
        return logger.isLoggable(Level.FINE);
    }
    
    @Override
    public final boolean isFatalEnabled() {
        return logger.isLoggable(Level.SEVERE);
    }
    
    @Override
    public final boolean isTraceEnabled() {
        return logger.isLoggable(Level.FINER);
    }
    
    @Override
    public final void debug(Object message) {
        log(Level.FINE, String.valueOf(message), null);
    }
    
    @Override
    public final void debug(Object message, Throwable t) {
        log(Level.FINE, String.valueOf(message), t);
    }
    
    @Override
    public final void trace(Object message) {
        log(Level.FINER, String.valueOf(message), null);
    }
    
    @Override
    public final void trace(Object message, Throwable t) {
        log(Level.FINER, String.valueOf(message), t);
    }
    
    @Override
    public final void info(Object message) {
        log(Level.INFO, String.valueOf(message), null);
    }
    
    @Override
    public final void info(Object message, Throwable t) {        
        log(Level.INFO, String.valueOf(message), t);
    }
    
    @Override
    public final void warn(Object message) {
        log(Level.WARNING, String.valueOf(message), null);
    }
    
    @Override
    public final void warn(Object message, Throwable t) {
        log(Level.WARNING, String.valueOf(message), t);
    }
    
    @Override
    public final void error(Object message) {
        log(Level.SEVERE, String.valueOf(message), null);
    }
    
    @Override
    public final void error(Object message, Throwable t) {
        log(Level.SEVERE, String.valueOf(message), t);
    }
    
    @Override
    public final void fatal(Object message) {
        log(Level.SEVERE, String.valueOf(message), null);
    }
    
    @Override
    public final void fatal(Object message, Throwable t) {
        log(Level.SEVERE, String.valueOf(message), t);
    }    

    // from commons logging. This would be my number one reason why java.util.logging
    // is bad - design by committee can be really bad ! The impact on performance of 
    // using java.util.logging - and the ugliness if you need to wrap it - is far
    // worse than the unfriendly and uncommon default format for logs. 
    
    private void log(Level level, String msg, Throwable ex) {
        if (logger.isLoggable(level)) {
            // Hack (?) to get the stack trace.
            Throwable dummyException=new Throwable();
            StackTraceElement locations[]=dummyException.getStackTrace();
            // Caller will be the third element
            String cname = "unknown";
            String method = "unknown";
            if (locations != null && locations.length >2) {
                StackTraceElement caller = locations[2];
                cname = caller.getClassName();
                method = caller.getMethodName();
            }
            if (ex==null) {
                logger.logp(level, cname, method, msg);
            } else {
                logger.logp(level, cname, method, msg, ex);
            }
        }
    }        

    // for LogFactory
    static void release() {
        
    }
    
    static Log getInstance(String name) {
        return new DirectJDKLog( name );
    }
}


