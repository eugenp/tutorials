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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.Attribute;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.loading.MLet;
import javax.xml.transform.TransformerException;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.DomUtil;
import org.apache.tomcat.util.modeler.AttributeInfo;
import org.apache.tomcat.util.modeler.BaseModelMBean;
import org.apache.tomcat.util.modeler.ManagedBean;
import org.apache.tomcat.util.modeler.Registry;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


/** This will create mbeans based on a config file.
 *  The format is an extended version of MLET.
 *
 * Classloading. We don't support any explicit classloader tag. 
 * A ClassLoader is just an mbean ( it can be the standard MLetMBean or
 * a custom one ). 
 * 
 * XXX add a special attribute to reference the loader mbean,
 * XXX figure out how to deal with private loaders
 *
 * @deprecated Unused: Will be removed in Tomcat 8.0.x
 */
@Deprecated
public class MbeansSource extends ModelerSource implements MbeansSourceMBean
{
    private static final Log log = LogFactory.getLog(MbeansSource.class);
    Registry registry;
    String type;

    // true if we are during the original loading
    boolean loading=true;
    List<ObjectName> mbeans = new ArrayList<ObjectName>();
    static boolean loaderLoaded=false;
    private Document document;
    private HashMap<ObjectName,Node> object2Node =
        new HashMap<ObjectName,Node>();

    long lastUpdate;
    long updateInterval=10000; // 10s

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

    @Override
    public void setSource( Object source ) {
        this.source=source;
    }

    @Override
    public Object getSource() {
        return source;
    }

    public String getLocation() {
        return location;
    }
    
    /** Return the list of mbeans created by this source.
     *  It can be used to implement runtime services.
     */
    @Override
    public List<ObjectName> getMBeans() {
        return mbeans;
    }

    @Override
    public List<ObjectName> loadDescriptors(Registry registry, String type,
            Object source) throws Exception {
        setRegistry(registry);
        setType(type);
        setSource(source);
        execute();
        return mbeans;
    }

    public void start() throws Exception {
        registry.invoke(mbeans, "start", false);        
    }

    public void stop() throws Exception {
        registry.invoke(mbeans, "stop", false);        
    }
    
    @Override
    public void init() throws Exception {
        if( mbeans==null) execute();
        if( registry==null ) registry=Registry.getRegistry(null, null);
        
        registry.invoke(mbeans, "init", false);
    }
    
    public void destroy() throws Exception {
        registry.invoke(mbeans, "destroy", false);                
    }
    
    @Override
    public void load() throws Exception {
        execute(); // backward compat
    }

    public void execute() throws Exception {
        if( registry==null ) registry=Registry.getRegistry(null, null);
        try {
            InputStream stream=getInputStream();
            long t1=System.currentTimeMillis();
            document = DomUtil.readXml(stream);

            // We don't care what the root node is.
            Node descriptorsN=document.getDocumentElement();

            if( descriptorsN == null ) {
                log.error("No descriptors found");
                return;
            }

            Node firstMbeanN=DomUtil.getChild(descriptorsN, null);

            if( firstMbeanN==null ) {
                // maybe we have a single mlet
                if( log.isDebugEnabled() )
                    log.debug("No child " + descriptorsN);
                firstMbeanN=descriptorsN;
            }

            MBeanServer server =
                Registry.getRegistry(null, null).getMBeanServer();

            // XXX Not very clean...  Just a workaround
            if( ! loaderLoaded ) {
                // Register a loader that will be find ant classes.
                ObjectName defaultLoader= new ObjectName("modeler",
                        "loader", "modeler");
                MLet mlet=new MLet( new URL[0], this.getClass().getClassLoader());
                server.registerMBean(mlet, defaultLoader);
                loaderLoaded=true;
            }
        
            // Process nodes
            for (Node mbeanN = firstMbeanN; mbeanN != null;
                 mbeanN= DomUtil.getNext(mbeanN, null, Node.ELEMENT_NODE))
            {
                String nodeName=mbeanN.getNodeName();

                // mbean is the "official" name
                if( "mbean".equals(nodeName) || "MLET".equals(nodeName) )
                {
                    String code=DomUtil.getAttribute( mbeanN, "code" );
                    String objectName=DomUtil.getAttribute( mbeanN, "objectName" );
                    if( objectName==null ) {
                        objectName=DomUtil.getAttribute( mbeanN, "name" );
                    }
                    
                    if( log.isDebugEnabled())
                        log.debug( "Processing mbean objectName=" + objectName +
                                " code=" + code);

                    // args can be grouped in constructor or direct childs
                    Node constructorN=DomUtil.getChild(mbeanN, "constructor");
                    if( constructorN == null ) constructorN=mbeanN;

                    processArg(constructorN);

                    try {
                        ObjectName oname=new ObjectName(objectName);
                        if( ! server.isRegistered( oname )) {
                            // We wrap everything in a model mbean.
                            // XXX need to support "StandardMBeanDescriptorsSource"
                            String modelMBean=BaseModelMBean.class.getName();                            
                            server.createMBean(modelMBean, oname,
                                    new Object[] { code, this},
                                    new String[] { String.class.getName(),
                                                  ModelerSource.class.getName() } 
                                    );
                            mbeans.add(oname);
                        }
                        object2Node.put( oname, mbeanN );
                        // XXX Arguments, loader !!!
                    } catch( Exception ex ) {
                        log.error( "Error creating mbean " + objectName, ex);
                    }

                    Node firstAttN=DomUtil.getChild(mbeanN, "attribute");
                    for (Node descN = firstAttN; descN != null;
                         descN = DomUtil.getNext( descN ))
                    {
                        processAttribute(server, descN, objectName);
                    }
                } else if("jmx-operation".equals(nodeName) ) {
                    String name=DomUtil.getAttribute(mbeanN, "objectName");
                    if( name==null )
                        name=DomUtil.getAttribute(mbeanN, "name");

                    String operation=DomUtil.getAttribute(mbeanN, "operation");

                    if( log.isDebugEnabled())
                        log.debug( "Processing invoke objectName=" + name +
                                " code=" + operation);
                    try {
                        ObjectName oname=new ObjectName(name);

                        processArg( mbeanN );
                        server.invoke( oname, operation, null, null);
                    } catch (Exception e) {
                        log.error( "Error in invoke " + name + " " + operation);
                    }
                }

                ManagedBean managed=new ManagedBean();
                DomUtil.setAttributes(managed, mbeanN);
                Node firstN;

                // process attribute info
                firstN=DomUtil.getChild( mbeanN, "attribute");
                for (Node descN = firstN; descN != null;
                     descN = DomUtil.getNext( descN ))
                {
                    AttributeInfo ci=new AttributeInfo();
                    DomUtil.setAttributes(ci, descN);
                    managed.addAttribute( ci );
                }

            }

            long t2=System.currentTimeMillis();
            log.info( "Reading mbeans  " + (t2-t1));
            loading=false;
        } catch( Exception ex ) {
            log.error( "Error reading mbeans ", ex);
        }
    }
    
