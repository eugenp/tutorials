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
package org.apache.jasper.el;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.servlet.jsp.el.ELException;
import javax.servlet.jsp.el.ELParseException;
import javax.servlet.jsp.el.Expression;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.FunctionMapper;
import javax.servlet.jsp.el.VariableResolver;

@Deprecated
public final class ExpressionEvaluatorImpl extends ExpressionEvaluator {

    private final ExpressionFactory factory;
    
    public ExpressionEvaluatorImpl(ExpressionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Expression parseExpression(String expression,
            @SuppressWarnings("rawtypes") // API does not use generics
            Class expectedType,
            FunctionMapper fMapper) throws ELException {
        try {
            ELContextImpl ctx =
                new ELContextImpl(ELContextImpl.getDefaultResolver());
            if (fMapper != null) {
                ctx.setFunctionMapper(new FunctionMapperImpl(fMapper));
            }
            ValueExpression ve = this.factory.createValueExpression(ctx, expression, expectedType);
            return new ExpressionImpl(ve);
        } catch (javax.el.ELException e) {
            throw new ELParseException(e.getMessage());
        }
    }

    @Override
    public Object evaluate(String expression,
            @SuppressWarnings("rawtypes") // API does not use generics
            Class expectedType,
            VariableResolver vResolver, FunctionMapper fMapper)
            throws ELException {
        return this.parseExpression(expression, expectedType, fMapper).evaluate(vResolver);
    }

}
