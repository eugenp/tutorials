package jmxbp.ex2;

import jmxbp.common.*;

/**
 * Base class for the sample application.
 * Implemented as a dynamic MBean. There are three
 * attributes:
 *
 * 1. NumberOfResets (int)
 * 2. TraceOn (boolean)
 * 3. DebugOn (boolean)
 *
 * and five operations:
 *
 * 1. enableTracing()
 * 2. disableTracing()
 * 3. enableDebugging()
 * 4. disableDebugging()
 * 5. reset() (abstract - must be implemented by subclasses)
 *
 * @author Steve Perry
 */
public abstract class Basic extends DynamicMBeanFacade {

    public Basic () {
        super();
    }

    private int _numberOfResets;
    public int getNumberOfResets () {
        return  _numberOfResets;
    }

    public void setNumberOfResets (int value) {
        _numberOfResets = value;
    }

    private boolean _traceOn;
    public boolean isTraceOn () {
        return  _traceOn;
    }

    private boolean _debugOn;
    public boolean isDebugOn () {
        return  _debugOn;
    }

    // operations
    public void enableTracing () {
        _traceOn = true;
    }
    public void disableTracing () {
        _traceOn = false;
    }
    public void enableDebugging () {
        _debugOn = true;
    }
    public void disableDebugging () {
        _debugOn = false;
    }
    public abstract void reset ();

    /**
     * Helper method. Takes the string representation of a class
     * and returns the corresponding Class object.
     */
    static Class getClassFromString (String className) throws ClassNotFoundException {
        Class ret = null;
        if (className.equals(Boolean.TYPE.getName()))
            ret = Boolean.TYPE; 
        else if (className.equals(Character.TYPE.getName()))
            ret = Character.TYPE; 
        else if (className.equals(Byte.TYPE.getName()))
            ret = Byte.TYPE; 
        else if (className.equals(Short.TYPE.getName()))
            ret = Short.TYPE; 
        else if (className.equals(Integer.TYPE.getName()))
            ret = Integer.TYPE; 
        else if (className.equals(Long.TYPE.getName()))
            ret = Long.TYPE; 
        else if (className.equals(Float.TYPE.getName()))
            ret = Float.TYPE; 
        else if (className.equals(Double.TYPE.getName()))
            ret = Double.TYPE; 
        else if (className.equals(Void.TYPE.getName()))
            ret = Void.TYPE;
        //
        // Not a primitive type, just load the class based
        /// on the name.
        //
        else 
            ret = Class.forName(className);
        return  ret;
    }
}



