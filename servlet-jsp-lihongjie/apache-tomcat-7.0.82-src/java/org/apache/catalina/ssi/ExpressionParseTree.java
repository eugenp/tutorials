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


import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
/**
 * Represents a parsed expression.
 * 
 * @author Paul Speed
 */
public class ExpressionParseTree {
    /**
     * Contains the current set of completed nodes. This is a workspace for the
     * parser.
     */
    private LinkedList<Node> nodeStack = new LinkedList<Node>();
    /**
     * Contains operator nodes that don't yet have values. This is a workspace
     * for the parser.
     */
    private LinkedList<OppNode> oppStack = new LinkedList<OppNode>();
    /**
     * The root node after the expression has been parsed.
     */
    private Node root;
    /**
     * The SSIMediator to use when evaluating the expressions.
     */
    private SSIMediator ssiMediator;


    /**
     * Creates a new parse tree for the specified expression.
     */
    public ExpressionParseTree(String expr, SSIMediator ssiMediator)
            throws ParseException {
        this.ssiMediator = ssiMediator;
        parseExpression(expr);
    }


    /**
     * Evaluates the tree and returns true or false. The specified SSIMediator
     * is used to resolve variable references.
     */
    public boolean evaluateTree() {
        return root.evaluate();
    }


    /**
     * Pushes a new operator onto the opp stack, resolving existing opps as
     * needed.
     */
    private void pushOpp(OppNode node) {
        // If node is null then it's just a group marker
        if (node == null) {
            oppStack.add(0, node);
            return;
        }
        while (true) {
            if (oppStack.size() == 0) break;
            OppNode top = oppStack.get(0);
            // If the top is a spacer then don't pop
            // anything
            if (top == null) break;
            // If the top node has a lower precedence then
            // let it stay
            if (top.getPrecedence() < node.getPrecedence()) break;
            // Remove the top node
            oppStack.remove(0);
            // Let it fill its branches
            top.popValues(nodeStack);
            // Stick it on the resolved node stack
            nodeStack.add(0, top);
        }
        // Add the new node to the opp stack
        oppStack.add(0, node);
    }


    /**
     * Resolves all pending opp nodes on the stack until the next group marker
     * is reached.
     */
    private void resolveGroup() {
        OppNode top = null;
        while ((top = oppStack.remove(0)) != null) {
            // Let it fill its branches
            top.popValues(nodeStack);
            // Stick it on the resolved node stack
            nodeStack.add(0, top);
        }
    }


    /**
     * Parses the specified expression into a tree of parse nodes.
     */
    private void parseExpression(String expr) throws ParseException {
        StringNode currStringNode = null;
        // We cheat a little and start an artificial
        // group right away. It makes finishing easier.
        pushOpp(null);
        ExpressionTokenizer et = new ExpressionTokenizer(expr);
        while (et.hasMoreTokens()) {
            int token = et.nextToken();
            if (token != ExpressionTokenizer.TOKEN_STRING)
                currStringNode = null;
            switch (token) {
                case ExpressionTokenizer.TOKEN_STRING :
                    if (currStringNode == null) {
                        currStringNode = new StringNode(et.getTokenValue());
                        nodeStack.add(0, currStringNode);
                    } else {
                        // Add to the existing
                        currStringNode.value.append(" ");
                        currStringNode.value.append(et.getTokenValue());
                    }
                    break;
                case ExpressionTokenizer.TOKEN_AND :
                    pushOpp(new AndNode());
                    break;
                case ExpressionTokenizer.TOKEN_OR :
                    pushOpp(new OrNode());
                    break;
                case ExpressionTokenizer.TOKEN_NOT :
                    pushOpp(new NotNode());
                    break;
                case ExpressionTokenizer.TOKEN_EQ :
                    pushOpp(new EqualNode());
                    break;
                case ExpressionTokenizer.TOKEN_NOT_EQ :
                    pushOpp(new NotNode());
                    // Sneak the regular node in. The NOT will
                    // be resolved when the next opp comes along.
                    oppStack.add(0, new EqualNode());
                    break;
                case ExpressionTokenizer.TOKEN_RBRACE :
                    // Closeout the current group
                    resolveGroup();
                    break;
                case ExpressionTokenizer.TOKEN_LBRACE :
                    // Push a group marker
                    pushOpp(null);
                    break;
                case ExpressionTokenizer.TOKEN_GE :
                    pushOpp(new NotNode());
                    // Similar strategy to NOT_EQ above, except this
                    // is NOT less than
                    oppStack.add(0, new LessThanNode());
                    break;
                case ExpressionTokenizer.TOKEN_LE :
                    pushOpp(new NotNode());
                    // Similar strategy to NOT_EQ above, except this
                    // is NOT greater than
                    oppStack.add(0, new GreaterThanNode());
                    break;
                case ExpressionTokenizer.TOKEN_GT :
                    pushOpp(new GreaterThanNode());
                    break;
                case ExpressionTokenizer.TOKEN_LT :
                    pushOpp(new LessThanNode());
                    break;
                case ExpressionTokenizer.TOKEN_END :
                    break;
            }
        }
        // Finish off the rest of the opps
        resolveGroup();
        if (nodeStack.size() == 0) {
            throw new ParseException("No nodes created.", et.getIndex());
        }
        if (nodeStack.size() > 1) {
            throw new ParseException("Extra nodes created.", et.getIndex());
        }
        if (oppStack.size() != 0) {
            throw new ParseException("Unused opp nodes exist.", et.getIndex());
        }
        root = nodeStack.get(0);
    }

