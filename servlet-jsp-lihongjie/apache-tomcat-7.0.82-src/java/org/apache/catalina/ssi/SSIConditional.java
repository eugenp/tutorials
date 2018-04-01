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
package org.apache.catalina.ssi;


import java.io.PrintWriter;
import java.text.ParseException;
/**
 * SSI command that handles all conditional directives.
 * 
 * @author Paul Speed
 * @author David Becker
 */
public class SSIConditional implements SSICommand {
    /**
     * @see SSICommand
     */
    @Override
    public long process(SSIMediator ssiMediator, String commandName,
            String[] paramNames, String[] paramValues, PrintWriter writer)
            throws SSIStopProcessingException {
        // Assume anything using conditionals was modified by it
        long lastModified = System.currentTimeMillis();
        // Retrieve the current state information
        SSIConditionalState state = ssiMediator.getConditionalState();
        if ("if".equalsIgnoreCase(commandName)) {
            // Do nothing if we are nested in a false branch
            // except count it
            if (state.processConditionalCommandsOnly) {
                state.nestingCount++;
                return lastModified;
            }
            state.nestingCount = 0;
            // Evaluate the expression
            if (evaluateArguments(paramNames, paramValues, ssiMediator)) {
                // No more branches can be taken for this if block
                state.branchTaken = true;
            } else {
                // Do not process this branch
                state.processConditionalCommandsOnly = true;
                state.branchTaken = false;
            }
        } else if ("elif".equalsIgnoreCase(commandName)) {
            // No need to even execute if we are nested in
            // a false branch
            if (state.nestingCount > 0) return lastModified;
            // If a branch was already taken in this if block
            // then disable output and return
            if (state.branchTaken) {
                state.processConditionalCommandsOnly = true;
                return lastModified;
            }
            // Evaluate the expression
            if (evaluateArguments(paramNames, paramValues, ssiMediator)) {
                // Turn back on output and mark the branch
                state.processConditionalCommandsOnly = false;
                state.branchTaken = true;
            } else {
                // Do not process this branch
                state.processConditionalCommandsOnly = true;
                state.branchTaken = false;
            }
        } else if ("else".equalsIgnoreCase(commandName)) {
            // No need to even execute if we are nested in
            // a false branch
            if (state.nestingCount > 0) return lastModified;
            // If we've already taken another branch then
            // disable output otherwise enable it.
            state.processConditionalCommandsOnly = state.branchTaken;
            // And in any case, it's safe to say a branch
            // has been taken.
            state.branchTaken = true;
        } else if ("endif".equalsIgnoreCase(commandName)) {
            // If we are nested inside a false branch then pop out
            // one level on the nesting count
            if (state.nestingCount > 0) {
                state.nestingCount--;
                return lastModified;
            }
            // Turn output back on
            state.processConditionalCommandsOnly = false;
            // Reset the branch status for any outer if blocks,
            // since clearly we took a branch to have gotten here
            // in the first place.
            state.branchTaken = true;
        } else {
            throw new SSIStopProcessingException();
            //throw new SsiCommandException( "Not a conditional command:" +
            // cmdName );
        }
        return lastModified;
    }


    /**
     * Retrieves the expression from the specified arguments and peforms the
     * necessary evaluation steps.
     */
    private boolean evaluateArguments(String[] names, String[] values,
            SSIMediator ssiMediator) throws SSIStopProcessingException {
        String expr = getExpression(names, values);
        if (expr == null) {
            throw new SSIStopProcessingException();
            //throw new SsiCommandException( "No expression specified." );
        }
        try {
            ExpressionParseTree tree = new ExpressionParseTree(expr,
                    ssiMediator);
            return tree.evaluateTree();
        } catch (ParseException e) {
            //throw new SsiCommandException( "Error parsing expression." );
            throw new SSIStopProcessingException();
        }
    }


    /**
     * Returns the "expr" if the arg name is appropriate, otherwise returns
     * null.
     */
    private String getExpression(String[] paramNames, String[] paramValues) {
        if ("expr".equalsIgnoreCase(paramNames[0])) return paramValues[0];
        return null;
    }
}