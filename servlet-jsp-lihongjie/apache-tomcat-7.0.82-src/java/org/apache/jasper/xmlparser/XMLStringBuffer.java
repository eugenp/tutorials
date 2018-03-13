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
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 1999, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package org.apache.jasper.xmlparser;

/**
 * XMLString is a structure used to pass character arrays. However,
 * XMLStringBuffer is a buffer in which characters can be appended
 * and extends XMLString so that it can be passed to methods
 * expecting an XMLString object. This is a safe operation because
 * it is assumed that any callee will <strong>not</strong> modify
 * the contents of the XMLString structure.
 * <p> 
 * The contents of the string are managed by the string buffer. As
 * characters are appended, the string buffer will grow as needed.
 * <p>
 * <strong>Note:</strong> Never set the <code>ch</code>, 
 * <code>offset</code>, and <code>length</code> fields directly.
 * These fields are managed by the string buffer. In order to reset
 * the buffer, call <code>clear()</code>.
 * 
 * @author Andy Clark, IBM
 * @author Eric Ye, IBM
 */
public class XMLStringBuffer
    extends XMLString {

    //
    // Constants
    //

    /** Default buffer size (32). */
    public static final int DEFAULT_SIZE = 32;

    //
    // Constructors
    //

    /**
     * 
     */
    public XMLStringBuffer() {
        this(DEFAULT_SIZE);
    } // <init>()

    /**
     * 
     * 
     * @param size 
     */
    public XMLStringBuffer(int size) {
        ch = new char[size];
    } // <init>(int)

    //
    // Public methods
    //

    /** Clears the string buffer. */
    @Override
    public void clear() {
        offset = 0;
        length = 0;
    }

    /**
     * append
     * 
     * @param c 
     */
    public void append(char c) {
        if (this.length + 1 > this.ch.length) {
                    int newLength = this.ch.length*2;
                    if (newLength < this.ch.length + DEFAULT_SIZE)
                        newLength = this.ch.length + DEFAULT_SIZE;
                    char[] newch = new char[newLength];
                    System.arraycopy(this.ch, 0, newch, 0, this.length);
                    this.ch = newch;
        }
        this.ch[this.length] = c;
        this.length++;
    } // append(char)

    /**
     * append
     * 
     * @param s 
     */
    public void append(String s) {
        int length = s.length();
        if (this.length + length > this.ch.length) {
            int newLength = this.ch.length*2;
            if (newLength < this.length + length + DEFAULT_SIZE)
                newLength = this.ch.length + length + DEFAULT_SIZE;
            char[] newch = new char[newLength];            
            System.arraycopy(this.ch, 0, newch, 0, this.length);
            this.ch = newch;
        }
        s.getChars(0, length, this.ch, this.length);
        this.length += length;
    } // append(String)

    /**
     * append
     * 
     * @param ch 
     * @param offset 
     * @param length 
     */
    public void append(char[] ch, int offset, int length) {
        if (this.length + length > this.ch.length) {
            char[] newch = new char[this.ch.length + length + DEFAULT_SIZE];
            System.arraycopy(this.ch, 0, newch, 0, this.length);
            this.ch = newch;
        }
        System.arraycopy(ch, offset, this.ch, this.length, length);
        this.length += length;
    } // append(char[],int,int)

    /**
     * append
     * 
     * @param s 
     */
    public void append(XMLString s) {
        append(s.ch, s.offset, s.length);
    } // append(XMLString)

} // class XMLStringBuffer
