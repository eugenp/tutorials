package com.baeldung.di.types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demo bean for XML injection, used by {@link DependencyInjectionTypesDemo}. It uses constructor and setter injection.
 * 
 * @author Donato Rimenti
 *
 */
public class XmlInjectionExample {

    /**
     * Logger.
     */
    private Logger LOG = LoggerFactory.getLogger(XmlInjectionExample.class);

    /**
     * Field injected through constructor injection.
     */
    private String constructorInjection;

    /**
     * Field injected through setter injection.
     */
    private String setterInjection;

    /**
     * Default constructor.
     */
    public XmlInjectionExample() {
        LOG.info("Default constructor called.");
    }

    /**
     * Constructor for constructor injection.
     * 
     * @param constructorInjection {@link #constructorInjection}
     */
    public XmlInjectionExample(String constructorInjection) {
        LOG.info("Setting [{}] into constructorInjection through constructor.", constructorInjection);
        this.constructorInjection = constructorInjection;
    }

    /**
     * Sets the {@link #setterInjection}. Used for setter injection.
     *
     * @param setterInjection the new {@link #setterInjection}
     */
    public void setSetterInjection(String setterInjection) {
        LOG.info("Setting [{}] into setterInjection through setter.", setterInjection);
        this.setterInjection = setterInjection;
    }

    /**
     * Sets the {@link #constructorInjection}.
     *
     * @param constructorInjection the new {@link #constructorInjection}
     */
    public void setConstructorInjection(String constructorInjection) {
        LOG.info("Setting [{}] into constructorInjection through setter. This will never be called.", constructorInjection);
        this.constructorInjection = constructorInjection;
    }

    /**
     * Gets the {@link #constructorInjection}.
     *
     * @return the {@link #constructorInjection}
     */
    public String getConstructorInjection() {
        return constructorInjection;
    }

    /**
     * Gets the {@link #setterInjection}.
     *
     * @return the {@link #setterInjection}
     */
    public String getSetterInjection() {
        return setterInjection;
    }

}
