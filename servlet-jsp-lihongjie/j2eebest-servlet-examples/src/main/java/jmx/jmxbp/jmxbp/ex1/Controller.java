package jmxbp.ex1;

import com.sun.jdmk.comm.*;
import javax.management.*;
import java.util.*;
import javax.xml.parsers.*;
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
    
    public static void main(String[] args) {
        new Controller();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {}
    }
    
    public Controller() {
        super();
        MessageLog log = new MessageLog();
        this.enableTracing();
        _server = MBeanServerFactory.createMBeanServer();
        // Create the HTML adapter
        this.createHTMLAdapter();
        int supplierWorkFactor = 100;
        int consumerWorkFactor = 150;
        System.out.println("Controller: INFO: " + "supplierWorkFactor="
                + supplierWorkFactor + ", consumerWorkFactor=" + consumerWorkFactor);
        log.write("Controller: INFO: " + "supplierWorkFactor=" + 
                supplierWorkFactor + ", consumerWorkFactor=" + consumerWorkFactor);
        // Register the controller as an MBean
        ObjectName objName = null;
        try {
            _queue = new Queue();
            _queue.addAttribute("TraceOn", "Indicates whether or not tracing is on.");
            _queue.addAttribute("DebugOn", "Indicates whether or not debugging is on.");
            _queue.addAttribute("NumberOfResets", "The number of times the state of this MBean has been reset.");
            _queue.addAttribute("QueueSize", "Maximum number of items the queue may contain at one time.");
            _queue.addAttribute("NumberOfConsumers", "The number of consumers pulling from this Queue.");
            _queue.addAttribute("NumberOfSuppliers", "The number of suppliers supplying to this Queue.");
            _queue.addAttribute("QueueFull", "Indicates whether or not the Queue is full.");
            _queue.addAttribute("QueueEmpty", "Indicates whether or not the Queue is empty.");
            _queue.addAttribute("Suspended", "Indicates whether or not the Queue is currently suspended.");
            _queue.addAttribute("EndOfInput", "Indicates whether or not the end-of-input has been signalled by all suppliers.");
            _queue.addAttribute("NumberOfConsumers", "The number of consumers pulling from this Queue.");
            _queue.addAttribute("NumberOfItemsProcessed", "The number of items that have been removed from the Queue.");
            _queue.addAttribute("AddWaitTime", "The number of milliseconds spent waiting to add because the Queue was full.");
            _queue.addAttribute("RemoveWaitTime", "The number of milliseconds spent waiting to remove because the Queue was empty.");
            
            _queue.addOperation("enableTracing", "Turns tracing on.");
            _queue.addOperation("disableTracing", "Turns tracing off.");
            _queue.addOperation("enableDebugging", "Turns debugging on.");
            _queue.addOperation("disableDebugging", "Turns debugging off.");
            _queue.addOperation("reset", "Resets the state of this MBean.");
            _queue.addOperation("suspend", "Suspends processing of the Queue.");
            _queue.addOperation("resume", "Resumes processing of the Queue.");

            objName = new ObjectName(":name=Queue,instanceId=" + Integer.toString(_queue.hashCode()));
            _server.registerMBean(_queue, objName);
            trace("\tOBJECT NAME = " + objName);

            this.addAttribute("TraceOn", "Indicates whether or not tracing is on.");
            this.addAttribute("DebugOn", "Indicates whether or not debugging is on.");
            this.addAttribute("NumberOfResets", "The number of times the state of this MBean has been reset.");
            this.addOperation("enableTracing", "Turns tracing on.");
            this.addOperation("disableTracing", "Turns tracing off.");
            this.addOperation("enableDebugging", "Turns debugging on.");
            this.addOperation("disableDebugging", "Turns debugging off.");
            this.addOperation("reset", "Resets the state of this MBean.");
            this.addOperation("createWorker", "Creates a new Worker thread.");
            objName = new ObjectName(":name=Controller,instanceId=" + Integer.toString(this.hashCode()));
            _server.registerMBean(this, objName);
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
            objName = new ObjectName(buf.toString());
            worker.addAttribute("TraceOn", "Indicates whether or not tracing is on.");
            worker.addAttribute("DebugOn", "Indicates whether or not debugging is on.");
            worker.addAttribute("NumberOfResets", "The number of times the state of this MBean has been reset.");
            worker.addAttribute("WorkFactor", "The amount of work to do.");
            worker.addAttribute("NumberOfUnitsProcessed", "The number of units processed.");
            worker.addAttribute("AverageUnitProcessingTime","Average number of milliseconds to process each work unit.");
            worker.addAttribute("Suspended", "Whether or not this thread is suspended.");
            // Operations
            worker.addOperation("enableTracing", "Turns tracing on.");
            worker.addOperation("disableTracing", "Turns tracing off.");
            worker.addOperation("enableDebugging", "Turns debugging on.");
            worker.addOperation("disableDebugging", "Turns debugging off.");
            worker.addOperation("reset", "Resets the state of this MBean.");
            worker.addOperation("stop", "Stops this Worker thread.");
            worker.addOperation("suspend", "Suspends this Worker thread.");
            worker.addOperation("resume", "Resumes this Worker thread.");
            _server.registerMBean(worker, objName);
            trace("\tOBJECT NAME = " + objName);
            Thread t = new Thread(worker);
            t.start();
            if (role.equalsIgnoreCase(Supplier.ROLE)) {
                _numberOfSuppliers++;
            } 
            else if (role.equalsIgnoreCase(Consumer.ROLE)) {
                _numberOfConsumers++;
            }
        } catch (Exception e) {
            trace("Controller.main(): ERROR: " + "Could not register the Supplier MBean! Stack trace follows...");
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
    
}



