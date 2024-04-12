/*
 * Copyright (c) 2019 PloyRef
 * Created by Seun Matt <smatt382@gmail.com>
 * on 19 - 2 - 2019
 */

package com.baeldung.enforcer;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

public class MyCustomRule implements EnforcerRule {

    public void execute(EnforcerRuleHelper enforcerRuleHelper) throws EnforcerRuleException {

        try {

            String groupId = (String) enforcerRuleHelper.evaluate("${project.groupId}");

            if (groupId == null || !groupId.startsWith("com.baeldung")) {
                throw new EnforcerRuleException("Project group id does not start with com.baeldung");
            }

        }
        catch (ExpressionEvaluationException ex ) {
            throw new EnforcerRuleException( "Unable to lookup an expression " + ex.getLocalizedMessage(), ex );
        }
    }

    public boolean isCacheable() {
        return false;
    }

    public boolean isResultValid(EnforcerRule enforcerRule) {
        return false;
    }

    public String getCacheId() {
        return null;
    }
}
