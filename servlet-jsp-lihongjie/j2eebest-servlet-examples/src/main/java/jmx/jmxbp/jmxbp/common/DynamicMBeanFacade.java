/*
 * DynamicMBeanFacade.java
 *
 * Created on August 16, 2002, 11:21 AM
 */

package jmxbp.common;

import javax.management.DynamicMBean;
import javax.management.MBeanInfo;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationBroadcaster;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanException;
import javax.management.AttributeNotFoundException;
import javax.management.ReflectionException;

import java.lang.reflect.Method;
/**
 *
 * @author  unknown
 */
public class DynamicMBeanFacade implements DynamicMBean {
    
    private Object _managedResource;
    private MBeanInfo _mbi;
    public DynamicMBeanFacade(Object managedResource, String description) throws DynamicMBeanFacadeException{
        if (managedResource == null)
          throw new DynamicMBeanFacadeException("The managedResource reference cannot be null!");

        _managedResource = managedResource;

        // Create an empty MBeanInfo object so we're not constantly
        /// checking "if != null" while dynamically building the
        /// management interface. . .
        _mbi = new MBeanInfo(
            _managedResource.getClass().getName(),
           description,
            new MBeanAttributeInfo[0],
            new MBeanConstructorInfo[0],
            new MBeanOperationInfo[0],
            new MBeanNotificationInfo[0]      
        );
    }
    /**
     * This constructor is provided so that this class can be subclassed.
     */
    public DynamicMBeanFacade() {
        _managedResource = this;
        // Create an empty MBeanInfo object so we're not constantly
        /// checking "if != null" while dynamically building the
        /// management interface. . .
        _mbi = new MBeanInfo(
            _managedResource.getClass().getName(),
           _managedResource.getClass().getName(),
            new MBeanAttributeInfo[0],
            new MBeanConstructorInfo[0],
            new MBeanOperationInfo[0],
            new MBeanNotificationInfo[0]      
        );
    }
    /** 
     * Creates a new instance of DynamicMBeanFacade
     */
    public DynamicMBeanFacade(Object managedResource) throws DynamicMBeanFacadeException {
        this(managedResource, managedResource.getClass().getName());
    }
    
