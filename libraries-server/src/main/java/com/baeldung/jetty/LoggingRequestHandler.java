package com.baeldung.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handler implementation which simply logs that a request has been received.
 * 
 * @author Donato Rimenti
 */
public class LoggingRequestHandler implements Handler {

    /**
     * Logger.
     */
    private final static Logger LOG = LoggerFactory.getLogger(LoggingRequestHandler.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.util.component.LifeCycle#addLifeCycleListener(org.
     * eclipse.jetty.util.component.LifeCycle.Listener)
     */
    @Override
    public void addLifeCycleListener(Listener arg0) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.util.component.LifeCycle#isFailed()
     */
    @Override
    public boolean isFailed() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.util.component.LifeCycle#isRunning()
     */
    @Override
    public boolean isRunning() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.util.component.LifeCycle#isStarted()
     */
    @Override
    public boolean isStarted() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.util.component.LifeCycle#isStarting()
     */
    @Override
    public boolean isStarting() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.util.component.LifeCycle#isStopped()
     */
    @Override
    public boolean isStopped() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.util.component.LifeCycle#isStopping()
     */
    @Override
    public boolean isStopping() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jetty.util.component.LifeCycle#removeLifeCycleListener(org.
     * eclipse.jetty.util.component.LifeCycle.Listener)
     */
    @Override
    public void removeLifeCycleListener(Listener arg0) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.util.component.LifeCycle#start()
     */
    @Override
    public void start() throws Exception {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.util.component.LifeCycle#stop()
     */
    @Override
    public void stop() throws Exception {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.server.Handler#destroy()
     */
    @Override
    public void destroy() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.server.Handler#getServer()
     */
    @Override
    public Server getServer() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
     * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(String arg0, Request arg1, HttpServletRequest arg2, HttpServletResponse arg3) throws IOException, ServletException {
        LOG.info("Received a new request");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jetty.server.Handler#setServer(org.eclipse.jetty.server.
     * Server)
     */
    @Override
    public void setServer(Server server) {
    }

}
