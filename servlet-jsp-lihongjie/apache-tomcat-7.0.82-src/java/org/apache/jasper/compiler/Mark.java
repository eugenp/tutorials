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
package org.apache.jasper.compiler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;

import org.apache.jasper.JspCompilationContext;

/**
 * Mark represents a point in the JSP input. 
 *
 * @author Anil K. Vijendran
 */
final class Mark {

    // position within current stream
    int cursor, line, col;

    // directory of file for current stream
    String baseDir;

    // current stream
    char[] stream = null;

    // fileid of current stream
    private int fileId;

    // name of the current file
    private String fileName;

    /*
     * stack of stream and stream state of streams that have included
     * current stream
     */
    private Stack<IncludeState> includeStack = null;

    // encoding of current file
    private String encoding = null;

    // reader that owns this mark (so we can look up fileid's)
    private JspReader reader;

    private JspCompilationContext ctxt;

    /**
     * Constructor
     *
     * @param reader JspReader this mark belongs to
     * @param inStream current stream for this mark
     * @param fileId id of requested jsp file
     * @param name JSP file name
     * @param inBaseDir base directory of requested jsp file
     * @param inEncoding encoding of current file
     */
    Mark(JspReader reader, char[] inStream, int fileId, String name,
         String inBaseDir, String inEncoding) {

        this.reader = reader;
        this.ctxt = reader.getJspCompilationContext();
        this.stream = inStream;
        this.cursor = 0;
        this.line = 1;
        this.col = 1;
        this.fileId = fileId;
        this.fileName = name;
        this.baseDir = inBaseDir;
        this.encoding = inEncoding;
        this.includeStack = new Stack<IncludeState>();
    }


    /**
     * Constructor
     */
    Mark(Mark other) {
       init(other, false);
    }

    void update(int cursor, int line, int col) {
        this.cursor = cursor;
        this.line = line;
        this.col = col;
    }

    void init(Mark other, boolean singleFile) {
        this.cursor = other.cursor;
        this.line = other.line;
        this.col = other.col;

        if (!singleFile) {
            this.reader = other.reader;
            this.ctxt = other.ctxt;
            this.stream = other.stream;
            this.fileId = other.fileId;
            this.fileName = other.fileName;
            this.baseDir = other.baseDir;
            this.encoding = other.encoding;

            if (includeStack == null) {
                includeStack = new Stack<IncludeState>();
            } else {
                includeStack.clear();
            }
            for (int i = 0; i < other.includeStack.size(); i++ ) {
                includeStack.addElement(other.includeStack.elementAt(i));
            }
        }
    }


    /**
     * Constructor
     */    
    Mark(JspCompilationContext ctxt, String filename, int line, int col) {

        this.reader = null;
        this.ctxt = ctxt;
        this.stream = null;
        this.cursor = 0;
        this.line = line;
        this.col = col;
        this.fileId = -1;
        this.fileName = filename;
        this.baseDir = "le-basedir";
        this.encoding = "le-endocing";
        this.includeStack = null;
    }


    /**
     * Sets this mark's state to a new stream.
     * It will store the current stream in it's includeStack.
     *
     * @param inStream new stream for mark
     * @param inFileId id of new file from which stream comes from
     * @param inBaseDir directory of file
     * @param inEncoding encoding of new file
     */
    public void pushStream(char[] inStream, int inFileId, String name,
                           String inBaseDir, String inEncoding) 
    {
        // store current state in stack
        includeStack.push(new IncludeState(cursor, line, col, fileId,
                                           fileName, baseDir, 
                                           encoding, stream) );

        // set new variables
        cursor = 0;
        line = 1;
        col = 1;
        fileId = inFileId;
        fileName = name;
        baseDir = inBaseDir;
        encoding = inEncoding;
        stream = inStream;
    }


    /**
     * Restores this mark's state to a previously stored stream.
     * @return The previous Mark instance when the stream was pushed, or null
     * if there is no previous stream
     */
    public Mark popStream() {
        // make sure we have something to pop
        if ( includeStack.size() <= 0 ) {
            return null;
        }

        // get previous state in stack
        IncludeState state = includeStack.pop( );

        // set new variables
        cursor = state.cursor;
        line = state.line;
        col = state.col;
        fileId = state.fileId;
        fileName = state.fileName;
        baseDir = state.baseDir;
        stream = state.stream;
        return this;
    }


    // -------------------- Locator interface --------------------

    public int getLineNumber() {
        return line;
    }

    public int getColumnNumber() {
        return col;
    }

    public String getSystemId() {
        return getFile();
    }

    public String getPublicId() {
        return null;
    }

    @Override
    public String toString() {
        return getFile()+"("+line+","+col+")";
    }

    public String getFile() {
        return this.fileName;
    }

    /**
     * Gets the URL of the resource with which this Mark is associated
     *
     * @return URL of the resource with which this Mark is associated
     *
     * @exception MalformedURLException if the resource pathname is incorrect
     */
    public URL getURL() throws MalformedURLException {
        return ctxt.getResource(getFile());
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Mark) {
            Mark m = (Mark) other;
            return this.reader == m.reader && this.fileId == m.fileId 
                && this.cursor == m.cursor && this.line == m.line 
                && this.col == m.col;
        } 
        return false;
    }

    /**
     * Keep track of parser before parsing an included file.
     * This class keeps track of the parser before we switch to parsing an
     * included file. In other words, it's the parser's continuation to be
     * reinstalled after the included file parsing is done.
     */
    class IncludeState {
        int cursor, line, col;
        int fileId;
        String fileName;
        String baseDir;
        char[] stream = null;

        IncludeState(int inCursor, int inLine, int inCol, int inFileId, 
                     String name, String inBaseDir, String inEncoding,
                     char[] inStream) {
            cursor = inCursor;
            line = inLine;
            col = inCol;
            fileId = inFileId;
            fileName = name;
            baseDir = inBaseDir;
            encoding = inEncoding;
            stream = inStream;
        }
    }

}

