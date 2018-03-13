/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.util.buf;

import java.io.IOException;
import java.util.BitSet;

/** Efficient implementation for encoders.
 *  This class is not thread safe - you need one encoder per thread.
 *  The encoder will save and recycle the internal objects, avoiding
 *  garbage.
 *
 *  You can add extra characters that you want preserved, for example
 *  while encoding a URL you can add "/".
 *
 *  @author Costin Manolache
 */
public final class UEncoder {

    public enum SafeCharsSet {
        WITH_SLASH("/"), DEFAULT("");
        private final BitSet safeChars;

        private BitSet getSafeChars() {
            return this.safeChars;
        }

        private SafeCharsSet(String additionalSafeChars) {
            safeChars = initialSafeChars();
            for (char c : additionalSafeChars.toCharArray()) {
                safeChars.set(c);
            }
        }
    }

    // Not static - the set may differ ( it's better than adding
    // an extra check for "/", "+", etc
    private BitSet safeChars=null;
    private C2BConverter c2b=null;
    private ByteChunk bb=null;
    private CharChunk cb=null;
    private CharChunk output=null;
    private final boolean readOnlySafeChars;

    private String encoding="UTF8";

    public UEncoder() {
        this.safeChars = initialSafeChars();
        readOnlySafeChars = false;
    }

    /**
     * Create a UEncoder with an unmodifiable safe character set.
     * <p>
     * Calls to {@link UEncoder#addSafeCharacter(char) addSafeCharacter(char)}
     * on instances created by this constructor will throw an
     * {@link IllegalStateException}.
     *
     * @param safeCharsSet
     *            safe characters for this encoder
     */
    public UEncoder(SafeCharsSet safeCharsSet) {
        this.safeChars = safeCharsSet.getSafeChars();
        readOnlySafeChars = true;
    }

     /**
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public void setEncoding( String s ) {
        encoding=s;
    }

    public void addSafeCharacter( char c ) {
        if (readOnlySafeChars) {
            throw new IllegalStateException("UEncoders safeChararacters are read only");
        }
        safeChars.set( c );
    }


   /**
    * URL Encode string, using a specified encoding.
    *
    * @param s string to be encoded
    * @param start the beginning index, inclusive
    * @param end the ending index, exclusive
    * @throws IOException If an I/O error occurs
    */
   public CharChunk encodeURL(String s, int start, int end)
       throws IOException {
       if (c2b == null) {
           bb = new ByteChunk(8); // small enough.
           cb = new CharChunk(2); // small enough.
           output = new CharChunk(64); // small enough.
           c2b = new C2BConverter(encoding);
       } else {
           bb.recycle();
           cb.recycle();
           output.recycle();
       }

       for (int i = start; i < end; i++) {
           char c = s.charAt(i);
           if (safeChars.get(c)) {
               output.append(c);
           } else {
               cb.append(c);
               c2b.convert(cb, bb);

               // "surrogate" - UTF is _not_ 16 bit, but 21 !!!!
               // ( while UCS is 31 ). Amazing...
               if (c >= 0xD800 && c <= 0xDBFF) {
                   if ((i+1) < end) {
                       char d = s.charAt(i+1);
                       if (d >= 0xDC00 && d <= 0xDFFF) {
                           cb.append(d);
                           c2b.convert(cb, bb);
                           i++;
                       }
                   }
               }

               urlEncode(output, bb);
               cb.recycle();
               bb.recycle();
           }
       }
       
       return output;
   }

   protected void urlEncode(CharChunk out, ByteChunk bb)
       throws IOException {
       byte[] bytes = bb.getBuffer();
       for (int j = bb.getStart(); j < bb.getEnd(); j++) {
           out.append('%');
           char ch = Character.forDigit((bytes[j] >> 4) & 0xF, 16);
           out.append(ch);
           ch = Character.forDigit(bytes[j] & 0xF, 16);
           out.append(ch);
       }
   }
   
    // -------------------- Internal implementation --------------------

    private static BitSet initialSafeChars() {
        BitSet initialSafeChars=new BitSet(128);
        int i;
        for (i = 'a'; i <= 'z'; i++) {
            initialSafeChars.set(i);
        }
        for (i = 'A'; i <= 'Z'; i++) {
            initialSafeChars.set(i);
        }
        for (i = '0'; i <= '9'; i++) {
            initialSafeChars.set(i);
        }
        //safe
        initialSafeChars.set('$');
        initialSafeChars.set('-');
        initialSafeChars.set('_');
        initialSafeChars.set('.');

        // Dangerous: someone may treat this as " "
        // RFC1738 does allow it, it's not reserved
        //    initialSafeChars.set('+');
        //extra
        initialSafeChars.set('!');
        initialSafeChars.set('*');
        initialSafeChars.set('\'');
        initialSafeChars.set('(');
        initialSafeChars.set(')');
        initialSafeChars.set(',');
        return initialSafeChars;
    }
}
