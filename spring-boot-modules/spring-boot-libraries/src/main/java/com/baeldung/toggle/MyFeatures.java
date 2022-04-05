package com.baeldung.toggle;

import org.togglz.core.Feature;
import org.togglz.core.activation.SystemPropertyActivationStrategy;
import org.togglz.core.annotation.ActivationParameter;
import org.togglz.core.annotation.DefaultActivationStrategy;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum MyFeatures implements Feature {

    @Label("Employee Management Feature")
    @EnabledByDefault
    @DefaultActivationStrategy(id = SystemPropertyActivationStrategy.ID, parameters = { @ActivationParameter(name = SystemPropertyActivationStrategy.PARAM_PROPERTY_NAME, value = "employee.feature"),
            @ActivationParameter(name = SystemPropertyActivationStrategy.PARAM_PROPERTY_VALUE, value = "true") })
    EMPLOYEE_MANAGEMENT_FEATURE;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

}
