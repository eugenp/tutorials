package com.baeldung.di.types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Demo bean for autowired annotation injection, used by {@link DependencyInjectionTypesDemo}. It uses constructor, setter and field injection.
 * 
 * @author Donato Rimenti
 *
 */
@Component
public class AutowiredInjectionExample {

    /**
     * Logger.
     */
    private final static Logger LOG = LoggerFactory.getLogger(AutowiredInjectionExample.class);

    /**
     * Field injected through constructor injection.
     */
    private String constructorInjection;

    /**
     * Field injected through setter injection.
     */
    private String setterInjection;

    /**
     * Field injected through field injection.
     */
    @Autowired
    @Qualifier("colorOne")
    private String fieldInjection;

    /**
     * Default constructor.
     */
    public AutowiredInjectionExample() {
        LOG.info("Default constructor called.");
    }

    /**
     * Constructor for constructor injection.
     * 
     * @param constructorInjection {@link #constructorInjection}
     */
    @Autowired
    public AutowiredInjectionExample(@Qualifier("colorTwo") String constructorInjection) {
        LOG.info("Setting [{}] into constructorInjection through constructor.", constructorInjection);
        this.constructorInjection = constructorInjection;
    }

    /**
     * Sets the {@link #setterInjection}. Used for setter injection.
     *
     * @param setterInjection the new {@link #setterInjection}
     */
    @Autowired
    public void setSetterInjection(@Qualifier("colorThree") String setterInjection) {
        LOG.info("Setting [{}] into setterInjection through setter.", setterInjection);
        this.setterInjection = setterInjection;
    }

    /**
     * Sets {@link #constructorInjection}.
     *
     * @param constructorInjection the new {@link #constructorInjection}
     */
    public void setConstructorInjection(String constructorInjection) {
        LOG.info("Setting [{}] into constructorInjection through setter. This will never be called.", constructorInjection);
        this.constructorInjection = constructorInjection;
    }

    /**
     * Sets the {@link #fieldInjection}.
     *
     * @param fieldInjection the new {@link #fieldInjection}
     */
    public void setFieldInjection(String fieldInjection) {
        LOG.info("Setting [{}] into fieldInjection through setter. This will never be called.", fieldInjection);
        this.fieldInjection = fieldInjection;
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

    /**
     * Gets the {@link #fieldInjection}.
     *
     * @return the {@link #fieldInjection}
     */
    public String getFieldInjection() {
        return fieldInjection;
    }

}
