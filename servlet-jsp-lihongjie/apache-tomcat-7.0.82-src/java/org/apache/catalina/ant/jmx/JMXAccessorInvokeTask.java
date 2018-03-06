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


package org.apache.catalina.ant.jmx;


import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.apache.tools.ant.BuildException;


/**
 * Access <em>JMX</em> JSR 160 MBeans Server. 
 * <ul>
 * <li>open more then one JSR 160 rmi connection</li>
 * <li>Get/Set Mbeans attributes</li>
 * <li>Call Mbean Operation with arguments</li>
 * <li>Argument values can be converted from string to int,long,float,double,boolean,ObjectName or InetAddress </li>
 * <li>Query Mbeans</li>
 * <li>Show Get, Call, Query result at Ant console log</li>
 * <li>Bind Get, Call, Query result at Ant properties</li>
 * </ul>
 *
 * Examples:
 * <ul>
 * <li>
 * Get a session attribute hello from session with ref <em>${sessionid.0}</em> form 
 * app <em>Catalina:type=Manager,context=/ClusterTest,host=localhost</em> 
 * <pre>
 *   &lt;jmx:invoke
 *           name="Catalina:type=Manager,context=/ClusterTest,host=localhost" 
 *           operation="getSessionAttribute"
 *           resultproperty="hello"&gt;
 *         &lt;arg value="${sessionid.0}"/&gt;
 *         &lt;arg value="Hello"/&gt;
 *   &lt;/jmx:invoke&gt;
 * </pre>
 * </li>
 * <li>
 * Create new AccessLogger at localhost 
 * <code>
 *   &lt;jmx:invoke
 *           name="Catalina:type=MBeanFactory" 
 *           operation="createAccessLoggerValve"
 *           resultproperty="accessLoggerObjectName"
 *       &gt;
 *         &lt;arg value="Catalina:type=Host,host=localhost"/&gt;
 *   &lt;/jmx:invoke&gt;
 *
 * </code>
 * </li>
 * <li>
 * Remove existing AccessLogger at localhost 
 * <code>
 *   &lt;jmx:invoke
 *           name="Catalina:type=MBeanFactory" 
 *           operation="removeValve"
 *       &gt;
 *         &lt;arg value="Catalina:type=Valve,name=AccessLogValve,host=localhost"/&gt;
 *   &lt;/jmx:invoke&gt;
 *
 * </code>
 * </li>
 * </ul>
 * <p>
 * First call to a remote MBeanserver save the JMXConnection a referenz <em>jmx.server</em>
 * </p>
 * These tasks require Ant 1.6 or later interface.
 *
 * @author Peter Rossbach
 * @since 5.5.10
 */
public class JMXAccessorInvokeTask extends JMXAccessorTask {


    // ----------------------------------------------------- Instance Variables

    private String operation ;
    private List<Arg> args=new ArrayList<Arg>();

    // ----------------------------------------------------- Instance Info

    /**
     * Descriptive information describing this implementation.
     */
    private static final String info = "org.apache.catalina.ant.JMXAccessorInvokeTask/1.0";

    /**
     * Return descriptive information about this implementation and the
     * corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    @Override
    public String getInfo() {

        return (info);

    }

    // ------------------------------------------------------------- Properties
    
    /**
     * @return Returns the operation.
     */
    public String getOperation() {
        return operation;
    }
    /**
     * @param operation The operation to set.
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void addArg(Arg arg ) {
        args.add(arg);
    }

    /**
     * @return Returns the args.
     */
    public List<Arg> getArgs() {
        return args;
    }
    /**
     * @param args The args to set.
     */
    public void setArgs(List<Arg> args) {
        this.args = args;
    }
    
    // ------------------------------------------------------ protected Methods
    
    /**
     * Execute the specified command, based on the configured properties. The
     * input stream will be closed upon completion of this task, whether it was
     * executed successfully or not.
     * 
     * @exception BuildException
     *                if an error occurs
     */
    @Override
    public String jmxExecute(MBeanServerConnection jmxServerConnection)
        throws Exception {

        if (getName() == null) {
            throw new BuildException("Must specify a 'name'");
        }
        if ((operation == null)) {
            throw new BuildException(
                    "Must specify a 'operation' for call");
        }
        return  jmxInvoke(jmxServerConnection, getName());
     }

    /**
     * @param jmxServerConnection
     * @throws Exception
     */
    protected String jmxInvoke(MBeanServerConnection jmxServerConnection, String name) throws Exception {
        Object result ;
        if (args == null) {
             result = jmxServerConnection.invoke(new ObjectName(name),
                    operation, null, null);
        } else {
            Object argsA[]=new Object[ args.size()];
            String sigA[]=new String[args.size()];
            for( int i=0; i<args.size(); i++ ) {
                Arg arg=args.get(i);
                if (arg.getType() == null) {
                    arg.setType("java.lang.String");
                    sigA[i]=arg.getType();
                    argsA[i]=arg.getValue();
                } else {
                    sigA[i]=arg.getType();
                    argsA[i]=convertStringToType(arg.getValue(),arg.getType());
                }                
            }
            result = jmxServerConnection.invoke(new ObjectName(name), operation, argsA, sigA);
        }
        if(result != null) {
            echoResult(operation,result);
            createProperty(result);
        }
        return null;
    }

}
