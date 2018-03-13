package jmxbp.ex2;

import com.sun.jdmk.comm.*;
import javax.management.*;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.w3c.dom.*;
import java.io.*;
import jmxbp.common.*;

/**
 * Controls and coordinates all activities in the
 * sample application. Implemented as a dynamic MBean.
 */
public class Controller extends Basic implements MBeanRegistration {
    
    public static final String CONTROLLER_OBJECT_NAME = "UserDomain:name=Controller";
    private MBeanServer _server;
    private Queue _queue;
    private int _numberOfSuppliers;
    private int _numberOfConsumers;
    private DynamicMBeanFacade _mbean;
    private Document _config;
    
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Controller.main(): INFO: Usage: " +
                Controller.class.getPackage() + "." + 
                Controller.class.getName() + " [app-mbean-definition-file]");
        } else {
            String appMbeanDef = args[0];
            System.out.println("Controller.main(): INFO: Application definition mbean file is \'" +
                appMbeanDef + "\'");
            new Controller(appMbeanDef);
            try {
                Thread.currentThread().join();
            } catch (InterruptedException e) {}
        }
    }
    
    public Controller(String appMbeanDef) {
        super();
        MessageLog log = new MessageLog();
        this.enableTracing();
        _server = MBeanServerFactory.createMBeanServer();
        // Create the HTML adapter
        this.createHTMLAdapter();
        int supplierWorkFactor = 100;
        int consumerWorkFactor = 150;
        trace("Controller: INFO: " + "supplierWorkFactor=" + 
                supplierWorkFactor + ", consumerWorkFactor=" + consumerWorkFactor);
        // Register the controller as an MBean
        ObjectName objName = null;
        try {
            _queue = new Queue();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilderFactory.setValidating(true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            // Set an ErrorHandler before parsing
            OutputStreamWriter errorWriter =
                new OutputStreamWriter(System.err, "UTF-8");
            docBuilder.setErrorHandler(
                new MyErrorHandler(new PrintWriter(errorWriter, true)));
            _config = docBuilder.parse(new File(appMbeanDef));

            DynamicMBeanFacade mbean = createMBeanFromXMLDefinition(_config.getDocumentElement(), "Queue", _queue);
            if (mbean != null) {
                objName = new ObjectName(":name=Queue,instanceId=" + Integer.toString(_queue.hashCode()));
                _server.registerMBean(mbean, objName);
                trace("\tOBJECT NAME = " + objName);
            }
            mbean = createMBeanFromXMLDefinition(_config.getDocumentElement(), "Controller", this);
            if (mbean != null) {
                objName = new ObjectName(":name=Controller,instanceId=" + Integer.toString(this.hashCode()));
                _server.registerMBean(mbean, objName);
            }
            //
            // Now create the worker threads.
            //
            this.createWorker(Supplier.ROLE, supplierWorkFactor);
            this.createWorker(Consumer.ROLE, consumerWorkFactor);
        } catch (Exception e) {
            System.out.println("Controller.main(): ERROR: " + "Could not register the Controller MBean! Stack trace follows...");
            log.write("Controller.main(): ERROR: " + "Could not register the Controller MBean! Stack trace follows...");
            e.printStackTrace();
            log.write(e);
            return;
        }
    }

    /**
     * Creates the HTML adapter server and starts it running
     * on its own thread of execution.
     */
    private void createHTMLAdapter () {
        int portNumber = 8090;
        try {
            HtmlAdaptorServer html = new HtmlAdaptorServer(portNumber);
            ObjectName html_name = null;
            html_name = new ObjectName("Adaptor:name=html,port=" + portNumber);
            trace("\tOBJECT NAME = " + html_name);
            _server.registerMBean(html, html_name);
            html.start();
        } catch (Exception e) {
            trace("\t!!! Could not create the HTML adaptor !!!");
            e.printStackTrace();
            trace(e);
            return;
        }
    }

    /**
     * Returns the number of workers of each type
     */
    protected int getNumberOfWorkers (String role) {
        int ret = 0;
        if (role.equalsIgnoreCase(Supplier.ROLE)) {
            ret = _numberOfSuppliers;
        } 
        else if (role.equalsIgnoreCase(Consumer.ROLE)) {
            ret = _numberOfConsumers;
        } 
        else {
            throw  new RuntimeMBeanException(new IllegalArgumentException("Controller.getNumberOfWorkers(): ERROR: "
                    + "Unknown role name \'" + role + "\'."));
        }
        return  ret;
    }

    /**
     * Builds the worker key from the role name
     */
    protected String buildWorkerKey (String role) {
        StringBuffer buf = new StringBuffer();
        buf.append(Worker.OBJECT_NAME);
        buf.append(",role=");
        buf.append(role);
        return  buf.toString();
    }

    /**
     * Retrieves a reference to the Queue.
     */
    public Queue getConsumerSupplierQueue () {
        return  _queue;
    }

    /**
     * Creates and registers a worker MBean of the specified
     * type.
     */
    protected void createNewWorker (String role, int workFactor, int instanceId) {
        // Create the Worker and register it with the MBean server.
        trace("Controller.createNewWorker(): INFO: " +
            "Creating new worker in the role of \'" + role +
            "\' with a work factor of " + workFactor);
        Worker worker = null;
        ObjectName objName = null;
        StringBuffer buf = new StringBuffer();
        try {
            buf.append(buildWorkerKey(role));
            buf.append(",instanceId=");
            buf.append(instanceId);
            if (role.equalsIgnoreCase(Supplier.ROLE)) {
                worker = (Worker) new Supplier(_queue, workFactor);
            } 
            else if (role.equalsIgnoreCase(Consumer.ROLE)) {
                worker = (Worker) new Consumer(_queue, workFactor);
            }
            DynamicMBeanFacade mbean = createMBeanFromXMLDefinition(_config.getDocumentElement(), role, worker);
            if (mbean != null) {
                objName = new ObjectName(buf.toString());
                _server.registerMBean(mbean, objName);
                trace("\tOBJECT NAME = " + objName);
            }
            Thread t = new Thread(worker);
            t.start();
            if (role.equalsIgnoreCase(Supplier.ROLE)) {
                _numberOfSuppliers++;
            } 
            else if (role.equalsIgnoreCase(Consumer.ROLE)) {
                _numberOfConsumers++;
            }
        } catch (Exception e) {
            trace("Controller.main(): ERROR: " + "Could not register the Worker MBean! Stack trace follows...");
            e.printStackTrace();
            trace(e);
            return;
        }
    }

    /**
     * Creates and starts a worker thread
     */
    public void createWorker (String role, int workFactor) {
        int index = getNumberOfWorkers(role);
        createNewWorker(role, workFactor, index + 1);
    }

    public void reset () {
    // nothing to do, have to implement, though...
    }


    private void trace (String message) {
        if (isTraceOn()) {
            System.out.println(message);
        }
        traceLog(message);
    }

    private void trace (Throwable t) {
        traceLog(t);
    }
    private void traceLog (Throwable t) {
        if (isTraceOn()) {
            _logger.write(t);
        }
    }
    MessageLog _logger = new MessageLog();

    private void traceLog (String message) {
        if (isTraceOn()) {
            _logger.write(message);
        }
    }
    private DynamicMBeanFacade createMBeanFromXMLDefinition(Node n, String className, Object resource) {
        DynamicMBeanFacade mbean = null;
        try {
            trace("Controller.createMBeanFromXMLDefinition(): INFO: " +
                "Looking for mbean \'" + className + "\'");
            //
            // First attempt to find all mbean nodes so we can find the
            /// one we'return looking for.
            //
            NodeList nodes = n.getChildNodes();
            for (int aa = 0; aa < nodes.getLength(); aa++) {
                Node node = nodes.item(aa);
                trace("\tLooking at node: type=" + node.getNodeType() +
                    "\', name=\'" + node.getNodeName() + "\'");
                if (node.getNodeType() == Node.ELEMENT_NODE &&
                    node.getNodeName().equals("mbean")) {
                    NamedNodeMap atts = node.getAttributes();
                    Node att = atts.getNamedItem("className");
                    trace("\tFound an mbean node attribute named \'" + 
                    att.getNodeName() + "\' with value \'" + 
                    att.getNodeValue() + "\'");
                    if (att.getNodeValue().equals(className)) {
                        String mbeanDesc = this.getElementText(node);
                        mbean = new DynamicMBeanFacade(resource, mbeanDesc);
                        //
                        // Now read the management interface definition
                        //
                        trace("\tFound the mbean class: \'" + className + "\'");
                        NodeList features = node.getChildNodes();
                        for (int cc = 0; cc < features.getLength(); cc++) {
                            Node feature = features.item(cc);
                            if (feature.getNodeType() == Node.ELEMENT_NODE) {
                                NamedNodeMap atts2 = feature.getAttributes();
                                if (feature.getNodeName().equals("attribute")) {
                                    Node nameNode = atts2.getNamedItem("name");
                                    String attName = nameNode.getNodeValue();
                                    String attDesc = this.getElementText(feature);
                                    trace("\tProcessing attribute: name=\'" + attName + "\', description=\'" + attDesc + "\'");
                                    mbean.addAttribute(attName, attDesc);
                                } else if (feature.getNodeName().equals("operation")) {
                                    Node nameNode = atts2.getNamedItem("name");
                                    String opName = nameNode.getNodeValue();
                                    String opDesc = this.getElementText(feature);
                                    trace("\tProcessing operation: name=\'" + opName + "\', description=\'" + opDesc + "\'");
                                    mbean.addOperation(opName, opDesc);
                                } else if (feature.getNodeName().equals("notification")) {
                                    Node nameNode = atts2.getNamedItem("type");
                                    String notifName = nameNode.getNodeValue();
                                    String notifDesc = this.getElementText(feature);
                                    trace("\tProcessing notification: name=\'" + notifName + "\', description=\'" + notifDesc + "\'");
                                    mbean.addNotification(notifName, notifDesc);
                                }
                            }
                        } // for (int cc ...
                    } // att.getNodeValue().equals(className)
                }
                if (mbean != null)
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            trace(e);
        }
        if (mbean == null) {
            trace("Controller.createMBeanFromXMLDefinition(): INFO: MBean \'" +
                className + "\' not found in definition file.");
        }
        return mbean;
    }
    private String getElementText(Node node) {
        String ret = null;
        try {
            for (Node child = node.getFirstChild(); child != null;
                 child = child.getNextSibling()) {
                 if (child.getNodeType() == Node.TEXT_NODE) {
                     ret = child.getNodeValue();
                     break;
                 }
            }
        } catch (Exception e) {
            e.printStackTrace();
            trace(e);
        }
        return ret;
    }

    public void postDeregister() {
    }
    
    public void postRegister(Boolean booleanParam) {
    }
    
    public void preDeregister() throws java.lang.Exception {
    }
    
    public javax.management.ObjectName preRegister(javax.management.MBeanServer mBeanServer, javax.management.ObjectName objectName) throws java.lang.Exception {
        _server = mBeanServer;
        ObjectName objName = objectName;
        try {
            new ObjectName(Controller.CONTROLLER_OBJECT_NAME);
            System.out.println("\tOBJECT NAME = " + objName);
            trace("\tOBJECT NAME = " + objName);
        } catch (MalformedObjectNameException e) {
            trace (e);
        }
        return objName;
    }
    
    // Error handler to report errors and warnings
    private static class MyErrorHandler implements ErrorHandler {
        /** Error handler output goes here */
        private PrintWriter out;

        MyErrorHandler(PrintWriter out) {
            this.out = out;
        }

        /**
         * Returns a string describing parse exception details
         */
        private String getParseExceptionInfo(SAXParseException spe) {
            String systemId = spe.getSystemId();
            if (systemId == null) {
                systemId = "null";
            }
            String info = "URI=" + systemId +
                " Line=" + spe.getLineNumber() +
                ": " + spe.getMessage();
            return info;
        }

        // The following methods are standard SAX ErrorHandler methods.
        // See SAX documentation for more info.

        public void warning(SAXParseException spe) throws SAXException {
            out.println("Warning: " + getParseExceptionInfo(spe));
        }
        
        public void error(SAXParseException spe) throws SAXException {
            String message = "Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }

        public void fatalError(SAXParseException spe) throws SAXException {
            String message = "Fatal Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }
    }
}