    public synchronized void addAttribute(String name, String description) 
        throws DynamicMBeanFacadeException {
        
        if (name == null || name.equals(""))
          throw new DynamicMBeanFacadeException("Name cannot be null!");

//        trace("DynamicMBeanFacade.addAttribute(): INFO: Looking for attribute \'" + name + "\'");
        Class current = _managedResource.getClass();
        boolean isReadable = false;
        boolean isWritable = false;
        boolean isIs = false;
        String type = null;
        Method[] methods = current.getMethods();
        for (int aa = 0; aa < methods.length; aa++) {
            String methodName = methods[aa].getName();
            if (methodName.equals("get" + name)) {
                isReadable = true;
                type = methods[aa].getReturnType().getName();
//                trace("\tGetter found: \'" + methodName + "\', type = \'" + type + "\'");
            } else if (methodName.equals("set" + name)) {
                Class[] parmTypes = methods[aa].getParameterTypes();
                // a setter can only have one parameter
                if (parmTypes.length > 1) 
                    continue;
                else type = parmTypes[0].getName();
                isWritable = true;
//                trace("\tSetter found: \'" + methodName + "\', type = \'" + type + "\'");
            } else if (methodName.equals("is" + name)) {
                type = methods[aa].getReturnType().getName();
                if (type.equals(Boolean.TYPE.getName())) {
//                    trace("\tGetter found: \'" + methodName + "\', type = \'" + type + "\'");
                    isReadable = true;
                    isIs = true;
                } else type = null;
            }
        }

        if (!isReadable && !isWritable)
          throw new DynamicMBeanFacadeException("Attribute \'" + name + "\' not found.");

        MBeanAttributeInfo newAttribute = new MBeanAttributeInfo(
          name, type, description, isReadable, isWritable, isIs
        );
//        trace("\tMetadata: [name=\'" + name + "], [type=\'" + type + 
//            "\'], [isReadable=" + isReadable + "], [isWritable=" + isWritable +
//            "], [isIs=" + isIs + "]");
        // Preserve existing attributes
        MBeanAttributeInfo[] attributes = _mbi.getAttributes();
        int oldLength = attributes.length;
        MBeanAttributeInfo[] newAttributes = new MBeanAttributeInfo[oldLength + 1];
        System.arraycopy(attributes,0,newAttributes,0,oldLength);
        newAttributes[oldLength] = newAttribute;
        // Recreate MBeanInfo, with new attribute metadata array
        MBeanInfo newMbi = new MBeanInfo(_mbi.getClassName(), _mbi.getDescription(),
           newAttributes,
           _mbi.getConstructors(),
           _mbi.getOperations(),
           _mbi.getNotifications()
        );
        _mbi = newMbi;
    }
    public synchronized void removeAttribute(String name)
        throws DynamicMBeanFacadeException {
        if (name == null || name.equals(""))
          throw new DynamicMBeanFacadeException("Name cannot be null!");

        MBeanAttributeInfo[] attributes = _mbi.getAttributes();
        MBeanAttributeInfo[] newAttributes =
          new MBeanAttributeInfo[attributes.length - 1]; // optimistic
        int newIndex = 0;
        try {
          for (int aa = 0; aa < attributes.length; aa++) {
            // copy in all metadata except what we're looking for. . .
            if (!(attributes[aa].getName().equals(name))) {
              newAttributes[newIndex++] = attributes[aa];
            }
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          throw new DynamicMBeanFacadeException("Attribute not found.");
        }
        MBeanInfo newMbi = new MBeanInfo(_mbi.getClassName(), _mbi.getDescription(),
           newAttributes,
           _mbi.getConstructors(),
           _mbi.getOperations(),
           _mbi.getNotifications()
        );
        _mbi = newMbi;
    }
    public synchronized void addOperation(String name, String description)
        throws DynamicMBeanFacadeException {
        if (name == null || name.equals(""))
          throw new DynamicMBeanFacadeException("Name cannot be null!");

//        trace("DynamicMBeanFacade.addOperation(): INFO: Looking for operation \'" + name + "\'");
        Class current = _managedResource.getClass();
        String	returnType = null;
        Method[] methods = current.getMethods();
        Method theMethod = null;
        for (int aa = 0; aa < methods.length; aa++) {
          if (methods[aa].getName().equals(name)) {
//            trace("\tFound method: \'" + methods[aa].getName() + "\'");
            theMethod = methods[aa];
            break;  // only take the first one encountered. . .
          }
        }
        if (theMethod == null)
          throw new DynamicMBeanFacadeException("Operation not found.");
        // Now create operation metadata
        MBeanOperationInfo newOp =
          new MBeanOperationInfo(description, theMethod);
        MBeanOperationInfo[] ops = _mbi.getOperations();
        MBeanOperationInfo[] newOps = 
          new MBeanOperationInfo[ops.length + 1];
        System.arraycopy(ops, 0, newOps, 0, ops.length);
        newOps[ops.length] = newOp;
        // Recreate MBeanInfo, with new operation metadata array
        MBeanInfo newMbi = new MBeanInfo(
           _mbi.getClassName(), _mbi.getDescription(),
           _mbi.getAttributes(),
           _mbi.getConstructors(), 
           newOps,
           _mbi.getNotifications()
        );
        _mbi = newMbi;
    }
    
    public synchronized void removeOperation(String name)
        throws DynamicMBeanFacadeException {
        if (name == null || name.equals(""))
          throw new DynamicMBeanFacadeException("Name cannot be null!");

        MBeanOperationInfo[] ops = _mbi.getOperations();
        MBeanOperationInfo[] newOps =
          new MBeanOperationInfo[ops.length - 1]; // optimistic
        int newIndex = 0;
        try {
          for (int aa = 0; aa < ops.length; aa++) {
            // copy in all metadata except what we're looking for. . .
            if (!(ops[aa].getName().equals(name))) {
              newOps[newIndex++] = (MBeanOperationInfo)ops[aa].clone();
            }
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          throw new DynamicMBeanFacadeException("Operation not found.");
        }
        MBeanInfo newMbi = new MBeanInfo(
           _mbi.getClassName(), _mbi.getDescription(),
           _mbi.getAttributes(),
           _mbi.getConstructors(), 
           newOps,
           _mbi.getNotifications()
        );
        _mbi = newMbi;
    }
    public synchronized void addNotification(String type, String description) 
        throws DynamicMBeanFacadeException {
        if (type == null || type.equals(""))
          throw new DynamicMBeanFacadeException("Name cannot be null!");

        if (!(_managedResource instanceof NotificationBroadcaster))
          throw new DynamicMBeanFacadeException(
            "Managed resource does not implement NotificationBroadcaster!");

        MBeanNotificationInfo notif = new MBeanNotificationInfo(
          new String[] {type},
          "javax.management.Notification",
          description);

        MBeanNotificationInfo[] notifs = _mbi.getNotifications();
        MBeanNotificationInfo[] newNotifs =
          new MBeanNotificationInfo[notifs.length + 1];
        System.arraycopy(notifs, 0, newNotifs, 0, notifs.length);
        newNotifs[notifs.length] = notif;

        // Recreate MBeanInfo, with new notification metadata array
        MBeanInfo newMbi = new MBeanInfo(
           _mbi.getClassName(), _mbi.getDescription(),
           _mbi.getAttributes(),
           _mbi.getConstructors(),
           _mbi.getOperations(),
           newNotifs
        );
        _mbi = newMbi;
    }
    public synchronized void removeNotification(String type)
        throws DynamicMBeanFacadeException {
        if (type == null || type.equals(""))
          throw new DynamicMBeanFacadeException("Name cannot be null!");

        MBeanNotificationInfo[] notifs = _mbi.getNotifications();
        MBeanNotificationInfo[] newNotifs =
          new MBeanNotificationInfo[notifs.length - 1];
        int newIndex = 0;
        try {
          for (int aa = 0; aa < notifs.length; aa++) {
            // copy in all metadata except what we're looking for. . .
            String[] types = notifs[aa].getNotifTypes();
            if (!(types[0].equals(type))) {
              newNotifs[newIndex++] = notifs[aa];
            }
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          throw new DynamicMBeanFacadeException(
            "Notification type not found.");
        }
        MBeanInfo newMbi = new MBeanInfo(
           _mbi.getClassName(), _mbi.getDescription(),
           _mbi.getAttributes(),
           _mbi.getConstructors(),
           _mbi.getOperations(),
           newNotifs
        );
        _mbi = newMbi;
    }
    //
    // DynamicMBean implementation
    //
    public Object getAttribute(String attribute) throws javax.management.AttributeNotFoundException, javax.management.MBeanException, javax.management.ReflectionException {
        Object ret = null;
        Object[] params = new Object[0];
        String[] signature = new String[0];
        try {
            ret = this.invoke("get" + attribute, params, signature);
        } catch (MBeanException notGet) {
            try {
                ret = this.invoke("is" + attribute, params, signature);
            } catch (MBeanException notFound) {
                throw new AttributeNotFoundException("Attribute \'" + attribute + "\' not found.");
            }
        }
        return ret;
    }
    
    public javax.management.AttributeList getAttributes(String[] attributes) {
        AttributeList ret = new AttributeList();
        for (int aa = 0; aa < attributes.length; aa++) {
          try {
            Object value = this.getAttribute(attributes[aa]);
            ret.add(new Attribute(attributes[aa], value));
          } catch (Exception e) {
             // handle, but not by throwing another exception. . .
          }
        }
        return ret;
    }
    
    public javax.management.MBeanInfo getMBeanInfo() {
        return _mbi;
    }
    
    public Object invoke(String actionName, Object[] params, String[] signature)  throws javax.management.MBeanException, javax.management.ReflectionException {
        Object ret = null; // return code
        try {
          Class[] sig = new Class[signature.length];
          for (int bb = 0; bb < signature.length; bb++)
            sig[bb] = this.getClassFromClassName(signature[bb]);
          Class mr = _managedResource.getClass();
          Method method = mr.getMethod(actionName, sig);
          ret = method.invoke(_managedResource, params);
        } catch (Exception e) {
          throw new MBeanException(e);
        }
        return ret;
    }
    
    public void setAttribute(javax.management.Attribute attribute) throws javax.management.AttributeNotFoundException, javax.management.InvalidAttributeValueException, javax.management.MBeanException, javax.management.ReflectionException {
        boolean attributeFound = false;
        try {
          Object[] value = { attribute.getValue() };
          String[] signature = { value.getClass().getName() };
          this.invoke(attribute.getName(), value, signature);
        } catch (MBeanException e) {
          throw new AttributeNotFoundException("Attribute \'" + attribute.getName() + "\' not found.");
        }
    }
    
    public javax.management.AttributeList setAttributes(javax.management.AttributeList attributes) {
        AttributeList ret = new AttributeList();
        for (int aa = 0; aa < attributes.size(); aa++) {
          try {
            Attribute attribute = (Attribute)attributes.get(aa);
            this.setAttribute(attribute);

            String attName = attribute.getName();
            Object attValue = attribute.getValue();
            ret.add(new Attribute(attName, attValue));
          } catch (Exception e) {
             // handle, but not by throwing another exception. . .
          }
        }
        return ret;
    }
    
  /* 
   * Helper class to handle getting Class objects for primitive
   * types.
   */
  private Class getClassFromClassName(String className)
    throws ClassNotFoundException {

    Class ret = null;

    // see if className is one of the primitive types
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
    //
    // and so on and so forth for all other primitive types. . .
    //
    else
      // not a primitive type, just load the class
      ret = Class.forName(className);
    return ret;
  }
  
    private void trace (String message) {
//        System.out.println(message);
        traceLog(message);
    }

    private void trace (Throwable t) {
        traceLog(t);
    }
    private void traceLog (Throwable t) {
        _logger.write(t);
    }
    MessageLog _logger = new MessageLog();

    private void traceLog (String message) {
        _logger.write(message);
    }
}