    /**
     * A node in the expression parse tree.
     */
    private abstract class Node {
        /**
         * Return true if the node evaluates to true.
         */
        public abstract boolean evaluate();
    }
    /**
     * A node the represents a String value
     */
    private class StringNode extends Node {
        StringBuilder value;
        String resolved = null;


        public StringNode(String value) {
            this.value = new StringBuilder(value);
        }


        /**
         * Resolves any variable references and returns the value string.
         */
        public String getValue() {
            if (resolved == null)
                resolved = ssiMediator.substituteVariables(value.toString());
            return resolved;
        }


        /**
         * Returns true if the string is not empty.
         */
        @Override
        public boolean evaluate() {
            return !(getValue().length() == 0);
        }


        @Override
        public String toString() {
            return value.toString();
        }
    }

    private static final int PRECEDENCE_NOT = 5;
    private static final int PRECEDENCE_COMPARE = 4;
    private static final int PRECEDENCE_LOGICAL = 1;

    /**
     * A node implementation that represents an operation.
     */
    private abstract class OppNode extends Node {
        /**
         * The left branch.
         */
        Node left;
        /**
         * The right branch.
         */
        Node right;


        /**
         * Returns a preference level suitable for comparison to other OppNode
         * preference levels.
         */
        public abstract int getPrecedence();


        /**
         * Lets the node pop its own branch nodes off the front of the
         * specified list. The default pulls two.
         */
        public void popValues(List<Node> values) {
            right = values.remove(0);
            left = values.remove(0);
        }
    }
    private final class NotNode extends OppNode {
        @Override
        public boolean evaluate() {
            return !left.evaluate();
        }


        @Override
        public int getPrecedence() {
            return PRECEDENCE_NOT;
        }


        /**
         * Overridden to pop only one value.
         */
        @Override
        public void popValues(List<Node> values) {
            left = values.remove(0);
        }


        @Override
        public String toString() {
            return left + " NOT";
        }
    }
    private final class AndNode extends OppNode {
        @Override
        public boolean evaluate() {
            if (!left.evaluate()) // Short circuit
                return false;
            return right.evaluate();
        }


        @Override
        public int getPrecedence() {
            return PRECEDENCE_LOGICAL;
        }


        @Override
        public String toString() {
            return left + " " + right + " AND";
        }
    }
    private final class OrNode extends OppNode {
        @Override
        public boolean evaluate() {
            if (left.evaluate()) // Short circuit
                return true;
            return right.evaluate();
        }


        @Override
        public int getPrecedence() {
            return PRECEDENCE_LOGICAL;
        }


        @Override
        public String toString() {
            return left + " " + right + " OR";
        }
    }
    private abstract class CompareNode extends OppNode {
        protected int compareBranches() {
            String val1 = ((StringNode)left).getValue();
            String val2 = ((StringNode)right).getValue();
            
            int val2Len = val2.length();
            if (val2Len > 1 && val2.charAt(0) == '/' &&
                    val2.charAt(val2Len - 1) == '/') {
                // Treat as a regular expression
                String expr = val2.substring(1, val2Len - 1);
                try {
                    Pattern pattern = Pattern.compile(expr);
                    // Regular expressions will only ever be used with EqualNode
                    // so return zero for equal and non-zero for not equal
                    if (pattern.matcher(val1).find()) {
                        return 0;
                    } else {
                        return -1;
                    }
                } catch (PatternSyntaxException pse) {
                    ssiMediator.log("Invalid expression: " + expr, pse);
                    return 0;
                }
            }
            return val1.compareTo(val2);
        }
    }
    private final class EqualNode extends CompareNode {
        @Override
        public boolean evaluate() {
            return (compareBranches() == 0);
        }


        @Override
        public int getPrecedence() {
            return PRECEDENCE_COMPARE;
        }


        @Override
        public String toString() {
            return left + " " + right + " EQ";
        }
    }
    private final class GreaterThanNode extends CompareNode {
        @Override
        public boolean evaluate() {
            return (compareBranches() > 0);
        }


        @Override
        public int getPrecedence() {
            return PRECEDENCE_COMPARE;
        }


        @Override
        public String toString() {
            return left + " " + right + " GT";
        }
    }
    private final class LessThanNode extends CompareNode {
        @Override
        public boolean evaluate() {
            return (compareBranches() < 0);
        }


        @Override
        public int getPrecedence() {
            return PRECEDENCE_COMPARE;
        }


        @Override
        public String toString() {
            return left + " " + right + " LT";
        }
    }
}