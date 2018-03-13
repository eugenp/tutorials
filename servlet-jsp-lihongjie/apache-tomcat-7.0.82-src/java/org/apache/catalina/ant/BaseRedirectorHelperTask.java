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


package org.apache.catalina.ant;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Redirector;
import org.apache.tools.ant.types.RedirectorElement;


/**
 * Abstract base class to add output redirection support for Catalina
 * Ant tasks. These tasks require Ant 1.5 or later.
 * <br>
 * <strong>WARNING:</strong> due to depends chain, Ant could call a Task 
 * more than once and this can affect the output redirection when configured.
 * If you are collecting the output in a property, it will collect the output
 * of only the first run, since Ant properties are immutable and once created
 * they cannot be changed.
 * <br>
 * If you are collecting output in a file the file will be overwritten with the
 * output of the last run, unless you set append="true", in which case each run 
 * will append it's output to the file.
 * 
 *
 * @author Gabriele Garuglieri
 * @since 5.5
 */
public abstract class BaseRedirectorHelperTask extends Task {

    // ------------------------------------------------------------- Properties

    /** Redirector helper */
    protected Redirector redirector = new Redirector(this);
    //protected Redirector redirector = null;
    /** Redirector element for this task */
    protected RedirectorElement redirectorElement = null;
    /** The stream for info output */
    protected OutputStream redirectOutStream = null;
    /** The stream for error output */
    protected OutputStream redirectErrStream = null;
    /** The print stream for info output */
    PrintStream redirectOutPrintStream = null;
    /** The print stream for error output */
    PrintStream redirectErrPrintStream = null;
        
   /**
     * Whether to fail (with a BuildException) if
     * ManagerServlet returns an error. The default behavior is
     * to do so.
     * <b>
     * This flag does not control parameters checking. If the task is called
     * with wrong or invalid parameters, it will throw BuildException
     * independently from the setting of this flag.
     */    
    protected boolean failOnError = true;
    
    /** 
      * <code>true</code> true when output redirection is requested for this task .
      * Default is to log on Ant log.
      */    
    protected boolean redirectOutput = false;
 
    /** 
      * will be set to <code>true</code> when the configuration of the Redirector is
      * complete.
      */    
    protected boolean redirectorConfigured = false;

    /** 
     * Flag which indicates that, if redirected, output should also be 
     * always sent to the log. Default is that output is sent only to
     * redirected streams.
     */
    protected boolean alwaysLog = false;

    /**
     * Whether to fail (with a BuildException) if
     * ManagerServlet returns an error.  The default behavior is
     * to do so.
     */
    public void setFailonerror(boolean fail) {
        failOnError = fail;
    }

    /**
     * Returns the value of the failOnError
     * property.
     */
    public boolean isFailOnError() {
      return failOnError;
    }
        

    /**
     * File the output of the task is redirected to.
     *
     * @param out name of the output file
     */
    public void setOutput(File out) {
        redirector.setOutput(out);
        redirectOutput = true;
    }

    /**
     * File the error output of the task is redirected to.
     *
     * @param error name of the error file
     *
     */
    public void setError(File error) {
        redirector.setError(error);
        redirectOutput = true;
    }

    /**
     * Controls whether error output is logged. This is only useful
     * when output is being redirected and error output is desired in the
     * Ant log
     *
     * @param logError if true the standard error is sent to the Ant log system
     *        and not sent to output stream.
     */
    public void setLogError(boolean logError) {
        redirector.setLogError(logError);
        redirectOutput = true;
    }

    /**
     * Property name whose value should be set to the output of
     * the task.
     *
     * @param outputProperty property name
     *
     */
    public void setOutputproperty(String outputProperty) {
        redirector.setOutputProperty(outputProperty);
        redirectOutput = true;
    }

    /**
     * Property name whose value should be set to the error of
     * the task..
     *
     * @param errorProperty property name
     *
     */
    public void setErrorProperty(String errorProperty) {
        redirector.setErrorProperty(errorProperty);
        redirectOutput = true;
    }

    /**
     * If true, append output to existing file.
     *
     * @param append if true, append output to existing file
     *
     */
    public void setAppend(boolean append) {
        redirector.setAppend(append);
        redirectOutput = true;
    }

    /**
     * If true, (error and non-error) output will be redirected
     * as specified while being sent to Ant's logging mechanism as if no
     * redirection had taken place.  Defaults to false.
     * <br>
     * Actually handled internally, with Ant 1.6.3 it will be handled by
     * the <code>Redirector</code> itself.
     * @param alwaysLog <code>boolean</code>
     */
    public void setAlwaysLog(boolean alwaysLog) {
        this.alwaysLog = alwaysLog;
        //redirector.setAlwaysLog(alwaysLog);
        redirectOutput = true;
    }

