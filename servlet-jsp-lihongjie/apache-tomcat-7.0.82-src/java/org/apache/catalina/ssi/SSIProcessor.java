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


import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.catalina.util.IOTools;
/**
 * The entry point to SSI processing. This class does the actual parsing,
 * delegating to the SSIMediator, SSICommand, and SSIExternalResolver as
 * necessary[
 * 
 * @author Dan Sandberg
 * @author David Becker
 */
public class SSIProcessor {
    /** The start pattern */
    protected static final String COMMAND_START = "<!--#";
    /** The end pattern */
    protected static final String COMMAND_END = "-->";
    protected static final int BUFFER_SIZE = 4096;
    protected SSIExternalResolver ssiExternalResolver;
    protected HashMap<String,SSICommand> commands =
        new HashMap<String,SSICommand>();
    protected int debug;
    protected final boolean allowExec;


    public SSIProcessor(SSIExternalResolver ssiExternalResolver, int debug,
            boolean allowExec) {
        this.ssiExternalResolver = ssiExternalResolver;
        this.debug = debug;
        this.allowExec = allowExec;
        addBuiltinCommands();
    }


    protected void addBuiltinCommands() {
        addCommand("config", new SSIConfig());
        addCommand("echo", new SSIEcho());
        if (allowExec) {
            addCommand("exec", new SSIExec());
        }
        addCommand("include", new SSIInclude());
        addCommand("flastmod", new SSIFlastmod());
        addCommand("fsize", new SSIFsize());
        addCommand("printenv", new SSIPrintenv());
        addCommand("set", new SSISet());
        SSIConditional ssiConditional = new SSIConditional();
        addCommand("if", ssiConditional);
        addCommand("elif", ssiConditional);
        addCommand("endif", ssiConditional);
        addCommand("else", ssiConditional);
    }


    public void addCommand(String name, SSICommand command) {
        commands.put(name, command);
    }


    /**
     * Process a file with server-side commands, reading from reader and
     * writing the processed version to writer. NOTE: We really should be doing
     * this in a streaming way rather than converting it to an array first.
     * 
     * @param reader
     *            the reader to read the file containing SSIs from
     * @param writer
     *            the writer to write the file with the SSIs processed.
     * @return the most current modified date resulting from any SSI commands
     * @throws IOException
     *             when things go horribly awry. Should be unlikely since the
     *             SSICommand usually catches 'normal' IOExceptions.
     */
    public long process(Reader reader, long lastModifiedDate,
            PrintWriter writer) throws IOException {
        SSIMediator ssiMediator = new SSIMediator(ssiExternalResolver,
                lastModifiedDate, debug);
        StringWriter stringWriter = new StringWriter();
        IOTools.flow(reader, stringWriter);
        String fileContents = stringWriter.toString();
        stringWriter = null;
        int index = 0;
        boolean inside = false;
        StringBuilder command = new StringBuilder();
        try {
            while (index < fileContents.length()) {
                char c = fileContents.charAt(index);
                if (!inside) {
                    if (c == COMMAND_START.charAt(0)
                            && charCmp(fileContents, index, COMMAND_START)) {
                        inside = true;
                        index += COMMAND_START.length();
                        command.setLength(0); //clear the command string
                    } else {
                        if (!ssiMediator.getConditionalState().processConditionalCommandsOnly) {
                            writer.write(c);
                        }
                        index++;
                    }
                } else {
                    if (c == COMMAND_END.charAt(0)
                            && charCmp(fileContents, index, COMMAND_END)) {
                        inside = false;
                        index += COMMAND_END.length();
                        String strCmd = parseCmd(command);
                        if (debug > 0) {
                            ssiExternalResolver.log(
                                    "SSIProcessor.process -- processing command: "
                                            + strCmd, null);
                        }
                        String[] paramNames = parseParamNames(command, strCmd
                                .length());
                        String[] paramValues = parseParamValues(command,
                                strCmd.length(), paramNames.length);
                        //We need to fetch this value each time, since it may
                        // change
                        // during the loop
                        String configErrMsg = ssiMediator.getConfigErrMsg();
                        SSICommand ssiCommand =
                            commands.get(strCmd.toLowerCase(Locale.ENGLISH));
                        String errorMessage = null;
                        if (ssiCommand == null) {
                            errorMessage = "Unknown command: " + strCmd;
                        } else if (paramValues == null) {
                            errorMessage = "Error parsing directive parameters.";
                        } else if (paramNames.length != paramValues.length) {
                            errorMessage = "Parameter names count does not match parameter values count on command: "
                                    + strCmd;
                        } else {
                            // don't process the command if we are processing
                            // conditional
                            // commands only and the
                            // command is not conditional
                            if (!ssiMediator.getConditionalState().processConditionalCommandsOnly
                                    || ssiCommand instanceof SSIConditional) {
                                long lmd = ssiCommand.process(ssiMediator, strCmd,
                                               paramNames, paramValues, writer);
                                if (lmd > lastModifiedDate) {
                                    lastModifiedDate = lmd;
                                }                                    
                            }
                        }
                        if (errorMessage != null) {
                            ssiExternalResolver.log(errorMessage, null);
                            writer.write(configErrMsg);
                        }
                    } else {
                        command.append(c);
                        index++;
                    }
                }
            }
        } catch (SSIStopProcessingException e) {
            //If we are here, then we have already stopped processing, so all
            // is good
        }
        return lastModifiedDate;
    }


