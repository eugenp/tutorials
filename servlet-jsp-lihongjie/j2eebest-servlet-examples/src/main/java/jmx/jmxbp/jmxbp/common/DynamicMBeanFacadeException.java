/*
 * DynamicMBeanFacadeException.java
 *
 * Created on August 16, 2002, 11:34 AM
 */

package jmxbp.common;

/**
 *
 * @author  unknown
 */
public class DynamicMBeanFacadeException extends java.lang.Exception {
    
    /**
     * Creates a new instance of <code>DynamicMBeanFacadeException</code> without detail message.
     */
    public DynamicMBeanFacadeException() {
    }
    
    
    /**
     * Constructs an instance of <code>DynamicMBeanFacadeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DynamicMBeanFacadeException(String msg) {
        super(msg);
    }
}