    @Override
    public void updateField( ObjectName oname, String name, 
                             Object value )
    {
        if( loading ) return;
        // nothing by default
        //log.info( "XXX UpdateField " + oname + " " + name + " " + value);
        Node n = object2Node.get(oname);
        if( n == null ) {
            log.info( "Node not found " + oname );
            return;
        }
        Node attNode=DomUtil.findChildWithAtt(n, "attribute", "name", name);
        if( attNode == null ) {
            // found no existing attribute with this name
            attNode=n.getOwnerDocument().createElement("attribute");
            DomUtil.setAttribute(attNode, "name", name);
            n.appendChild(attNode);
        } 
        String oldValue=DomUtil.getAttribute(attNode, "value");
        if( oldValue != null ) {
            // we'll convert all values to text content
            DomUtil.removeAttribute( attNode, "value");
        }
        DomUtil.setText(attNode, value.toString());

        //store();
    }
    
    /** Store the mbeans. 
     * XXX add a background thread to store it periodically 
     */ 
    @Override
    public void save() {
        // XXX customize no often than ( based on standard descriptor ), etc.
        // It doesn't work very well if we call this on each set att - 
        // the triger will work for the first att, but all others will be delayed
        long time=System.currentTimeMillis();
        if( location!=null &&
                time - lastUpdate > updateInterval ) {
            lastUpdate=time;
            try {
                FileOutputStream fos=new FileOutputStream(location);
                DomUtil.writeXml(document, fos);
            } catch (TransformerException e) {
                log.error( "Error writing");
            } catch (FileNotFoundException e) {
                log.error( "Error writing" ,e );
            }
        }
    }

    private void processAttribute(MBeanServer server,
                                  Node descN, String objectName ) {
        String attName=DomUtil.getAttribute(descN, "name");
        String value=DomUtil.getAttribute(descN, "value");
        String type=null; // DomUtil.getAttribute(descN, "type");
        if( value==null ) {
            // The value may be specified as CDATA
            value=DomUtil.getContent(descN);
        }
        try {
            if( log.isDebugEnabled())
                log.debug("Set attribute " + objectName + " " + attName +
                        " " + value);
            ObjectName oname=new ObjectName(objectName);
            // find the type
            type=registry.getType(  oname, attName );

            if( type==null ) {
                log.info("Can't find attribute " + objectName + " " + attName );

            } else {
                Object valueO=registry.convertValue( type, value);
                server.setAttribute(oname, new Attribute(attName, valueO));
            }
        } catch( Exception ex) {
            log.error("Error processing attribute " + objectName + " " +
                    attName + " " + value, ex);
        }

    }

    private void processArg(Node mbeanN) {
        Node firstArgN=DomUtil.getChild(mbeanN, "arg" );
        // process all args
        for (Node argN = firstArgN; argN != null;
             argN = DomUtil.getNext( argN ))
        {
            DomUtil.getAttribute(argN, "type");
            String value=DomUtil.getAttribute(argN, "value");
            if( value==null ) {
                // The value may be specified as CDATA
                value=DomUtil.getContent(argN);
            }
        }
    }
}