    /**
     * Whether output and error files should be created even when empty.
     * Defaults to true.
     * @param createEmptyFiles <CODE>boolean</CODE>.
     */
    public void setCreateEmptyFiles(boolean createEmptyFiles) {
        redirector.setCreateEmptyFiles(createEmptyFiles);
        redirectOutput = true;
    }

    /**
     * Add a <CODE>RedirectorElement</CODE> to this task.
     * @param redirectorElement   <CODE>RedirectorElement</CODE>.
     */
    public void addConfiguredRedirector(RedirectorElement redirectorElement) {
        if (this.redirectorElement != null) {
            throw new BuildException("Cannot have > 1 nested <redirector>s");
        } else {
            this.redirectorElement = redirectorElement;
        }
    }

    /**
     * Set up properties on the Redirector from RedirectorElement if present.
     */
    private void configureRedirector() {
        if (redirectorElement != null) {
            redirectorElement.configure(redirector);
            redirectOutput = true;
        }
        /*
         * Due to depends chain, Ant could call the Task more than once,
         * this is to prevent that we attempt to configure uselessly
         * more than once the Redirector.
         */
        redirectorConfigured = true;
    }

    /**
     * Set up properties on the Redirector and create output streams.
     */
    protected void openRedirector() {
        if (! redirectorConfigured) {
            configureRedirector();
        }
        if (redirectOutput) {
            redirector.createStreams();
            redirectOutStream = redirector.getOutputStream();
            redirectOutPrintStream = new PrintStream(redirectOutStream);
            redirectErrStream = redirector.getErrorStream();
            redirectErrPrintStream = new PrintStream(redirectErrStream);
        }
   }

    /**
     * Ask redirector to close all the streams. It is necessary to call this method
     * before leaving the Task to have the Streams flush their contents. If you are 
     * collecting output in a property, it will be created only if this method is
     * called, otherwise you'll find it unset.
     */
    protected void closeRedirector() {
        try {
            if (redirectOutput && redirectOutPrintStream != null) {
                redirector.complete();
            }
        } catch (IOException ioe) {
            log("Error closing redirector: "
                + ioe.getMessage(), Project.MSG_ERR);
        }
        /*
         * Due to depends chain, Ant could call the Task more than once,
         * this is to prevent that we attempt to reuse the previously 
         * closed Streams.
         */
        redirectOutStream = null;
        redirectOutPrintStream = null;
        redirectErrStream = null;
        redirectErrPrintStream = null;
    }
    
    /**
     * Handles output with the INFO priority.
     *
     * @param output The output to log. Should not be <code>null</code>.
     */
    @Override
    protected void handleOutput(String output) {
        if (redirectOutput) {
            if (redirectOutPrintStream == null) {
                openRedirector();
            }
            redirectOutPrintStream.println(output);
            if (alwaysLog) {
                log(output, Project.MSG_INFO);
            }
        } else { 
            log(output, Project.MSG_INFO);
        }
    }

    /**
     * Handles output with the INFO priority and flushes the stream.
     *
     * @param output The output to log. Should not be <code>null</code>.
     *
     */
    @Override
    protected void handleFlush(String output) {
        handleOutput(output);
        redirectOutPrintStream.flush();
    }

    /**
     * Handles error output with the ERR priority.
     *
     * @param output The error output to log. Should not be <code>null</code>.
     */
    @Override
    protected void handleErrorOutput(String output) {
        if (redirectOutput) {
            if (redirectErrPrintStream == null) {
                openRedirector();
            }
            redirectErrPrintStream.println(output);
            if (alwaysLog) {
                log(output, Project.MSG_ERR);
            }
        } else { 
            log(output, Project.MSG_ERR);
        }
    }

    /**
     * Handles error output with the ERR priority and flushes the stream.
     *
     * @param output The error output to log. Should not be <code>null</code>.
     *
     */
    @Override
    protected void handleErrorFlush(String output) {
        handleErrorOutput(output);
        redirectErrPrintStream.flush();
    }
  
    /**
     * Handles output with ERR priority to error stream and all other
     * priorities to output stream.
     *
     * @param output The output to log. Should not be <code>null</code>.
     */
    protected void handleOutput(String output, int priority) {
        if (priority == Project.MSG_ERR) {
            handleErrorOutput(output);
        } else {
            handleOutput(output);
        }
    }
}