    /**
     * Parse a StringBuilder and take out the param type token. Called from
     * <code>requestHandler</code>
     * 
     * @param cmd
     *            a value of type 'StringBuilder'
     * @return a value of type 'String[]'
     */
    protected String[] parseParamNames(StringBuilder cmd, int start) {
        int bIdx = start;
        int i = 0;
        int quotes = 0;
        boolean inside = false;
        StringBuilder retBuf = new StringBuilder();
        while (bIdx < cmd.length()) {
            if (!inside) {
                while (bIdx < cmd.length() && isSpace(cmd.charAt(bIdx)))
                    bIdx++;
                if (bIdx >= cmd.length()) break;
                inside = !inside;
            } else {
                while (bIdx < cmd.length() && cmd.charAt(bIdx) != '=') {
                    retBuf.append(cmd.charAt(bIdx));
                    bIdx++;
                }
                retBuf.append('=');
                inside = !inside;
                quotes = 0;
                boolean escaped = false;
                for (; bIdx < cmd.length() && quotes != 2; bIdx++) {
                    char c = cmd.charAt(bIdx);
                    // Need to skip escaped characters
                    if (c == '\\' && !escaped) {
                        escaped = true;
                        continue;
                    }
                    if (c == '"' && !escaped) quotes++;
                    escaped = false;
                }
            }
        }
        StringTokenizer str = new StringTokenizer(retBuf.toString(), "=");
        String[] retString = new String[str.countTokens()];
        while (str.hasMoreTokens()) {
            retString[i++] = str.nextToken().trim();
        }
        return retString;
    }


    /**
     * Parse a StringBuilder and take out the param token. Called from
     * <code>requestHandler</code>
     * 
     * @param cmd
     *            a value of type 'StringBuilder'
     * @return a value of type 'String[]'
     */
    protected String[] parseParamValues(StringBuilder cmd, int start, int count) {
        int valIndex = 0;
        boolean inside = false;
        String[] vals = new String[count];
        StringBuilder sb = new StringBuilder();
        char endQuote = 0;
        for (int bIdx = start; bIdx < cmd.length(); bIdx++) {
            if (!inside) {
                while (bIdx < cmd.length() && !isQuote(cmd.charAt(bIdx)))
                    bIdx++;
                if (bIdx >= cmd.length()) break;
                inside = !inside;
                endQuote = cmd.charAt(bIdx);
            } else {
                boolean escaped = false;
                for (; bIdx < cmd.length(); bIdx++) {
                    char c = cmd.charAt(bIdx);
                    // Check for escapes
                    if (c == '\\' && !escaped) {
                        escaped = true;
                        continue;
                    }
                    // If we reach the other " then stop
                    if (c == endQuote && !escaped) break;
                    // Since parsing of attributes and var
                    // substitution is done in separate places,
                    // we need to leave escape in the string
                    if (c == '$' && escaped) sb.append('\\');
                    escaped = false;
                    sb.append(c);
                }
                // If we hit the end without seeing a quote
                // the signal an error
                if (bIdx == cmd.length()) return null;
                vals[valIndex++] = sb.toString();
                sb.delete(0, sb.length()); // clear the buffer
                inside = !inside;
            }
        }
        return vals;
    }


    /**
     * Parse a StringBuilder and take out the command token. Called from
     * <code>requestHandler</code>
     * 
     * @param cmd
     *            a value of type 'StringBuilder'
     * @return a value of type 'String', or null if there is none
     */
    private String parseCmd(StringBuilder cmd) {
        int firstLetter = -1;
        int lastLetter = -1;
        for (int i = 0; i < cmd.length(); i++) {
            char c = cmd.charAt(i);
            if (Character.isLetter(c)) {
                if (firstLetter == -1) {
                    firstLetter = i;
                }
                lastLetter = i;
            } else if (isSpace(c)) {
                if (lastLetter > -1) {
                    break;
                }
            } else {
                break;
            }
        }
        if (firstLetter == -1) {
            return "";
        } else {
            return cmd.substring(firstLetter, lastLetter + 1);
        }
    }


    protected boolean charCmp(String buf, int index, String command) {
        return buf.regionMatches(index, command, 0, command.length());
    }


    protected boolean isSpace(char c) {
        return c == ' ' || c == '\n' || c == '\t' || c == '\r';
    }
    
    protected boolean isQuote(char c) {
        return c == '\'' || c == '\"' || c == '`';
    }
}