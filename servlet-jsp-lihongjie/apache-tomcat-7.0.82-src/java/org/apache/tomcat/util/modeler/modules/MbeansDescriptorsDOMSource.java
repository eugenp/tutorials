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


package org.apache.tomcat.util.modeler.modules;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.management.ObjectName;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.DomUtil;
import org.apache.tomcat.util.modeler.AttributeInfo;
import org.apache.tomcat.util.modeler.ManagedBean;
import org.apache.tomcat.util.modeler.NotificationInfo;
import org.apache.tomcat.util.modeler.OperationInfo;
import org.apache.tomcat.util.modeler.ParameterInfo;
import org.apache.tomcat.util.modeler.Registry;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * @deprecated Unused: Will be removed in Tomcat 8.0.x
 */
@Deprecated
public class MbeansDescriptorsDOMSource extends ModelerSource
{
    private static final Log log = LogFactory.getLog(MbeansDescriptorsDOMSource.class);

    Registry registry;
    String location;
    String type;
    Object source;
    List<ObjectName> mbeans=new ArrayList<ObjectName>();

    public void setRegistry(Registry reg) {
        this.registry=reg;
    }

    public void setLocation( String loc ) {
        this.location=loc;
    }

    /** Used if a single component is loaded
     *
     * @param type
     */
    public void setType( String type ) {
       this.type=type;
    }

    public void setSource( Object source ) {
        this.source=source;
    }

    @Override
    public List<ObjectName> loadDescriptors( Registry registry,
                                 String type, Object source)
            throws Exception
    {
        setRegistry(registry);
        setType(type);
        setSource(source);
        execute();
        return mbeans;
    }

