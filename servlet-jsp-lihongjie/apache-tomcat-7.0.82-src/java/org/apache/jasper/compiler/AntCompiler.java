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

package org.apache.jasper.compiler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;

import org.apache.jasper.Constants;
import org.apache.jasper.JasperException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.PatternSet;

/**
 * Main JSP compiler class. This class uses Ant for compiling.
 *
 * @author Anil K. Vijendran
 * @author Mandar Raje
 * @author Pierre Delisle
 * @author Kin-man Chung
 * @author Remy Maucherat
 * @author Mark Roth
 */
public class AntCompiler extends Compiler {

    private final Log log = LogFactory.getLog(AntCompiler.class); // must not be static
    
    protected static final Object javacLock = new Object();

    static {
        System.setErr(new SystemLogHandler(System.err));
    }

    // ----------------------------------------------------- Instance Variables

    protected Project project = null;
    protected JasperAntLogger logger;

    // ------------------------------------------------------------ Constructor

    // Lazy eval - if we don't need to compile we probably don't need the project
    protected Project getProject() {
        
        if (project != null)
            return project;
        
        // Initializing project
        project = new Project();
        logger = new JasperAntLogger();
        logger.setOutputPrintStream(System.out);
        logger.setErrorPrintStream(System.err);
        logger.setMessageOutputLevel(Project.MSG_INFO);
        project.addBuildListener( logger);
        if (System.getProperty(Constants.CATALINA_HOME_PROP) != null) {
            project.setBasedir(System.getProperty(Constants.CATALINA_HOME_PROP));
        }
        
        if( options.getCompiler() != null ) {
            if( log.isDebugEnabled() )
                log.debug("Compiler " + options.getCompiler() );
            project.setProperty("build.compiler", options.getCompiler() );
        }
        project.init();
        return project;
    }
    
    public static class JasperAntLogger extends DefaultLogger {
        
        protected StringBuilder reportBuf = new StringBuilder();
        
        @Override
        protected void printMessage(final String message,
                final PrintStream stream,
                final int priority) {
        }
        
        @Override
        protected void log(String message) {
            reportBuf.append(message);
            reportBuf.append(Constants.NEWLINE);
        }
        
        protected String getReport() {
            String report = reportBuf.toString();
            reportBuf.setLength(0);
            return report;
        }
    }
    
    // --------------------------------------------------------- Public Methods


