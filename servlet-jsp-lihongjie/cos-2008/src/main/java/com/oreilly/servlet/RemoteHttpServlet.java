// Copyright (C) 1998-2001 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package com.oreilly.servlet;

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


/** 
 * A superclass for any HTTP servlet that wishes to act as an RMI server.
 * RemoteHttpServlet begins listening for RMI calls in its 
 * <tt>init()</tt> method and stops listening in its <tt>destroy()</tt> 
 * method.  To register itself it uses the registry on the local machine 
 * on the port determined by <tt>getRegistryPort()</tt>.  It registers
 * under the name determined by <tt>getRegistryName()</tt>.  
 *
 * @see com.oreilly.servlet.RemoteDaemonHttpServlet
 *
 * @author <b>Jason Hunter</b>, Copyright &#169; 1998
 * @version 1.0, 98/09/18
 */
public abstract class RemoteHttpServlet extends HttpServlet
                                        implements Remote {
  /**
   * The registry for the servlet
   */
  protected Registry registry;

  /**
   * Begins the servlet's RMI operations.  Causes the servlet to export
   * itself and then bind itself to the registry.  Logs any errors.
   * Subclasses that override this method must be sure to first call 
   * <tt>super.init(config)</tt>.
   * 
   * @param config the servlet config
   * @exception ServletException if a servlet exception occurs
   */
  public void init(ServletConfig config) throws ServletException {
    super.init(config);
    try {
      UnicastRemoteObject.exportObject(this);
      bind();
    }
    catch (RemoteException e) {
      log("Problem binding to RMI registry: " + e.getMessage());
    }
  }

  /**
   * Halts the servlet's RMI operations.  Causes the servlet to
   * unbind itself from the registry.  Logs any errors.  Subclasses that 
   * override this method must be sure to first call <tt>super.destroy()</tt>.
   */
  public void destroy() {
    unbind();
  }

  /**
   * Returns the name under which the servlet should be bound in the
   * registry.  By default the name is the servlet's class name.  This
   * can be overridden with the <tt>registryName</tt> init parameter.
   *
   * @return the name under which the servlet should be bound in the registry
   */
  protected String getRegistryName() {
    // First name choice is the "registryName" init parameter
    String name = getInitParameter("registryName");
    if (name != null) return name;

    // Fallback choice is the name of this class
    return this.getClass().getName();
  }

  /**
   * Returns the port where the registry should be running.  By default 
   * the port is the default registry port (1099).  This can be 
   * overridden with the <tt>registryPort</tt> init parameter.
   *
   * @return the port for the registry
   */
  protected int getRegistryPort() {
    // First port choice is the "registryPort" init parameter
    try { return Integer.parseInt(getInitParameter("registryPort")); }

    // Fallback choice is the default registry port (1099)
    catch (NumberFormatException e) { return Registry.REGISTRY_PORT; }
  }

  /**
   * Binds the servlet to the registry.  Creates the registry if necessary.
   * Logs any errors.
   */
  protected void bind() {
    // Try to find the appropriate registry already running
    try {
      registry = LocateRegistry.getRegistry(getRegistryPort());
      registry.list();  // Verify it's alive and well
    }
    catch (Exception e) {
      // Couldn't get a valid registry
      registry = null;
    }

    // If we couldn't find it, we need to create it.
    // (Equivalent to running "rmiregistry")
    if (registry == null) {
      try {
        registry = LocateRegistry.createRegistry(getRegistryPort());
      }
      catch (Exception e) { 
        log("Could not get or create RMI registry on port " +
            getRegistryPort() + ": " + e.getMessage());
        return;
      }
    }

    // If we get here, we must have a valid registry.
    // Now register this servlet instance with that registry.
    try {
      registry.rebind(getRegistryName(), this);
    }
    catch (Exception e) {
      log("Could not bind to RMI registry: " + e.getMessage());
      return;
    }
  }

  /**
   * Unbinds the servlet from the registry.
   * Logs any errors.
   */
  protected void unbind() {
    try {
      if (registry != null) registry.unbind(getRegistryName());
    }
    catch (Exception e) {
      log("Problem unbinding from RMI registry: " + e.getMessage());
    }
  }
}
