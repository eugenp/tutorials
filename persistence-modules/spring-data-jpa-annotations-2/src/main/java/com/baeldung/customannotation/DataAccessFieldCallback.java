package com.baeldung.customannotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

public final class DataAccessFieldCallback implements FieldCallback {

    private static Logger logger = LoggerFactory.getLogger(DataAccessFieldCallback.class);
    private static int AUTOWIRE_MODE = AutowireCapableBeanFactory.AUTOWIRE_BY_NAME;

    private static String ERROR_ENTITY_VALUE_NOT_SAME = "@DataAccess(entity) " + "value should have same type with injected generic type.";
    private static String WARN_NON_GENERIC_VALUE = "@DataAccess annotation assigned " + "to raw (non-generic) declaration. This will make your code less type-safe.";
    private static String ERROR_CREATE_INSTANCE = "Cannot create instance of " + "type '{}' or instance creation is failed because: {}";

    private ConfigurableListableBeanFactory configurableListableBeanFactory;
    private Object bean;

    public DataAccessFieldCallback(final ConfigurableListableBeanFactory bf, final Object bean) {
        configurableListableBeanFactory = bf;
        this.bean = bean;
    }

    @Override
    public void doWith(final Field field) throws IllegalArgumentException, IllegalAccessException {
        if (!field.isAnnotationPresent(DataAccess.class)) {
            return;
        }
        ReflectionUtils.makeAccessible(field);
        final Type fieldGenericType = field.getGenericType();
        // In this example, get actual "GenericDAO' type.
        final Class<?> generic = field.getType();
        final Class<?> classValue = field.getDeclaredAnnotation(DataAccess.class).entity();

        if (genericTypeIsValid(classValue, fieldGenericType)) {
            final String beanName = classValue.getSimpleName() + generic.getSimpleName();
            final Object beanInstance = getBeanInstance(beanName, generic, classValue);
            field.set(bean, beanInstance);
        } else {
            throw new IllegalArgumentException(ERROR_ENTITY_VALUE_NOT_SAME);
        }
    }

    /**
     * For example, if user write:
     * <pre>
     * &#064;DataAccess(entity=Person.class) 
     * private GenericDAO&lt;Account&gt; personGenericDAO;
     * </pre>
     * then this is should be failed.
     */
    public boolean genericTypeIsValid(final Class<?> clazz, final Type field) {
        if (field instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) field;
            final Type type = parameterizedType.getActualTypeArguments()[0];

            return type.equals(clazz);
        } else {
            logger.warn(WARN_NON_GENERIC_VALUE);
            return true;
        }
    }

    public final Object getBeanInstance(final String beanName, final Class<?> genericClass, final Class<?> paramClass) {
        Object daoInstance = null;
        if (!configurableListableBeanFactory.containsBean(beanName)) {
            logger.info("Creating new DataAccess bean named '{}'.", beanName);

            Object toRegister = null;
            try {
                final Constructor<?> ctr = genericClass.getConstructor(Class.class);
                toRegister = ctr.newInstance(paramClass);
            } catch (final Exception e) {
                logger.error(ERROR_CREATE_INSTANCE, genericClass.getTypeName(), e);
                throw new RuntimeException(e);
            }

            daoInstance = configurableListableBeanFactory.initializeBean(toRegister, beanName);
            configurableListableBeanFactory.autowireBeanProperties(daoInstance, AUTOWIRE_MODE, true);
            configurableListableBeanFactory.registerSingleton(beanName, daoInstance);
            logger.info("Bean named '{}' created successfully.", beanName);
        } else {
            daoInstance = configurableListableBeanFactory.getBean(beanName);
            logger.info("Bean named '{}' already exist used as current bean reference.", beanName);
        }
        return daoInstance;
    }
}