    /** 
     * Compile the servlet from .java file to .class file
     */
    @Override
    protected void generateClass(String[] smap)
        throws FileNotFoundException, JasperException, Exception {
        
        long t1 = 0;
        if (log.isDebugEnabled()) {
            t1 = System.currentTimeMillis();
        }

        String javaEncoding = ctxt.getOptions().getJavaEncoding();
        String javaFileName = ctxt.getServletJavaFileName();
        String classpath = ctxt.getClassPath(); 
        
        StringBuilder errorReport = new StringBuilder();
        
        StringBuilder info=new StringBuilder();
        info.append("Compile: javaFileName=" + javaFileName + "\n" );
        info.append("    classpath=" + classpath + "\n" );
        
        // Start capturing the System.err output for this thread
        SystemLogHandler.setThread();
        
        // Initializing javac task
        getProject();
        Javac javac = (Javac) project.createTask("javac");
        
        // Initializing classpath
        Path path = new Path(project);
        path.setPath(System.getProperty("java.class.path"));
        info.append("    cp=" + System.getProperty("java.class.path") + "\n");
        StringTokenizer tokenizer = new StringTokenizer(classpath, File.pathSeparator);
        while (tokenizer.hasMoreElements()) {
            String pathElement = tokenizer.nextToken();
            File repository = new File(pathElement);
            path.setLocation(repository);
            info.append("    cp=" + repository + "\n");
        }
        
        if (log.isDebugEnabled()) {
            log.debug( "Using classpath: " + System.getProperty("java.class.path") +
                    File.pathSeparator + classpath);
        }
        
        // Initializing sourcepath
        Path srcPath = new Path(project);
        srcPath.setLocation(options.getScratchDir());
        
        info.append("    work dir=" + options.getScratchDir() + "\n");
        
        // Initialize and set java extensions
        String exts = System.getProperty("java.ext.dirs");
        if (exts != null) {
            Path extdirs = new Path(project);
            extdirs.setPath(exts);
            javac.setExtdirs(extdirs);
            info.append("    extension dir=" + exts + "\n");
        }

        // Add endorsed directories if any are specified and we're forking
        // See Bugzilla 31257
        if(ctxt.getOptions().getFork()) {
            String endorsed = System.getProperty("java.endorsed.dirs");
            if(endorsed != null) {
                Javac.ImplementationSpecificArgument endorsedArg = 
                    javac.createCompilerArg();
                endorsedArg.setLine("-J-Djava.endorsed.dirs=" +
                        quotePathList(endorsed));
                info.append("    endorsed dir=" + quotePathList(endorsed) +
                        "\n");
            } else {
                info.append("    no endorsed dirs specified\n");
            }
        }
        
        // Configure the compiler object
        javac.setEncoding(javaEncoding);
        javac.setClasspath(path);
        javac.setDebug(ctxt.getOptions().getClassDebugInfo());
        javac.setSrcdir(srcPath);
        javac.setTempdir(options.getScratchDir());
        javac.setOptimize(! ctxt.getOptions().getClassDebugInfo() );
        javac.setFork(ctxt.getOptions().getFork());
        info.append("    srcDir=" + srcPath + "\n" );
        
        // Set the Java compiler to use
        if (options.getCompiler() != null) {
            javac.setCompiler(options.getCompiler());
            info.append("    compiler=" + options.getCompiler() + "\n");
        }

        if (options.getCompilerTargetVM() != null) {
            javac.setTarget(options.getCompilerTargetVM());
            info.append("   compilerTargetVM=" + options.getCompilerTargetVM() + "\n");
        }

        if (options.getCompilerSourceVM() != null) {
            javac.setSource(options.getCompilerSourceVM());
            info.append("   compilerSourceVM=" + options.getCompilerSourceVM() + "\n");
        }
        
        // Build includes path
        PatternSet.NameEntry includes = javac.createInclude();
        
        includes.setName(ctxt.getJavaPath());
        info.append("    include="+ ctxt.getJavaPath() + "\n" );
        
        BuildException be = null;
        
        try {
            if (ctxt.getOptions().getFork()) {
                javac.execute();
            } else {
                synchronized(javacLock) {
                    javac.execute();
                }
            }
        } catch (BuildException e) {
            be = e;
            log.error(Localizer.getMessage("jsp.error.javac"), e);
            log.error(Localizer.getMessage("jsp.error.javac.env") + info.toString());
        }
        
        errorReport.append(logger.getReport());

        // Stop capturing the System.err output for this thread
        String errorCapture = SystemLogHandler.unsetThread();
        if (errorCapture != null) {
            errorReport.append(Constants.NEWLINE);
            errorReport.append(errorCapture);
        }

        if (!ctxt.keepGenerated()) {
            File javaFile = new File(javaFileName);
            javaFile.delete();
        }
        
        if (be != null) {
            String errorReportString = errorReport.toString();
            log.error(Localizer.getMessage("jsp.error.compilation", javaFileName, errorReportString));
            JavacErrorDetail[] javacErrors = ErrorDispatcher.parseJavacErrors(
                    errorReportString, javaFileName, pageNodes);
            if (javacErrors != null) {
                errDispatcher.javacError(javacErrors);
            } else {
                errDispatcher.javacError(errorReportString, be);
            }
        }
        
        if( log.isDebugEnabled() ) {
            long t2 = System.currentTimeMillis();
            log.debug("Compiled " + ctxt.getServletJavaFileName() + " "
                      + (t2-t1) + "ms");
        }
        
        logger = null;
        project = null;
        
        if (ctxt.isPrototypeMode()) {
            return;
        }
        
        // JSR45 Support
        if (! options.isSmapSuppressed()) {
            SmapUtil.installSmap(smap);
        }
    }

