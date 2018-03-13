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


import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.apache.tools.ant.BuildException;


/**
 * Query for Mbeans. 
 * <ul>
 * <li>open no existing JSR 160 rmi jmx connection</li>
 * <li>Get all Mbeans attributes</li>
 * <li>Get only the Query Mbeans ObjectNames</li>
 * <li>Show query result as Ant console log</li>
 * <li>Bind query result as Ant properties</li>
 * </ul>
 * <br>
 * Query a list of Mbeans.
 * <pre>
 *   &lt;jmxQuery
 *           host="127.0.0.1"
 *           port="9014"
 *           name="Catalina:type=Manager,* 
 *           resultproperty="manager" /&gt;
 * </pre>
 * with attribute <em>attributebinding="true"</em> you can get 
 * all attributes also from result objects.<br/>
 * The property manager.length show the size of the result 
 * and with manager.[0..length].name the 
 * resulted ObjectNames are saved. 
 * These tasks require Ant 1.6 or later interface.
 *
 * @author Peter Rossbach
 * @since 5.5.10
 */
public class JMXAccessorQueryTask extends JMXAccessorTask {

    // ----------------------------------------------------- Instance Variables

    private boolean attributebinding = false;

    // ----------------------------------------------------- Instance Info

    /**
     * Descriptive information describing this implementation.
     */
    private static final String info = "org.apache.catalina.ant.JMXAccessorQueryTask/1.0";

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
     * @return Returns the attributebinding.
     */
    public boolean isAttributebinding() {
        return attributebinding;
    }
    /**
     * @param attributeBinding The attributebinding to set.
     */
    public void setAttributebinding(boolean attributeBinding) {
        this.attributebinding = attributeBinding;
    }
  
    // ------------------------------------------------------ protected Methods

    
    /**
     * Execute the specified command, based on the configured properties. The
     * input stream will be closed upon completion of this task, whether it was
     * executed successfully or not.
     * 
     * @exception Exception
     *                if an error occurs
     */
    @Override
    public String jmxExecute(MBeanServerConnection jmxServerConnection)
        throws Exception {

        if (getName() == null) {
            throw new BuildException("Must specify a 'name'");
        }
        return jmxQuery(jmxServerConnection, getName());

    }

       
    /**
     * Call Mbean server for some mbeans with same domain, attributes.
     *  with <em>attributebinding=true</em> you can save all attributes from all found objects
     * as your ant properties
     * @param jmxServerConnection
     * @param qry
     * @return The query result
     */
    protected String jmxQuery(MBeanServerConnection jmxServerConnection,
            String qry) {
        String isError = null;
        Set<ObjectName> names = null;
        String resultproperty = getResultproperty();
        try {
            names = jmxServerConnection.queryNames(new ObjectName(qry), null);
            if (resultproperty != null) {
                setProperty(resultproperty + ".Length",Integer.toString(names.size()));
            }
        } catch (Exception e) {
            if (isEcho())
                handleErrorOutput(e.getMessage());
            return "Can't query mbeans " + qry;
        }

        if (resultproperty != null) {
            Iterator<ObjectName> it = names.iterator();
            int oindex = 0;
            String pname = null;
            while (it.hasNext()) {
                ObjectName oname = it.next();
                pname = resultproperty + "." + Integer.toString(oindex) + ".";
                oindex++;
                    setProperty(pname + "Name", oname.toString());
                    if (isAttributebinding()) {
                        bindAttributes(jmxServerConnection, resultproperty, pname, oname);
                
                    }
                }
        }
        return isError;
    }

    /**
     * @param jmxServerConnection
     * @param resultproperty
     * @param pname
     * @param oname
     */
    protected void bindAttributes(MBeanServerConnection jmxServerConnection, String resultproperty, String pname, ObjectName oname) {
        if (jmxServerConnection != null  && resultproperty != null 
            && pname != null && oname != null ) {
            try {
                MBeanInfo minfo = jmxServerConnection.getMBeanInfo(oname);
                MBeanAttributeInfo attrs[] = minfo.getAttributes();
                Object value = null;

                for (int i = 0; i < attrs.length; i++) {
                    if (!attrs[i].isReadable())
                        continue;
                    String attName = attrs[i].getName();
                    if (attName.indexOf('=') >= 0 || attName.indexOf(':') >= 0
                            || attName.indexOf(' ') >= 0) {
                        continue;
                    }

                    try {
                        value = jmxServerConnection
                                .getAttribute(oname, attName);
                    } catch (Exception e) {
                        if (isEcho())
                            handleErrorOutput("Error getting attribute "
                                    + oname + " " + pname + attName + " "
                                    + e.toString());
                        continue;
                    }
                    if (value == null)
                        continue;
                    if ("modelerType".equals(attName))
                        continue;
                    createProperty(pname + attName, value);
                }
            } catch (Exception e) {
                // Ignore
            }
        }
    }
}