    public void execute() throws Exception {
        if( registry==null ) registry=Registry.getRegistry(null, null);

        try {
            InputStream stream=(InputStream)source;
            long t1=System.currentTimeMillis();
            Document doc=DomUtil.readXml(stream);
            // Ignore for now the name of the root element
            Node descriptorsN=doc.getDocumentElement();
            //Node descriptorsN=DomUtil.getChild(doc, "mbeans-descriptors");
            if( descriptorsN == null ) {
                log.error("No descriptors found");
                return;
            }

            Node firstMbeanN=null;
            if( "mbean".equals( descriptorsN.getNodeName() ) ) {
                firstMbeanN=descriptorsN;
            } else {
                firstMbeanN=DomUtil.getChild(descriptorsN, "mbean");
            }

            if( firstMbeanN==null ) {
                log.error(" No mbean tags ");
                return;
            }

            // Process each <mbean> element
            for (Node mbeanN = firstMbeanN; mbeanN != null;
                 mbeanN= DomUtil.getNext(mbeanN))
            {

                // Create a new managed bean info
                ManagedBean managed=new ManagedBean();
                DomUtil.setAttributes(managed, mbeanN);
                Node firstN;

                // Process descriptor subnode
                /*Node mbeanDescriptorN =
                    DomUtil.getChild(mbeanN, "descriptor");
                if (mbeanDescriptorN != null) {
                    Node firstFieldN =
                        DomUtil.getChild(mbeanDescriptorN, "field");
                    for (Node fieldN = firstFieldN; fieldN != null;
                         fieldN = DomUtil.getNext(fieldN)) {
                        FieldInfo fi = new FieldInfo();
                        DomUtil.setAttributes(fi, fieldN);
                        managed.addField(fi);
                    }
                }*/

                // process attribute nodes
                firstN=DomUtil.getChild( mbeanN, "attribute");
                for (Node descN = firstN; descN != null;
                     descN = DomUtil.getNext( descN ))
                {

                    // Create new attribute info
                    AttributeInfo ai=new AttributeInfo();
                    DomUtil.setAttributes(ai, descN);

                    // Process descriptor subnode
                    /*Node descriptorN =
                        DomUtil.getChild(descN, "descriptor");
                    if (descriptorN != null) {
                        Node firstFieldN =
                            DomUtil.getChild(descriptorN, "field");
                        for (Node fieldN = firstFieldN; fieldN != null;
                             fieldN = DomUtil.getNext(fieldN)) {
                            FieldInfo fi = new FieldInfo();
                            DomUtil.setAttributes(fi, fieldN);
                            ai.addField(fi);
                        }
                    }
                    */

                    // Add this info to our managed bean info
                    managed.addAttribute( ai );
                    if (log.isTraceEnabled()) {
                        log.trace("Create attribute " + ai);
                    }

                }

                // process constructor nodes
                /*
                firstN=DomUtil.getChild( mbeanN, "constructor");
                for (Node descN = firstN; descN != null;
                     descN = DomUtil.getNext( descN )) {

                    // Create new constructor info
                    ConstructorInfo ci=new ConstructorInfo();
                    DomUtil.setAttributes(ci, descN);

                    // Process descriptor subnode
                    Node firstDescriptorN =
                        DomUtil.getChild(descN, "descriptor");
                    if (firstDescriptorN != null) {
                        Node firstFieldN =
                            DomUtil.getChild(firstDescriptorN, "field");
                        for (Node fieldN = firstFieldN; fieldN != null;
                             fieldN = DomUtil.getNext(fieldN)) {
                            FieldInfo fi = new FieldInfo();
                            DomUtil.setAttributes(fi, fieldN);
                            ci.addField(fi);
                        }
                    }

                    // Process parameter subnodes
                    Node firstParamN=DomUtil.getChild( descN, "parameter");
                    for (Node paramN = firstParamN;  paramN != null;
                         paramN = DomUtil.getNext(paramN))
                    {
                        ParameterInfo pi=new ParameterInfo();
                        DomUtil.setAttributes(pi, paramN);
                        ci.addParameter( pi );
                    }

                    // Add this info to our managed bean info
                    managed.addConstructor( ci );
                    if (log.isTraceEnabled()) {
                        log.trace("Create constructor " + ci);
                    }

                }*/

                // process notification nodes
                firstN=DomUtil.getChild( mbeanN, "notification");
                for (Node descN = firstN; descN != null;
                     descN = DomUtil.getNext( descN ))
                {

                    // Create new notification info
                    NotificationInfo ni=new NotificationInfo();
                    DomUtil.setAttributes(ni, descN);

                    // Process descriptor subnode
                    /*Node firstDescriptorN =
                        DomUtil.getChild(descN, "descriptor");
                    if (firstDescriptorN != null) {
                        Node firstFieldN =
                            DomUtil.getChild(firstDescriptorN, "field");
                        for (Node fieldN = firstFieldN; fieldN != null;
                             fieldN = DomUtil.getNext(fieldN)) {
                            FieldInfo fi = new FieldInfo();
                            DomUtil.setAttributes(fi, fieldN);
                            ni.addField(fi);
                        }
                    }*/

                    // Process notification-type subnodes
                    Node firstParamN=DomUtil.getChild( descN, "notification-type");
                    for (Node paramN = firstParamN;  paramN != null;
                         paramN = DomUtil.getNext(paramN))
                    {
                        ni.addNotifType( DomUtil.getContent(paramN) );
                    }

                    // Add this info to our managed bean info
                    managed.addNotification( ni );
                    if (log.isTraceEnabled()) {
                        log.trace("Created notification " + ni);
                    }

                }

                // process operation nodes
                firstN=DomUtil.getChild( mbeanN, "operation");
                for (Node descN = firstN; descN != null;
                     descN = DomUtil.getNext( descN ))

                {

                    // Create new operation info
                    OperationInfo oi=new OperationInfo();
                    DomUtil.setAttributes(oi, descN);

                    // Process descriptor subnode
                    /*Node firstDescriptorN =
                        DomUtil.getChild(descN, "descriptor");
                    if (firstDescriptorN != null) {
                        Node firstFieldN =
                            DomUtil.getChild(firstDescriptorN, "field");
                        for (Node fieldN = firstFieldN; fieldN != null;
                             fieldN = DomUtil.getNext(fieldN)) {
                            FieldInfo fi = new FieldInfo();
                            DomUtil.setAttributes(fi, fieldN);
                            oi.addField(fi);
                        }
                    }*/

                    // Process parameter subnodes
                    Node firstParamN=DomUtil.getChild( descN, "parameter");
                    for (Node paramN = firstParamN;  paramN != null;
                         paramN = DomUtil.getNext(paramN))
                    {
                        ParameterInfo pi=new ParameterInfo();
                        DomUtil.setAttributes(pi, paramN);
                        if( log.isTraceEnabled())
                            log.trace("Add param " + pi.getName());
                        oi.addParameter( pi );
                    }

                    // Add this info to our managed bean info
                    managed.addOperation( oi );
                    if( log.isTraceEnabled()) {
                        log.trace("Create operation " + oi);
                    }

                }

                // Add the completed managed bean info to the registry
                registry.addManagedBean(managed);
            }

            long t2=System.currentTimeMillis();
            log.debug( "Reading descriptors ( dom ) " + (t2-t1));
        } catch( Exception ex ) {
            log.error( "Error reading descriptors ", ex);
        }
    }
}
