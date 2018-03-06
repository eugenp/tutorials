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


package org.apache.catalina.startup;


import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.catalina.Globals;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.ExceptionUtils;


/**
 * <p>General purpose wrapper for command line tools that should execute in an
 * environment with the common class loader environment set up by Catalina.
 * This should be executed from a command line script that conforms to
 * the following requirements:</p>
 * <ul>
 * <li>Passes the <code>catalina.home</code> system property configured with
 *     the pathname of the Tomcat installation directory.</li>
 * <li>Sets the system classpath to include <code>bootstrap.jar</code> and
 *     <code>$JAVA_HOME/lib/tools.jar</code>.</li>
 * </ul>
 *
 * <p>The command line to execute the tool looks like:</p>
 * <pre>
 *   java -classpath $CLASSPATH org.apache.catalina.startup.Tool \
 *     ${options} ${classname} ${arguments}
 * </pre>
 *
 * <p>with the following replacement contents:
 * <ul>
 * <li><strong>${options}</strong> - Command line options for this Tool wrapper.
 *     The following options are supported:
 *     <ul>
 *     <li><em>-ant</em> : Set the <code>ant.home</code> system property
 *         to corresponding to the value of <code>catalina.home</code>
 *         (useful when your command line tool runs Ant).</li>
 *     <li><em>-common</em> : Add <code>common/classes</code> and
 *         <code>common/lib</codE) to the class loader repositories.</li>
 *     <li><em>-server</em> : Add <code>server/classes</code> and
 *         <code>server/lib</code> to the class loader repositories.</li>
 *     <li><em>-shared</em> : Add <code>shared/classes</code> and
 *         <code>shared/lib</code> to the class loader repositories.</li>
 *     </ul>
 * <li><strong>${classname}</strong> - Fully qualified Java class name of the
 *     application's main class.</li>
 * <li><strong>${arguments}</strong> - Command line arguments to be passed to
 *     the application's <code>main()</code> method.</li>
 * </ul>
 *
 * @author Craig R. McClanahan
 */
public final class Tool {


    private static final Log log = LogFactory.getLog(Tool.class);
    
    // ------------------------------------------------------- Static Variables


    /**
     * Set <code>ant.home</code> system property?
     */
    private static boolean ant = false;


    /**
     * The pathname of our installation base directory.
     */
    private static String catalinaHome = System.getProperty(Globals.CATALINA_HOME_PROP);


    /**
     * Include common classes in the repositories?
     */
    private static boolean common = false;


    /**
     * Include server classes in the repositories?
     */
    private static boolean server = false;


    /**
     * Include shared classes in the repositories?
     */
    private static boolean shared = false;


    // ----------------------------------------------------------- Main Program


    /**
     * The main program for the bootstrap.
     *
     * @param args Command line arguments to be processed
     */
    @SuppressWarnings("null")
    public static void main(String args[]) {

        // Verify that "catalina.home" was passed.
        if (catalinaHome == null) {
            log.error("Must set '" + Globals.CATALINA_HOME_PROP + "' system property");
            System.exit(1);
        }

        // Process command line options
        int index = 0;
        while (true) {
            if (index == args.length) {
                usage();
                System.exit(1);
            }
            if ("-ant".equals(args[index]))
                ant = true;
            else if ("-common".equals(args[index]))
                common = true;
            else if ("-server".equals(args[index]))
                server = true;
            else if ("-shared".equals(args[index]))
                shared = true;
            else
                break;
            index++;
        }
        if (index > args.length) {
            usage();
            System.exit(1);
        }

        // Set "ant.home" if requested
        if (ant)
            System.setProperty("ant.home", catalinaHome);

        // Construct the class loader we will be using
        ClassLoader classLoader = null;
        try {
            ArrayList<File> packed = new ArrayList<File>();
            ArrayList<File> unpacked = new ArrayList<File>();
            unpacked.add(new File(catalinaHome, "classes"));
            packed.add(new File(catalinaHome, "lib"));
            if (common) {
                unpacked.add(new File(catalinaHome,
                                      "common" + File.separator + "classes"));
                packed.add(new File(catalinaHome,
                                    "common" + File.separator + "lib"));
            }
            if (server) {
                unpacked.add(new File(catalinaHome,
                                      "server" + File.separator + "classes"));
                packed.add(new File(catalinaHome,
                                    "server" + File.separator + "lib"));
            }
            if (shared) {
                unpacked.add(new File(catalinaHome,
                                      "shared" + File.separator + "classes"));
                packed.add(new File(catalinaHome,
                                    "shared" + File.separator + "lib"));
            }
            classLoader =
                ClassLoaderFactory.createClassLoader
                (unpacked.toArray(new File[0]),
                 packed.toArray(new File[0]),
                 null);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            log.error("Class loader creation threw exception", t);
            System.exit(1);
        }
        Thread.currentThread().setContextClassLoader(classLoader);

        // Load our application class
        Class<?> clazz = null;
        String className = args[index++];
        try {
            if (log.isDebugEnabled())
                log.debug("Loading application class " + className);
            clazz = classLoader.loadClass(className);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            log.error("Exception creating instance of " + className, t);
            System.exit(1);
        }

        Method method = null;
        String params[] = new String[args.length - index];
        System.arraycopy(args, index, params, 0, params.length);
        try {
            if (log.isDebugEnabled())
                log.debug("Identifying main() method");
            String methodName = "main";
            Class<?> paramTypes[] = new Class[1];
            paramTypes[0] = params.getClass();
            method = clazz.getMethod(methodName, paramTypes);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            log.error("Exception locating main() method", t);
            System.exit(1);
        }

        // Invoke the main method of the application class
        try {
            if (log.isDebugEnabled())
                log.debug("Calling main() method");
            Object paramValues[] = new Object[1];
            paramValues[0] = params;
            method.invoke(null, paramValues);
        } catch (Throwable t) {
            t = ExceptionUtils.unwrapInvocationTargetException(t);
            ExceptionUtils.handleThrowable(t);
            log.error("Exception calling main() method", t);
            System.exit(1);
        }

    }


    /**
     * Display usage information about this tool.
     */
    private static void usage() {

        log.info("Usage:  java org.apache.catalina.startup.Tool [<options>] <class> [<arguments>]");

    }


}
