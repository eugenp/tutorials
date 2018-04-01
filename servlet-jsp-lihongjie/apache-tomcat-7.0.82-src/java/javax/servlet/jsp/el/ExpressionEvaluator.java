/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.servlet.jsp.el;

/**
 * <p>
 * The abstract base class for an expression-language evaluator. Classes that
 * implement an expression language expose their functionality via this abstract
 * class.
 * </p>
 * <p>
 * An instance of the ExpressionEvaluator can be obtained via the JspContext /
 * PageContext
 * </p>
 * <p>
 * The parseExpression() and evaluate() methods must be thread-safe. That is,
 * multiple threads may call these methods on the same ExpressionEvaluator
 * object simultaneously. Implementations should synchronize access if they
 * depend on transient state. Implementations should not, however, assume that
 * only one object of each ExpressionEvaluator type will be instantiated; global
 * caching should therefore be static.
 * </p>
 * <p>
 * Only a single EL expression, starting with '${' and ending with '}', can be
 * parsed or evaluated at a time. EL expressions cannot be mixed with static
 * text. For example, attempting to parse or evaluate "
 * <code>abc${1+1}def${1+1}ghi</code>" or even "<code>${1+1}${1+1}</code>" will
 * cause an <code>ELException</code> to be thrown.
 * </p>
 * <p>
 * The following are examples of syntactically legal EL expressions:
 * </p>
 * <ul>
 * <li><code>${person.lastName}</code></li>
 * <li><code>${8 * 8}</code></li>
 * <li><code>${my:reverse('hello')}</code></li>
 * </ul>
 * 
 * @since 2.0
 * @deprecated As of JSP 2.1, replaced by javax.el.ExpressionFactory
 */
@SuppressWarnings("dep-ann")
// TCK signature test fails with annotation
public abstract class ExpressionEvaluator {

    /**
     * Prepare an expression for later evaluation. This method should perform
     * syntactic validation of the expression; if in doing so it detects errors,
     * it should raise an ELParseException.
     * 
     * @param expression
     *            The expression to be evaluated.
     * @param expectedType
     *            The expected type of the result of the evaluation
     * @param fMapper
     *            A FunctionMapper to resolve functions found in the expression.
     *            It can be null, in which case no functions are supported for
     *            this invocation. The ExpressionEvaluator must not hold on to
     *            the FunctionMapper reference after returning from
     *            <code>parseExpression()</code>. The <code>Expression</code>
     *            object returned must invoke the same functions regardless of
     *            whether the mappings in the provided
     *            <code>FunctionMapper</code> instance change between calling
     *            <code>ExpressionEvaluator.parseExpression()</code> and
     *            <code>Expression.evaluate()</code>.
     * @return The Expression object encapsulating the arguments.
     * @exception ELException
     *                Thrown if parsing errors were found.
     */
    public abstract Expression parseExpression(String expression,
            @SuppressWarnings("rawtypes")// TCK signature fails with generics
            Class expectedType, FunctionMapper fMapper) throws ELException;

    /**
     * Evaluates an expression. This method may perform some syntactic
     * validation and, if so, it should raise an ELParseException error if it
     * encounters syntactic errors. EL evaluation errors should cause an
     * ELException to be raised.
     * 
     * @param expression
     *            The expression to be evaluated.
     * @param expectedType
     *            The expected type of the result of the evaluation
     * @param vResolver
     *            A VariableResolver instance that can be used at runtime to
     *            resolve the name of implicit objects into Objects.
     * @param fMapper
     *            A FunctionMapper to resolve functions found in the expression.
     *            It can be null, in which case no functions are supported for
     *            this invocation.
     * @return The result of the expression evaluation.
     * @exception ELException
     *                Thrown if the expression evaluation failed.
     */
    public abstract Object evaluate(
            String expression,
            @SuppressWarnings("rawtypes")// TCK signature fails with generics
            Class expectedType, VariableResolver vResolver,
            FunctionMapper fMapper) throws ELException;
}