    private String quotePathList(String list) {
        StringBuilder result = new StringBuilder(list.length() + 10);
        StringTokenizer st = new StringTokenizer(list, File.pathSeparator);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.indexOf(' ') == -1) {
                result.append(token);
            } else {
                result.append('\"');
                result.append(token);
                result.append('\"');
            }
            if (st.hasMoreTokens()) {
                result.append(File.pathSeparatorChar);
            }
        }
        return result.toString();
    }


    protected static class SystemLogHandler extends PrintStream {


        // ----------------------------------------------------------- Constructors


        /**
         * Construct the handler to capture the output of the given steam.
         */
        public SystemLogHandler(PrintStream wrapped) {
            super(wrapped);
            this.wrapped = wrapped;
        }


        // ----------------------------------------------------- Instance Variables


        /**
         * Wrapped PrintStream.
         */
        protected PrintStream wrapped = null;


        /**
         * Thread <-> PrintStream associations.
         */
        protected static ThreadLocal<PrintStream> streams =
            new ThreadLocal<PrintStream>();


        /**
         * Thread <-> ByteArrayOutputStream associations.
         */
        protected static ThreadLocal<ByteArrayOutputStream> data =
            new ThreadLocal<ByteArrayOutputStream>();


        // --------------------------------------------------------- Public Methods


        /**
         * @deprecated Unused. Will be removed in Tomcat 8.0.x.
         */
        @Deprecated
        public PrintStream getWrapped() {
          return wrapped;
        }

        /**
         * Start capturing thread's output.
         */
        public static void setThread() {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            data.set(baos);
            streams.set(new PrintStream(baos));
        }


        /**
         * Stop capturing thread's output and return captured data as a String.
         */
        public static String unsetThread() {
            ByteArrayOutputStream baos = data.get();
            if (baos == null) {
                return null;
            }
            streams.set(null);
            data.set(null);
            return baos.toString();
        }


        // ------------------------------------------------------ Protected Methods


        /**
         * Find PrintStream to which the output must be written to.
         */
        protected PrintStream findStream() {
            PrintStream ps = streams.get();
            if (ps == null) {
                ps = wrapped;
            }
            return ps;
        }


        // ---------------------------------------------------- PrintStream Methods


        @Override
        public void flush() {
            findStream().flush();
        }

        @Override
        public void close() {
            findStream().close();
        }

        @Override
        public boolean checkError() {
            return findStream().checkError();
        }

        @Override
        protected void setError() {
            //findStream().setError();
        }

        @Override
        public void write(int b) {
            findStream().write(b);
        }

        @Override
        public void write(byte[] b)
            throws IOException {
            findStream().write(b);
        }

        @Override
        public void write(byte[] buf, int off, int len) {
            findStream().write(buf, off, len);
        }

        @Override
        public void print(boolean b) {
            findStream().print(b);
        }

        @Override
        public void print(char c) {
            findStream().print(c);
        }

        @Override
        public void print(int i) {
            findStream().print(i);
        }

        @Override
        public void print(long l) {
            findStream().print(l);
        }

        @Override
        public void print(float f) {
            findStream().print(f);
        }

        @Override
        public void print(double d) {
            findStream().print(d);
        }

        @Override
        public void print(char[] s) {
            findStream().print(s);
        }

        @Override
        public void print(String s) {
            findStream().print(s);
        }

        @Override
        public void print(Object obj) {
            findStream().print(obj);
        }

        @Override
        public void println() {
            findStream().println();
        }

        @Override
        public void println(boolean x) {
            findStream().println(x);
        }

        @Override
        public void println(char x) {
            findStream().println(x);
        }

        @Override
        public void println(int x) {
            findStream().println(x);
        }

        @Override
        public void println(long x) {
            findStream().println(x);
        }

        @Override
        public void println(float x) {
            findStream().println(x);
        }

        @Override
        public void println(double x) {
            findStream().println(x);
        }

        @Override
        public void println(char[] x) {
            findStream().println(x);
        }

        @Override
        public void println(String x) {
            findStream().println(x);
        }

        @Override
        public void println(Object x) {
            findStream().println(x);
        }

    }

}
