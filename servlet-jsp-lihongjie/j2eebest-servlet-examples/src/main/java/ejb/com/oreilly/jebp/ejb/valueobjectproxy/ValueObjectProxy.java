package com.oreilly.jebp.ejb.valueobjectproxy;

import java.lang.reflect.*;
import java.util.HashMap;
import java.io.Serializable;

/**
 * Java Enterprise Best Practices, Chapter 2: EJB.
 * Using Value Object Proxy to implement large scale value object pattern.
 *
 * The value object proxy is a dynamic proxy class that acts as a generic
 * hashmap value object, but 
**/
public class ValueObjectProxy implements InvocationHandler, Serializable {
	/**
	 * The map of field names and their values.
	**/
	protected HashMap fieldMap;

	/**
	 * The interface that this proxy implements.
	**/
	protected Class valueObjectClass;

	protected ValueObjectProxy (Class valueObjectClass) {
		this.valueObjectClass = valueObjectClass;
		fieldMap = new HashMap();
	} // constructor

	/**
	 * Created a new value object proxy that implements the given interface.
	 *
	 * @param	valueObjectClass	interface to proxy.
	**/
	public static Object createValueObject (Class valueObjectClass) {
		return Proxy.newProxyInstance (
			valueObjectClass.getClassLoader(),
			new Class[] {valueObjectClass},
			new ValueObjectProxy(valueObjectClass));
	} // createValueObject

	/**
	 * InvocationHandler's invoke method - this will be called whenever
	 * a method on the interface is called.
	**/
	public Object invoke (Object proxy, Method method, Object[] args)
	throws ValueObjectException {
		String methodName = method.getName();

		if (methodName.startsWith ("get")) {
			// remove "get" to get the field name
			String fieldName = methodName.substring(3);

			// it’s a "get", so return the value
			if (!fieldMap.containsKey (fieldName))
				throw new ValueObjectException ("Field " + fieldName
				                                + " does not exist");
			return fieldMap.get(fieldName);

		} else if (methodName.startsWith ("set")) {
			// remove 'set' to get the field name
			String fieldName = methodName.substring(3);

			// put it into the hashmap
			// assume we received one argument in the set method
			fieldMap.put (fieldName, args[0]);
			return null; // nothing to return

		// it’s neither a get nor a set
		} else {
			throw new ValueObjectException ("Invalid method");
		}
	} // invoke
} // ValueObjectProxy