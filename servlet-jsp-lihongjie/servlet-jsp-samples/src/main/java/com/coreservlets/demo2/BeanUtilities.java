package com.coreservlets.demo2;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 自动填充Java bean 工具类
 */
public class BeanUtilities {

    public static void populateBean(Object formBean, HttpServletRequest request) {

        populateBean(formBean, request.getParameterMap());
    }

    /**
     * Populates a bean based on a Map: Map keys are the bean property names; Map values are the bean
     * property values. Type conversion is performed automatically as described above.
     * @param formBean
     * @param propertyMap
     */
    public static void populateBean(Object formBean, Map propertyMap) {
        try {
            BeanUtils.populate(formBean, propertyMap);
        } catch (Exception e) {
            System.out.println("出错了");
            e.printStackTrace();
            // Empty catch. The two possible exceptions are java.lang.IllegalAccessException and
            // java.lang.reflect.InvocationTargetException.
            // In both cases, just skip the bean operation.
        }

    }
}
