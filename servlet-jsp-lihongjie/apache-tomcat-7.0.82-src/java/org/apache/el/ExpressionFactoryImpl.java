/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.el;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import org.apache.el.lang.ELSupport;
import org.apache.el.lang.ExpressionBuilder;
import org.apache.el.util.MessageFactory;


/**
 * @see javax.el.ExpressionFactory
 * 
 * @author Jacob Hookom [jacob@hookom.net]
 */
public class ExpressionFactoryImpl extends ExpressionFactory {

    /**
     * 
     */
    public ExpressionFactoryImpl() {
        super();
    }

    @Override
    public Object coerceToType(Object obj, Class<?> type) {
        return ELSupport.coerceToType(obj, type);
    }

    @Override
    public MethodExpression createMethodExpression(ELContext context,
            String expression, Class<?> expectedReturnType,
            Class<?>[] expectedParamTypes) {
        ExpressionBuilder builder = new ExpressionBuilder(expression, context);
        return builder.createMethodExpression(expectedReturnType,
                expectedParamTypes);
    }

    @Override
    public ValueExpression createValueExpression(ELContext context,
            String expression, Class<?> expectedType) {
        if (expectedType == null) {
            throw new NullPointerException(MessageFactory
                    .get("error.value.expectedType"));
        }
        ExpressionBuilder builder = new ExpressionBuilder(expression, context);
        return builder.createValueExpression(expectedType);
    }

    @Override
    public ValueExpression createValueExpression(Object instance,
            Class<?> expectedType) {
        if (expectedType == null) {
            throw new NullPointerException(MessageFactory
                    .get("error.value.expectedType"));
        }
        return new ValueExpressionLiteral(instance, expectedType);
    }
}
