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

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Locale;
import java.util.jar.JarFile;

import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.compiler.ErrorDispatcher;
import org.apache.jasper.compiler.JspUtil;
import org.apache.jasper.compiler.Localizer;

public class XMLEncodingDetector {
    
    private InputStream stream;
    private String encoding;
    private boolean isEncodingSetInProlog;
    private boolean isBomPresent;
    private int skip;
    private Boolean isBigEndian;
    private Reader reader;
    
    // org.apache.xerces.impl.XMLEntityManager fields
    public static final int DEFAULT_BUFFER_SIZE = 2048;
    public static final int DEFAULT_XMLDECL_BUFFER_SIZE = 64;
    private boolean fAllowJavaEncodings;
    private SymbolTable fSymbolTable;
    private XMLEncodingDetector fCurrentEntity;
    private int fBufferSize = DEFAULT_BUFFER_SIZE;
    
    // org.apache.xerces.impl.XMLEntityManager.ScannedEntity fields
    private int lineNumber = 1;
    private int columnNumber = 1;
    private boolean literal;
    private char[] ch = new char[DEFAULT_BUFFER_SIZE];
    private int position;
    private int count;
    private boolean mayReadChunks = false;
    
    // org.apache.xerces.impl.XMLScanner fields
    private XMLString fString = new XMLString();    
    private XMLStringBuffer fStringBuffer = new XMLStringBuffer();
    private XMLStringBuffer fStringBuffer2 = new XMLStringBuffer();
    private static final String fVersionSymbol = "version";
    private static final String fEncodingSymbol = "encoding";
    private static final String fStandaloneSymbol = "standalone";
    
    // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl fields
    private int fMarkupDepth = 0;
    private String[] fStrings = new String[3];

    private ErrorDispatcher err;

    /**
     * Constructor
     */
    public XMLEncodingDetector() {
        fSymbolTable = new SymbolTable();
        fCurrentEntity = this;
    }

    /**
     * Autodetects the encoding of the XML document supplied by the given
     * input stream.
     *
     * Encoding autodetection is done according to the XML 1.0 specification,
     * Appendix F.1: Detection Without External Encoding Information.
     *
     * @return Two-element array, where the first element (of type
     * java.lang.String) contains the name of the (auto)detected encoding, and
     * the second element (of type java.lang.Boolean) specifies whether the 
     * encoding was specified using the 'encoding' attribute of an XML prolog
     * (TRUE) or autodetected (FALSE).
     */
    public static Object[] getEncoding(String fname, JarFile jarFile,
                                       JspCompilationContext ctxt,
                                       ErrorDispatcher err)
        throws IOException, JasperException
    {
        InputStream inStream = JspUtil.getInputStream(fname, jarFile, ctxt,
                                                      err);
        XMLEncodingDetector detector = new XMLEncodingDetector();
        Object[] ret = detector.getEncoding(inStream, err);
        inStream.close();

        return ret;
    }

    private Object[] getEncoding(InputStream in, ErrorDispatcher err)
        throws IOException, JasperException
    {
        this.stream = in;
        this.err=err;
        createInitialReader();
        scanXMLDecl();

        return new Object[] { this.encoding,
                              Boolean.valueOf(this.isEncodingSetInProlog),
                              Boolean.valueOf(this.isBomPresent),
                              Integer.valueOf(this.skip) };
    }
    
    // stub method
    void endEntity() {
    }
    
    // Adapted from:
    // org.apache.xerces.impl.XMLEntityManager.startEntity()
    private void createInitialReader() throws IOException, JasperException {

        // wrap this stream in RewindableInputStream
        stream = new RewindableInputStream(stream);

        // perform auto-detect of encoding if necessary
        if (encoding == null) {
            // read first four bytes and determine encoding
            final byte[] b4 = new byte[4];
            int count = 0;
            for (; count<4; count++ ) {
                b4[count] = (byte)stream.read();
            }
            if (count == 4) {
                Object [] encodingDesc = getEncodingName(b4, count);
                encoding = (String)(encodingDesc[0]);
                isBigEndian = (Boolean)(encodingDesc[1]);
        
                if (encodingDesc.length > 3) {
                    isBomPresent = ((Boolean)(encodingDesc[2])).booleanValue();
                    skip = ((Integer)(encodingDesc[3])).intValue();
                } else {
                    isBomPresent = true;
                    skip = ((Integer)(encodingDesc[2])).intValue();
                }

                stream.reset();
                // Special case UTF-8 files with BOM created by Microsoft
                // tools. It's more efficient to consume the BOM than make
                // the reader perform extra checks. -Ac
                if (encoding.equals("UTF-8")) {
                    int b0 = b4[0] & 0xFF;
                    int b1 = b4[1] & 0xFF;
                    int b2 = b4[2] & 0xFF;
                    if (b0 == 0xEF && b1 == 0xBB && b2 == 0xBF) {
                        // ignore first three bytes...
                        long skipped = stream.skip(3);
                        if (skipped != 3) {
                            throw new IOException(Localizer.getMessage(
                                    "xmlParser.skipBomFail"));
                        }
                    }
                }
                reader = createReader(stream, encoding, isBigEndian);
            } else {
                reader = createReader(stream, encoding, isBigEndian);
            }
        }
    }

    // Adapted from:
    // org.apache.xerces.impl.XMLEntityManager.createReader
    /**
     * Creates a reader capable of reading the given input stream in
     * the specified encoding.
     *
     * @param inputStream  The input stream.
     * @param encoding     The encoding name that the input stream is
     *                     encoded using. If the user has specified that
     *                     Java encoding names are allowed, then the
     *                     encoding name may be a Java encoding name;
     *                     otherwise, it is an ianaEncoding name.
     * @param isBigEndian   For encodings (like uCS-4), whose names cannot
     *                      specify a byte order, this tells whether the order
     *                      is bigEndian. null means unknown or not relevant.
     *
     * @return Returns a reader.
     */
    private Reader createReader(InputStream inputStream, String encoding,
                                Boolean isBigEndian)
                throws IOException, JasperException {

        // normalize encoding name
        if (encoding == null) {
            encoding = "UTF-8";
        }

        // try to use an optimized reader
        String ENCODING = encoding.toUpperCase(Locale.ENGLISH);
        if (ENCODING.equals("UTF-8")) {
            return new UTF8Reader(inputStream, fBufferSize);
        }
        if (ENCODING.equals("US-ASCII")) {
            return new ASCIIReader(inputStream, fBufferSize);
        }
        if (ENCODING.equals("ISO-10646-UCS-4")) {
            if (isBigEndian != null) {
                boolean isBE = isBigEndian.booleanValue();
                if (isBE) {
                    return new UCSReader(inputStream, UCSReader.UCS4BE);
                } else {
                    return new UCSReader(inputStream, UCSReader.UCS4LE);
                }
            } else {
                err.jspError("jsp.error.xml.encodingByteOrderUnsupported",
                             encoding);
            }
        }
        if (ENCODING.equals("ISO-10646-UCS-2")) {
            if (isBigEndian != null) { // sould never happen with this encoding...
                boolean isBE = isBigEndian.booleanValue();
                if (isBE) {
                    return new UCSReader(inputStream, UCSReader.UCS2BE);
                } else {
                    return new UCSReader(inputStream, UCSReader.UCS2LE);
                }
            } else {
                err.jspError("jsp.error.xml.encodingByteOrderUnsupported",
                             encoding);
            }
        }

        // check for valid name
        boolean validIANA = XMLChar.isValidIANAEncoding(encoding);
        boolean validJava = XMLChar.isValidJavaEncoding(encoding);
        if (!validIANA || (fAllowJavaEncodings && !validJava)) {
            err.jspError("jsp.error.xml.encodingDeclInvalid", encoding);
            // NOTE: AndyH suggested that, on failure, we use ISO Latin 1
            //       because every byte is a valid ISO Latin 1 character.
            //       It may not translate correctly but if we failed on
            //       the encoding anyway, then we're expecting the content
            //       of the document to be bad. This will just prevent an
            //       invalid UTF-8 sequence to be detected. This is only
            //       important when continue-after-fatal-error is turned
            //       on. -Ac
            encoding = "ISO-8859-1";
        }

        // try to use a Java reader
        String javaEncoding = EncodingMap.getIANA2JavaMapping(ENCODING);
        if (javaEncoding == null) {
            if (fAllowJavaEncodings) {
                javaEncoding = encoding;
            } else {
                err.jspError("jsp.error.xml.encodingDeclInvalid", encoding);
                // see comment above.
                javaEncoding = "ISO8859_1";
            }
        }
        return new InputStreamReader(inputStream, javaEncoding);

    } // createReader(InputStream,String, Boolean): Reader

    // Adapted from:
    // org.apache.xerces.impl.XMLEntityManager.getEncodingName
    /**
     * Returns the IANA encoding name that is auto-detected from
     * the bytes specified, with the endian-ness of that encoding where
     * appropriate.
     *
     * @param b4    The first four bytes of the input.
     * @param count The number of bytes actually read.
     * @return a 2-element array:  the first element, an IANA-encoding string,
     *  the second element a Boolean which is true iff the document is big
     *  endian, false if it's little-endian, and null if the distinction isn't
     *  relevant.
     */
    private Object[] getEncodingName(byte[] b4, int count) {

        if (count < 2) {
            return new Object[]{"UTF-8", null, Boolean.FALSE, Integer.valueOf(0)};
        }

        // UTF-16, with BOM
        int b0 = b4[0] & 0xFF;
        int b1 = b4[1] & 0xFF;
        if (b0 == 0xFE && b1 == 0xFF) {
            // UTF-16, big-endian
            return new Object [] {"UTF-16BE", Boolean.TRUE, Integer.valueOf(2)};
        }
        if (b0 == 0xFF && b1 == 0xFE) {
            // UTF-16, little-endian
            return new Object [] {"UTF-16LE", Boolean.FALSE, Integer.valueOf(2)};
        }

        // default to UTF-8 if we don't have enough bytes to make a
        // good determination of the encoding
        if (count < 3) {
            return new Object [] {"UTF-8", null, Boolean.FALSE, Integer.valueOf(0)};
        }

        // UTF-8 with a BOM
        int b2 = b4[2] & 0xFF;
        if (b0 == 0xEF && b1 == 0xBB && b2 == 0xBF) {
            return new Object [] {"UTF-8", null, Integer.valueOf(3)};
        }

        // default to UTF-8 if we don't have enough bytes to make a
        // good determination of the encoding
        if (count < 4) {
            return new Object [] {"UTF-8", null, Integer.valueOf(0)};
        }

        // other encodings
        int b3 = b4[3] & 0xFF;
        if (b0 == 0x00 && b1 == 0x00 && b2 == 0x00 && b3 == 0x3C) {
            // UCS-4, big endian (1234)
            return new Object [] {"ISO-10646-UCS-4", Boolean.TRUE, Integer.valueOf(4)};
        }
        if (b0 == 0x3C && b1 == 0x00 && b2 == 0x00 && b3 == 0x00) {
            // UCS-4, little endian (4321)
            return new Object [] {"ISO-10646-UCS-4", Boolean.FALSE, Integer.valueOf(4)};
        }
        if (b0 == 0x00 && b1 == 0x00 && b2 == 0x3C && b3 == 0x00) {
            // UCS-4, unusual octet order (2143)
            // REVISIT: What should this be?
            return new Object [] {"ISO-10646-UCS-4", null, Integer.valueOf(4)};
        }
        if (b0 == 0x00 && b1 == 0x3C && b2 == 0x00 && b3 == 0x00) {
            // UCS-4, unusual octect order (3412)
            // REVISIT: What should this be?
            return new Object [] {"ISO-10646-UCS-4", null, Integer.valueOf(4)};
        }
        if (b0 == 0x00 && b1 == 0x3C && b2 == 0x00 && b3 == 0x3F) {
            // UTF-16, big-endian, no BOM
            // (or could turn out to be UCS-2...
            // REVISIT: What should this be?
            return new Object [] {"UTF-16BE", Boolean.TRUE, Integer.valueOf(4)};
        }
        if (b0 == 0x3C && b1 == 0x00 && b2 == 0x3F && b3 == 0x00) {
            // UTF-16, little-endian, no BOM
            // (or could turn out to be UCS-2...
            return new Object [] {"UTF-16LE", Boolean.FALSE, Integer.valueOf(4)};
        }
        if (b0 == 0x4C && b1 == 0x6F && b2 == 0xA7 && b3 == 0x94) {
            // EBCDIC
            // a la xerces1, return CP037 instead of EBCDIC here
            return new Object [] {"CP037", null, Integer.valueOf(4)};
        }

        // default encoding
        return new Object [] {"UTF-8", null, Boolean.FALSE, Integer.valueOf(0)};

    }

    // Adapted from:
    // org.apache.xerces.impl.XMLEntityManager.EntityScanner.isExternal
    /** Returns true if the current entity being scanned is external. */
    public boolean isExternal() {
        return true;
    }

    // Adapted from:
    // org.apache.xerces.impl.XMLEntityManager.EntityScanner.peekChar
    /**
     * Returns the next character on the input.
     * <p>
     * <strong>Note:</strong> The character is <em>not</em> consumed.
     *
     * @throws IOException  Thrown if i/o error occurs.
     * @throws EOFException Thrown on end of file.
     */
    public int peekChar() throws IOException {
        
        // load more characters, if needed
        if (fCurrentEntity.position == fCurrentEntity.count) {
            load(0, true);
        }
        
        // peek at character
        int c = fCurrentEntity.ch[fCurrentEntity.position];

        // return peeked character
        if (fCurrentEntity.isExternal()) {
            return c != '\r' ? c : '\n';
        }
        else {
            return c;
        }
        
    } // peekChar():int
    
    // Adapted from:
    // org.apache.xerces.impl.XMLEntityManager.EntityScanner.scanChar
    /**
     * Returns the next character on the input.
     * <p>
     * <strong>Note:</strong> The character is consumed.
     *
     * @throws IOException  Thrown if i/o error occurs.
     * @throws EOFException Thrown on end of file.
     */
    public int scanChar() throws IOException {

        // load more characters, if needed
        if (fCurrentEntity.position == fCurrentEntity.count) {
            load(0, true);
        }

        // scan character
        int c = fCurrentEntity.ch[fCurrentEntity.position++];
        boolean external = false;
        if (c == '\n' ||
            (c == '\r' && (external = fCurrentEntity.isExternal()))) {
            fCurrentEntity.lineNumber++;
            fCurrentEntity.columnNumber = 1;
            if (fCurrentEntity.position == fCurrentEntity.count) {
                fCurrentEntity.ch[0] = (char)c;
                load(1, false);
            }
            if (c == '\r' && external) {
                if (fCurrentEntity.ch[fCurrentEntity.position++] != '\n') {
                    fCurrentEntity.position--;
                }
                c = '\n';
            }
        }

        // return character that was scanned
        fCurrentEntity.columnNumber++;
        return c;
        
    }

    // Adapted from:
    // org.apache.xerces.impl.XMLEntityManager.EntityScanner.scanName
    /**
     * Returns a string matching the Name production appearing immediately
     * on the input as a symbol, or null if no Name string is present.
     * <p>
     * <strong>Note:</strong> The Name characters are consumed.
     * <p>
     * <strong>Note:</strong> The string returned must be a symbol. The
     * SymbolTable can be used for this purpose.
     *
     * @throws IOException  Thrown if i/o error occurs.
     * @throws EOFException Thrown on end of file.
     *
     * @see SymbolTable
     * @see XMLChar#isName
     * @see XMLChar#isNameStart
     */
    public String scanName() throws IOException {
        
        // load more characters, if needed
        if (fCurrentEntity.position == fCurrentEntity.count) {
            load(0, true);
        }
        
        // scan name
        int offset = fCurrentEntity.position;
        if (XMLChar.isNameStart(fCurrentEntity.ch[offset])) {
            if (++fCurrentEntity.position == fCurrentEntity.count) {
                fCurrentEntity.ch[0] = fCurrentEntity.ch[offset];
                offset = 0;
                if (load(1, false)) {
                    fCurrentEntity.columnNumber++;
                    String symbol = fSymbolTable.addSymbol(fCurrentEntity.ch,
                                                           0, 1);
                    return symbol;
                }
            }
            while (XMLChar.isName(fCurrentEntity.ch[fCurrentEntity.position])) {
                if (++fCurrentEntity.position == fCurrentEntity.count) {
                    int length = fCurrentEntity.position - offset;
                    if (length == fBufferSize) {
                        // bad luck we have to resize our buffer
                        char[] tmp = new char[fBufferSize * 2];
                        System.arraycopy(fCurrentEntity.ch, offset,
                                         tmp, 0, length);
                        fCurrentEntity.ch = tmp;
                        fBufferSize *= 2;
                    } else {
                        System.arraycopy(fCurrentEntity.ch, offset,
                                         fCurrentEntity.ch, 0, length);
                    }
                    offset = 0;
                    if (load(length, false)) {
                        break;
                    }
                }
            }
        }
        int length = fCurrentEntity.position - offset;
        fCurrentEntity.columnNumber += length;

        // return name
        String symbol = null;
        if (length > 0) {
            symbol = fSymbolTable.addSymbol(fCurrentEntity.ch, offset, length);
        }
        return symbol;
        
    }

    // Adapted from:
    // org.apache.xerces.impl.XMLEntityManager.EntityScanner.scanLiteral
    /**
     * Scans a range of attribute value data, setting the fields of the
     * XMLString structure, appropriately.
     * <p>
     * <strong>Note:</strong> The characters are consumed.
     * <p>
     * <strong>Note:</strong> This method does not guarantee to return
     * the longest run of attribute value data. This method may return
     * before the quote character due to reaching the end of the input
     * buffer or any other reason.
     * <p>
     * <strong>Note:</strong> The fields contained in the XMLString
     * structure are not guaranteed to remain valid upon subsequent calls
     * to the entity scanner. Therefore, the caller is responsible for
     * immediately using the returned character data or making a copy of
     * the character data.
     *
     * @param quote   The quote character that signifies the end of the
     *                attribute value data.
     * @param content The content structure to fill.
     *
     * @return Returns the next character on the input, if known. This
     *         value may be -1 but this does <em>note</em> designate
     *         end of file.
     *
     * @throws IOException  Thrown if i/o error occurs.
     * @throws EOFException Thrown on end of file.
     */
    public int scanLiteral(int quote, XMLString content)
        throws IOException {

        // load more characters, if needed
        if (fCurrentEntity.position == fCurrentEntity.count) {
            load(0, true);
        } else if (fCurrentEntity.position == fCurrentEntity.count - 1) {
            fCurrentEntity.ch[0] = fCurrentEntity.ch[fCurrentEntity.count - 1];
            load(1, false);
            fCurrentEntity.position = 0;
        }

        // normalize newlines
        int offset = fCurrentEntity.position;
        int c = fCurrentEntity.ch[offset];
        int newlines = 0;
        boolean external = fCurrentEntity.isExternal();
        if (c == '\n' || (c == '\r' && external)) {
            do {
                c = fCurrentEntity.ch[fCurrentEntity.position++];
                if (c == '\r' && external) {
                    newlines++;
                    fCurrentEntity.lineNumber++;
                    fCurrentEntity.columnNumber = 1;
                    if (fCurrentEntity.position == fCurrentEntity.count) {
                        offset = 0;
                        fCurrentEntity.position = newlines;
                        if (load(newlines, false)) {
                            break;
                        }
                    }
                    if (fCurrentEntity.ch[fCurrentEntity.position] == '\n') {
                        fCurrentEntity.position++;
                        offset++;
                    }
                    /*** NEWLINE NORMALIZATION ***/
                    else {
                        newlines++;
                    }
                    /***/
                }
                else if (c == '\n') {
                    newlines++;
                    fCurrentEntity.lineNumber++;
                    fCurrentEntity.columnNumber = 1;
                    if (fCurrentEntity.position == fCurrentEntity.count) {
                        offset = 0;
                        fCurrentEntity.position = newlines;
                        if (load(newlines, false)) {
                            break;
                        }
                    }
                    /*** NEWLINE NORMALIZATION ***
                         if (fCurrentEntity.ch[fCurrentEntity.position] == '\r'
                         && external) {
                         fCurrentEntity.position++;
                         offset++;
                         }
                         /***/
                }
                else {
                    fCurrentEntity.position--;
                    break;
                }
            } while (fCurrentEntity.position < fCurrentEntity.count - 1);
            for (int i = offset; i < fCurrentEntity.position; i++) {
                fCurrentEntity.ch[i] = '\n';
            }
            int length = fCurrentEntity.position - offset;
            if (fCurrentEntity.position == fCurrentEntity.count - 1) {
                content.setValues(fCurrentEntity.ch, offset, length);
                return -1;
            }
        }

        // scan literal value
        while (fCurrentEntity.position < fCurrentEntity.count) {
            c = fCurrentEntity.ch[fCurrentEntity.position++];
            if ((c == quote &&
                 (!fCurrentEntity.literal || external))
                || c == '%' || !XMLChar.isContent(c)) {
                fCurrentEntity.position--;
                break;
            }
        }
        int length = fCurrentEntity.position - offset;
        fCurrentEntity.columnNumber += length - newlines;
        content.setValues(fCurrentEntity.ch, offset, length);

        // return next character
        if (fCurrentEntity.position != fCurrentEntity.count) {
            c = fCurrentEntity.ch[fCurrentEntity.position];
            // NOTE: We don't want to accidentally signal the
            //       end of the literal if we're expanding an
            //       entity appearing in the literal. -Ac
            if (c == quote && fCurrentEntity.literal) {
                c = -1;
            }
        }
        else {
            c = -1;
        }
        return c;

    }

    /**
     * Scans a range of character data up to the specified delimiter,
     * setting the fields of the XMLString structure, appropriately.
     * <p>
     * <strong>Note:</strong> The characters are consumed.
     * <p>
     * <strong>Note:</strong> This assumes that the internal buffer is
     * at least the same size, or bigger, than the length of the delimiter
     * and that the delimiter contains at least one character.
     * <p>
     * <strong>Note:</strong> This method does not guarantee to return
     * the longest run of character data. This method may return before
     * the delimiter due to reaching the end of the input buffer or any
     * other reason.
     * <p>
     * <strong>Note:</strong> The fields contained in the XMLString
     * structure are not guaranteed to remain valid upon subsequent calls
     * to the entity scanner. Therefore, the caller is responsible for
     * immediately using the returned character data or making a copy of
     * the character data.
     *
     * @param delimiter The string that signifies the end of the character
     *                  data to be scanned.
     * @param buffer    The data structure to fill.
     *
     * @return Returns true if there is more data to scan, false otherwise.
     *
     * @throws IOException  Thrown if i/o error occurs.
     * @throws EOFException Thrown on end of file.
     */
    public boolean scanData(String delimiter, XMLStringBuffer buffer)
        throws IOException {

        boolean done = false;
        int delimLen = delimiter.length();
        char charAt0 = delimiter.charAt(0);
        boolean external = fCurrentEntity.isExternal();
        do {
    
            // load more characters, if needed
    
            if (fCurrentEntity.position == fCurrentEntity.count) {
                load(0, true);
            }
            else if (fCurrentEntity.position >= fCurrentEntity.count - delimLen) {
                System.arraycopy(fCurrentEntity.ch, fCurrentEntity.position,
                                 fCurrentEntity.ch, 0, fCurrentEntity.count - fCurrentEntity.position);
                load(fCurrentEntity.count - fCurrentEntity.position, false);
                fCurrentEntity.position = 0;
            } 
            if (fCurrentEntity.position >= fCurrentEntity.count - delimLen) {
                // something must be wrong with the input: e.g., file ends an
                // unterminated comment
                int length = fCurrentEntity.count - fCurrentEntity.position;
                buffer.append (fCurrentEntity.ch, fCurrentEntity.position,
                               length); 
                fCurrentEntity.columnNumber += fCurrentEntity.count;
                fCurrentEntity.position = fCurrentEntity.count;
                load(0,true);
                return false;
            }
    
            // normalize newlines
            int offset = fCurrentEntity.position;
            int c = fCurrentEntity.ch[offset];
            int newlines = 0;
            if (c == '\n' || (c == '\r' && external)) {
                do {
                    c = fCurrentEntity.ch[fCurrentEntity.position++];
                    if (c == '\r' && external) {
                        newlines++;
                        fCurrentEntity.lineNumber++;
                        fCurrentEntity.columnNumber = 1;
                        if (fCurrentEntity.position == fCurrentEntity.count) {
                            offset = 0;
                            fCurrentEntity.position = newlines;
                            if (load(newlines, false)) {
                                break;
                            }
                        }
                        if (fCurrentEntity.ch[fCurrentEntity.position] == '\n') {
                            fCurrentEntity.position++;
                            offset++;
                        }
                        /*** NEWLINE NORMALIZATION ***/
                        else {
                            newlines++;
                        }
                    }
                    else if (c == '\n') {
                        newlines++;
                        fCurrentEntity.lineNumber++;
                        fCurrentEntity.columnNumber = 1;
                        if (fCurrentEntity.position == fCurrentEntity.count) {
                            offset = 0;
                            fCurrentEntity.position = newlines;
                            fCurrentEntity.count = newlines;
                            if (load(newlines, false)) {
                                break;
                            }
                        }
                    }
                    else {
                        fCurrentEntity.position--;
                        break;
                    }
                } while (fCurrentEntity.position < fCurrentEntity.count - 1);
                for (int i = offset; i < fCurrentEntity.position; i++) {
                    fCurrentEntity.ch[i] = '\n';
                }
                int length = fCurrentEntity.position - offset;
                if (fCurrentEntity.position == fCurrentEntity.count - 1) {
                    buffer.append(fCurrentEntity.ch, offset, length);
                    return true;
                }
            }
    
            // iterate over buffer looking for delimiter
        OUTER: while (fCurrentEntity.position < fCurrentEntity.count) {
            c = fCurrentEntity.ch[fCurrentEntity.position++];
            if (c == charAt0) {
                // looks like we just hit the delimiter
                int delimOffset = fCurrentEntity.position - 1;
                for (int i = 1; i < delimLen; i++) {
                    if (fCurrentEntity.position == fCurrentEntity.count) {
                        fCurrentEntity.position -= i;
                        break OUTER;
                    }
                    c = fCurrentEntity.ch[fCurrentEntity.position++];
                    if (delimiter.charAt(i) != c) {
                        fCurrentEntity.position--;
                        break;
                    }
                }
                if (fCurrentEntity.position == delimOffset + delimLen) {
                    done = true;
                    break;
                }
            }
            else if (c == '\n' || (external && c == '\r')) {
                fCurrentEntity.position--;
                break;
            }
            else if (XMLChar.isInvalid(c)) {
                fCurrentEntity.position--;
                int length = fCurrentEntity.position - offset;
                fCurrentEntity.columnNumber += length - newlines;
                buffer.append(fCurrentEntity.ch, offset, length); 
                return true;
            }
        }
            int length = fCurrentEntity.position - offset;
            fCurrentEntity.columnNumber += length - newlines;
            if (done) {
                length -= delimLen;
            }
            buffer.append (fCurrentEntity.ch, offset, length);
    
            // return true if string was skipped
        } while (!done);
        return !done;

    }

    // Adapted from:
    // org.apache.xerces.impl.XMLEntityManager.EntityScanner.skipChar
    /**
     * Skips a character appearing immediately on the input.
     * <p>
     * <strong>Note:</strong> The character is consumed only if it matches
     * the specified character.
     *
     * @param c The character to skip.
     *
     * @return Returns true if the character was skipped.
     *
     * @throws IOException  Thrown if i/o error occurs.
     * @throws EOFException Thrown on end of file.
     */
    public boolean skipChar(int c) throws IOException {

        // load more characters, if needed
        if (fCurrentEntity.position == fCurrentEntity.count) {
            load(0, true);
        }

        // skip character
        int cc = fCurrentEntity.ch[fCurrentEntity.position];
        if (cc == c) {
            fCurrentEntity.position++;
            if (c == '\n') {
                fCurrentEntity.lineNumber++;
                fCurrentEntity.columnNumber = 1;
            }
            else {
                fCurrentEntity.columnNumber++;
            }
            return true;
        } else if (c == '\n' && cc == '\r' && fCurrentEntity.isExternal()) {
            // handle newlines
            if (fCurrentEntity.position == fCurrentEntity.count) {
                fCurrentEntity.ch[0] = (char)cc;
                load(1, false);
            }
            fCurrentEntity.position++;
            if (fCurrentEntity.ch[fCurrentEntity.position] == '\n') {
                fCurrentEntity.position++;
            }
            fCurrentEntity.lineNumber++;
            fCurrentEntity.columnNumber = 1;
            return true;
        }

        // character was not skipped
        return false;

    }

    // Adapted from:
    // org.apache.xerces.impl.XMLEntityManager.EntityScanner.skipSpaces
    /**
     * Skips space characters appearing immediately on the input.
     * <p>
     * <strong>Note:</strong> The characters are consumed only if they are
     * space characters.
     *
     * @return Returns true if at least one space character was skipped.
     *
     * @throws IOException  Thrown if i/o error occurs.
     * @throws EOFException Thrown on end of file.
     *
     * @see XMLChar#isSpace
     */
    public boolean skipSpaces() throws IOException {

        // load more characters, if needed
        if (fCurrentEntity.position == fCurrentEntity.count) {
            load(0, true);
        }

        // skip spaces
        int c = fCurrentEntity.ch[fCurrentEntity.position];
        if (XMLChar.isSpace(c)) {
            boolean external = fCurrentEntity.isExternal();
            do {
                boolean entityChanged = false;
                // handle newlines
                if (c == '\n' || (external && c == '\r')) {
                    fCurrentEntity.lineNumber++;
                    fCurrentEntity.columnNumber = 1;
                    if (fCurrentEntity.position == fCurrentEntity.count - 1) {
                        fCurrentEntity.ch[0] = (char)c;
                        entityChanged = load(1, true);
                        if (!entityChanged)
                                // the load change the position to be 1,
                                // need to restore it when entity not changed
                            fCurrentEntity.position = 0;
                    }
                    if (c == '\r' && external) {
                        // REVISIT: Does this need to be updated to fix the
                        //          #x0D ^#x0A newline normalization problem? -Ac
                        if (fCurrentEntity.ch[++fCurrentEntity.position] != '\n') {
                            fCurrentEntity.position--;
                        }
                    }
                    /*** NEWLINE NORMALIZATION ***
                         else {
                         if (fCurrentEntity.ch[fCurrentEntity.position + 1] == '\r'
                         && external) {
                         fCurrentEntity.position++;
                         }
                         }
                         /***/
                }
                else {
                    fCurrentEntity.columnNumber++;
                }
                // load more characters, if needed
                if (!entityChanged)
                    fCurrentEntity.position++;
                if (fCurrentEntity.position == fCurrentEntity.count) {
                    load(0, true);
                }
            } while (XMLChar.isSpace(c = fCurrentEntity.ch[fCurrentEntity.position]));
            return true;
        }

        // no spaces were found
        return false;

    }

    /**
     * Skips the specified string appearing immediately on the input.
     * <p>
     * <strong>Note:</strong> The characters are consumed only if they are
     * space characters.
     *
     * @param s The string to skip.
     *
     * @return Returns true if the string was skipped.
     *
     * @throws IOException  Thrown if i/o error occurs.
     * @throws EOFException Thrown on end of file.
     */
    public boolean skipString(String s) throws IOException {

        // load more characters, if needed
        if (fCurrentEntity.position == fCurrentEntity.count) {
            load(0, true);
        }

        // skip string
        final int length = s.length();
        for (int i = 0; i < length; i++) {
            char c = fCurrentEntity.ch[fCurrentEntity.position++];
            if (c != s.charAt(i)) {
                fCurrentEntity.position -= i + 1;
                return false;
            }
            if (i < length - 1 && fCurrentEntity.position == fCurrentEntity.count) {
                System.arraycopy(fCurrentEntity.ch, fCurrentEntity.count - i - 1, fCurrentEntity.ch, 0, i + 1);
                // REVISIT: Can a string to be skipped cross an
                //          entity boundary? -Ac
                if (load(i + 1, false)) {
                    fCurrentEntity.position -= i + 1;
                    return false;
                }
            }
        }
        fCurrentEntity.columnNumber += length;
        return true;

    }

    // Adapted from:
    // org.apache.xerces.impl.XMLEntityManager.EntityScanner.load
    /**
     * Loads a chunk of text.
     *
     * @param offset       The offset into the character buffer to
     *                     read the next batch of characters.
     * @param changeEntity True if the load should change entities
     *                     at the end of the entity, otherwise leave
     *                     the current entity in place and the entity
     *                     boundary will be signaled by the return
     *                     value.
     *
     * @return Returns true if the entity changed as a result of this
     *         load operation.
     */
    final boolean load(int offset, boolean changeEntity)
        throws IOException {

        // read characters
        int length = fCurrentEntity.mayReadChunks?
            (fCurrentEntity.ch.length - offset):
            (DEFAULT_XMLDECL_BUFFER_SIZE);
        int count = fCurrentEntity.reader.read(fCurrentEntity.ch, offset,
                                               length);

        // reset count and position
        boolean entityChanged = false;
        if (count != -1) {
            if (count != 0) {
                fCurrentEntity.count = count + offset;
                fCurrentEntity.position = offset;
            }
        }

        // end of this entity
        else {
            fCurrentEntity.count = offset;
            fCurrentEntity.position = offset;
            entityChanged = true;
            if (changeEntity) {
                endEntity();
                if (fCurrentEntity == null) {
                    throw new EOFException();
                }
                // handle the trailing edges
                if (fCurrentEntity.position == fCurrentEntity.count) {
                    load(0, false);
                }
            }
        }

        return entityChanged;

    }

    // Adapted from:
    // org.apache.xerces.impl.XMLEntityManager.RewindableInputStream
    /**
     * This class wraps the byte inputstreams we're presented with.
     * We need it because java.io.InputStreams don't provide
     * functionality to reread processed bytes, and they have a habit
     * of reading more than one character when you call their read()
     * methods.  This means that, once we discover the true (declared)
     * encoding of a document, we can neither backtrack to read the
     * whole doc again nor start reading where we are with a new
     * reader.
     *
     * This class allows rewinding an inputStream by allowing a mark
     * to be set, and the stream reset to that position.  <strong>The
     * class assumes that it needs to read one character per
     * invocation when it's read() method is invoked, but uses the
     * underlying InputStream's read(char[], offset length) method--it
     * won't buffer data read this way!</strong>
     *
     * @author Neil Graham, IBM
     * @author Glenn Marcy, IBM
     */
    private final class RewindableInputStream extends InputStream {

        private InputStream fInputStream;
        private byte[] fData;
        private int fEndOffset;
        private int fOffset;
        private int fLength;
        private int fMark;

        public RewindableInputStream(InputStream is) {
            fData = new byte[DEFAULT_XMLDECL_BUFFER_SIZE];
            fInputStream = is;
            fEndOffset = -1;
            fOffset = 0;
            fLength = 0;
            fMark = 0;
        }

        @Override
        public int read() throws IOException {
            int b = 0;
            if (fOffset < fLength) {
                return fData[fOffset++] & 0xff;
            }
            if (fOffset == fEndOffset) {
                return -1;
            }
            if (fOffset == fData.length) {
                byte[] newData = new byte[fOffset << 1];
                System.arraycopy(fData, 0, newData, 0, fOffset);
                fData = newData;
            }
            b = fInputStream.read();
            if (b == -1) {
                fEndOffset = fOffset;
                return -1;
            }
            fData[fLength++] = (byte)b;
            fOffset++;
            return b & 0xff;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            int bytesLeft = fLength - fOffset;
            if (bytesLeft == 0) {
                if (fOffset == fEndOffset) {
                    return -1;
                }
                // better get some more for the voracious reader...
                if (fCurrentEntity.mayReadChunks) {
                    return fInputStream.read(b, off, len);
                }
                int returnedVal = read();
                if (returnedVal == -1) {
                    fEndOffset = fOffset;
                    return -1;
                }
                b[off] = (byte)returnedVal;
                return 1;
            }
            if (len < bytesLeft) {
                if (len <= 0) {
                    return 0;
                }
            }
            else {
                len = bytesLeft;
            }
            if (b != null) {
                System.arraycopy(fData, fOffset, b, off, len);
            }
            fOffset += len;
            return len;
        }

        @Override
        public long skip(long n)
            throws IOException
        {
            int bytesLeft;
            if (n <= 0) {
                return 0;
            }
            bytesLeft = fLength - fOffset;
            if (bytesLeft == 0) {
                if (fOffset == fEndOffset) {
                    return 0;
                }
                return fInputStream.skip(n);
            }
            if (n <= bytesLeft) {
                fOffset += n;
                return n;
            }
            fOffset += bytesLeft;
            if (fOffset == fEndOffset) {
                return bytesLeft;
            }
            n -= bytesLeft;
            /*
             * In a manner of speaking, when this class isn't permitting more
             * than one byte at a time to be read, it is "blocking".  The
             * available() method should indicate how much can be read without
             * blocking, so while we're in this mode, it should only indicate
             * that bytes in its buffer are available; otherwise, the result of
             * available() on the underlying InputStream is appropriate.
             */
            return fInputStream.skip(n) + bytesLeft;
        }

        @Override
        public int available() throws IOException {
            int bytesLeft = fLength - fOffset;
            if (bytesLeft == 0) {
                if (fOffset == fEndOffset) {
                    return -1;
                }
                return fCurrentEntity.mayReadChunks ? fInputStream.available()
                    : 0;
            }
            return bytesLeft;
        }

        @Override
        public synchronized void mark(int howMuch) {
            fMark = fOffset;
        }

        @Override
        public synchronized void reset() {
            fOffset = fMark;
        }

        @Override
        public boolean markSupported() {
            return true;
        }

        @Override
        public void close() throws IOException {
            if (fInputStream != null) {
                fInputStream.close();
                fInputStream = null;
            }
        }
    } // end of RewindableInputStream class

    // Adapted from:
    // org.apache.xerces.impl.XMLDocumentScannerImpl.dispatch
    private void scanXMLDecl() throws IOException, JasperException {

        if (skipString("<?xml")) {
            fMarkupDepth++;
            // NOTE: special case where document starts with a PI
            //       whose name starts with "xml" (e.g. "xmlfoo")
            if (XMLChar.isName(peekChar())) {
                fStringBuffer.clear();
                fStringBuffer.append("xml");
                while (XMLChar.isName(peekChar())) {
                    fStringBuffer.append((char)scanChar());
                }
                String target = fSymbolTable.addSymbol(fStringBuffer.ch,
                                                       fStringBuffer.offset,
                                                       fStringBuffer.length);
                scanPIData(target, fString);
            }

            // standard XML declaration
            else {
                scanXMLDeclOrTextDecl(false);
            }
        }
    }
    
    // Adapted from:
    // org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.scanXMLDeclOrTextDecl
    /**
     * Scans an XML or text declaration.
     * <p>
     * <pre>
     * [23] XMLDecl ::= '&lt;?xml' VersionInfo EncodingDecl? SDDecl? S? '?>'
     * [24] VersionInfo ::= S 'version' Eq (' VersionNum ' | " VersionNum ")
     * [80] EncodingDecl ::= S 'encoding' Eq ('"' EncName '"' |  "'" EncName "'" )
     * [81] EncName ::= [A-Za-z] ([A-Za-z0-9._] | '-')*
     * [32] SDDecl ::= S 'standalone' Eq (("'" ('yes' | 'no') "'")
     *                 | ('"' ('yes' | 'no') '"'))
     *
     * [77] TextDecl ::= '&lt;?xml' VersionInfo? EncodingDecl S? '?>'
     * </pre>
     *
     * @param scanningTextDecl True if a text declaration is to
     *                         be scanned instead of an XML
     *                         declaration.
     */
    private void scanXMLDeclOrTextDecl(boolean scanningTextDecl) 
        throws IOException, JasperException {

        // scan decl
        scanXMLDeclOrTextDecl(scanningTextDecl, fStrings);
        fMarkupDepth--;

        // pseudo-attribute values
        String encodingPseudoAttr = fStrings[1];

        // set encoding on reader
        if (encodingPseudoAttr != null) {
            isEncodingSetInProlog = true;
            encoding = encodingPseudoAttr;
        }
    }

    // Adapted from:
    // org.apache.xerces.impl.XMLScanner.scanXMLDeclOrTextDecl
    /**
     * Scans an XML or text declaration.
     * <p>
     * <pre>
     * [23] XMLDecl ::= '<?xml' VersionInfo EncodingDecl? SDDecl? S? '?>'
     * [24] VersionInfo ::= S 'version' Eq (' VersionNum ' | " VersionNum ")
     * [80] EncodingDecl ::= S 'encoding' Eq ('"' EncName '"' |  "'" EncName "'" )
     * [81] EncName ::= [A-Za-z] ([A-Za-z0-9._] | '-')*
     * [32] SDDecl ::= S 'standalone' Eq (("'" ('yes' | 'no') "'")
     *                 | ('"' ('yes' | 'no') '"'))
     *
     * [77] TextDecl ::= '<?xml' VersionInfo? EncodingDecl S? '?>'
     * </pre>
     *
     * @param scanningTextDecl True if a text declaration is to
     *                         be scanned instead of an XML
     *                         declaration.
     * @param pseudoAttributeValues An array of size 3 to return the version,
     *                         encoding and standalone pseudo attribute values
     *                         (in that order).
     *
     * <strong>Note:</strong> This method uses fString, anything in it
     * at the time of calling is lost.
     */
    private void scanXMLDeclOrTextDecl(boolean scanningTextDecl,
                                       String[] pseudoAttributeValues) 
                throws IOException, JasperException {

        // pseudo-attribute values
        String version = null;
        String encoding = null;
        String standalone = null;

        // scan pseudo-attributes
        final int STATE_VERSION = 0;
        final int STATE_ENCODING = 1;
        final int STATE_STANDALONE = 2;
        final int STATE_DONE = 3;
        int state = STATE_VERSION;

        boolean dataFoundForTarget = false;
        boolean sawSpace = skipSpaces();
        while (peekChar() != '?') {
            dataFoundForTarget = true;
            String name = scanPseudoAttribute(scanningTextDecl, fString);
            switch (state) {
                case STATE_VERSION: {
                    if (name == fVersionSymbol) {
                        if (!sawSpace) {
                            reportFatalError(scanningTextDecl
                                       ? "jsp.error.xml.spaceRequiredBeforeVersionInTextDecl"
                                       : "jsp.error.xml.spaceRequiredBeforeVersionInXMLDecl",
                                             null);
                        }
                        version = fString.toString();
                        state = STATE_ENCODING;
                        if (!version.equals("1.0")) {
                            // REVISIT: XML REC says we should throw an error
                            // in such cases.
                            // some may object the throwing of fatalError.
                            err.jspError("jsp.error.xml.versionNotSupported",
                                         version);
                        }
                    } else if (name == fEncodingSymbol) {
                        if (!scanningTextDecl) {
                            err.jspError("jsp.error.xml.versionInfoRequired");
                        }
                        if (!sawSpace) {
                            reportFatalError(scanningTextDecl
                                      ? "jsp.error.xml.spaceRequiredBeforeEncodingInTextDecl"
                                      : "jsp.error.xml.spaceRequiredBeforeEncodingInXMLDecl",
                                             null);
                        }
                        encoding = fString.toString();
                        state = scanningTextDecl ? STATE_DONE : STATE_STANDALONE;
                    } else {
                        if (scanningTextDecl) {
                            err.jspError("jsp.error.xml.encodingDeclRequired");
                        }
                        else {
                            err.jspError("jsp.error.xml.versionInfoRequired");
                        }
                    }
                    break;
                }
                case STATE_ENCODING: {
                    if (name == fEncodingSymbol) {
                        if (!sawSpace) {
                            reportFatalError(scanningTextDecl
                                      ? "jsp.error.xml.spaceRequiredBeforeEncodingInTextDecl"
                                      : "jsp.error.xml.spaceRequiredBeforeEncodingInXMLDecl",
                                             null);
                        }
                        encoding = fString.toString();
                        state = scanningTextDecl ? STATE_DONE : STATE_STANDALONE;
                        // TODO: check encoding name; set encoding on
                        //       entity scanner
                    } else if (!scanningTextDecl && name == fStandaloneSymbol) {
                        if (!sawSpace) {
                            err.jspError("jsp.error.xml.spaceRequiredBeforeStandalone");
                        }
                        standalone = fString.toString();
                        state = STATE_DONE;
                        if (!standalone.equals("yes") && !standalone.equals("no")) {
                            err.jspError("jsp.error.xml.sdDeclInvalid");
                        }
                    } else {
                        err.jspError("jsp.error.xml.encodingDeclRequired");
                    }
                    break;
                }
                case STATE_STANDALONE: {
                    if (name == fStandaloneSymbol) {
                        if (!sawSpace) {
                            err.jspError("jsp.error.xml.spaceRequiredBeforeStandalone");
                        }
                        standalone = fString.toString();
                        state = STATE_DONE;
                        if (!standalone.equals("yes") && !standalone.equals("no")) {
                            err.jspError("jsp.error.xml.sdDeclInvalid");
                        }
                    } else {
                        err.jspError("jsp.error.xml.encodingDeclRequired");
                    }
                    break;
                }
                default: {
                    err.jspError("jsp.error.xml.noMorePseudoAttributes");
                }
            }
            sawSpace = skipSpaces();
        }
        // REVISIT: should we remove this error reporting?
        if (scanningTextDecl && state != STATE_DONE) {
            err.jspError("jsp.error.xml.morePseudoAttributes");
        }
        
        // If there is no data in the xml or text decl then we fail to report
        // error for version or encoding info above.
        if (scanningTextDecl) {
            if (!dataFoundForTarget && encoding == null) {
                err.jspError("jsp.error.xml.encodingDeclRequired");
            }
        } else {
            if (!dataFoundForTarget && version == null) {
                err.jspError("jsp.error.xml.versionInfoRequired");
            }
        }

        // end
        if (!skipChar('?')) {
            err.jspError("jsp.error.xml.xmlDeclUnterminated");
        }
        if (!skipChar('>')) {
            err.jspError("jsp.error.xml.xmlDeclUnterminated");

        }
        
        // fill in return array
        pseudoAttributeValues[0] = version;
        pseudoAttributeValues[1] = encoding;
        pseudoAttributeValues[2] = standalone;
    }

    // Adapted from:
    // org.apache.xerces.impl.XMLScanner.scanPseudoAttribute
    /**
     * Scans a pseudo attribute.
     *
     * @param scanningTextDecl True if scanning this pseudo-attribute for a
     *                         TextDecl; false if scanning XMLDecl. This 
     *                         flag is needed to report the correct type of
     *                         error.
     * @param value            The string to fill in with the attribute 
     *                         value.
     *
     * @return The name of the attribute
     *
     * <strong>Note:</strong> This method uses fStringBuffer2, anything in it
     * at the time of calling is lost.
     */
    public String scanPseudoAttribute(boolean scanningTextDecl, 
                                      XMLString value) 
                throws IOException, JasperException {

        String name = scanName();
        if (name == null) {
            err.jspError("jsp.error.xml.pseudoAttrNameExpected");
        }
        skipSpaces();
        if (!skipChar('=')) {
            reportFatalError(scanningTextDecl ?
                             "jsp.error.xml.eqRequiredInTextDecl"
                             : "jsp.error.xml.eqRequiredInXMLDecl",
                             name);
        }
        skipSpaces();
        int quote = peekChar();
        if (quote != '\'' && quote != '"') {
            reportFatalError(scanningTextDecl ?
                             "jsp.error.xml.quoteRequiredInTextDecl"
                             : "jsp.error.xml.quoteRequiredInXMLDecl" ,
                             name);
        }
        scanChar();
        int c = scanLiteral(quote, value);
        if (c != quote) {
            fStringBuffer2.clear();
            do {
                fStringBuffer2.append(value);
                if (c != -1) {
                    if (c == '&' || c == '%' || c == '<' || c == ']') {
                        fStringBuffer2.append((char)scanChar());
                    }
                    else if (XMLChar.isHighSurrogate(c)) {
                        scanSurrogates(fStringBuffer2);
                    }
                    else if (XMLChar.isInvalid(c)) {
                        String key = scanningTextDecl
                            ? "jsp.error.xml.invalidCharInTextDecl"
                            : "jsp.error.xml.invalidCharInXMLDecl";
                        reportFatalError(key, Integer.toString(c, 16));
                        scanChar();
                    }
                }
                c = scanLiteral(quote, value);
            } while (c != quote);
            fStringBuffer2.append(value);
            value.setValues(fStringBuffer2);
        }
        if (!skipChar(quote)) {
            reportFatalError(scanningTextDecl ?
                             "jsp.error.xml.closeQuoteMissingInTextDecl"
                             : "jsp.error.xml.closeQuoteMissingInXMLDecl",
                             name);
        }

        // return
        return name;

    }
    
    // Adapted from:
    // org.apache.xerces.impl.XMLScanner.scanPIData
    /**
     * Scans a processing data. This is needed to handle the situation
     * where a document starts with a processing instruction whose 
     * target name <em>starts with</em> "xml". (e.g. xmlfoo)
     *
     * <strong>Note:</strong> This method uses fStringBuffer, anything in it
     * at the time of calling is lost.
     *
     * @param target The PI target
     * @param data The string to fill in with the data
     */
    private void scanPIData(String target, XMLString data) 
        throws IOException, JasperException {

        // check target
        if (target.length() == 3) {
            char c0 = Character.toLowerCase(target.charAt(0));
            char c1 = Character.toLowerCase(target.charAt(1));
            char c2 = Character.toLowerCase(target.charAt(2));
            if (c0 == 'x' && c1 == 'm' && c2 == 'l') {
                err.jspError("jsp.error.xml.reservedPITarget");
            }
        }

        // spaces
        if (!skipSpaces()) {
            if (skipString("?>")) {
                // we found the end, there is no data
                data.clear();
                return;
            }
            else {
                // if there is data there should be some space
                err.jspError("jsp.error.xml.spaceRequiredInPI");
            }
        }

        fStringBuffer.clear();
        // data
        if (scanData("?>", fStringBuffer)) {
            do {
                int c = peekChar();
                if (c != -1) {
                    if (XMLChar.isHighSurrogate(c)) {
                        scanSurrogates(fStringBuffer);
                    } else if (XMLChar.isInvalid(c)) {
                        err.jspError("jsp.error.xml.invalidCharInPI",
                                     Integer.toHexString(c));
                        scanChar();
                    }
                }
            } while (scanData("?>", fStringBuffer));
        }
        data.setValues(fStringBuffer);

    }

    // Adapted from:
    // org.apache.xerces.impl.XMLScanner.scanSurrogates
    /**
     * Scans surrogates and append them to the specified buffer.
     * <p>
     * <strong>Note:</strong> This assumes the current char has already been
     * identified as a high surrogate.
     *
     * @param buf The StringBuffer to append the read surrogates to.
     * @return True if it succeeded.
     */
    private boolean scanSurrogates(XMLStringBuffer buf)
        throws IOException, JasperException {

        int high = scanChar();
        int low = peekChar();
        if (!XMLChar.isLowSurrogate(low)) {
            err.jspError("jsp.error.xml.invalidCharInContent",
                         Integer.toString(high, 16));
            return false;
        }
        scanChar();

        // convert surrogates to supplemental character
        int c = XMLChar.supplemental((char)high, (char)low);

        // supplemental character must be a valid XML character
        if (!XMLChar.isValid(c)) {
            err.jspError("jsp.error.xml.invalidCharInContent",
                         Integer.toString(c, 16)); 
            return false;
        }

        // fill in the buffer
        buf.append((char)high);
        buf.append((char)low);

        return true;

    }

    // Adapted from:
    // org.apache.xerces.impl.XMLScanner.reportFatalError
    /**
     * Convenience function used in all XML scanners.
     */
    private void reportFatalError(String msgId, String arg)
                throws JasperException {
        err.jspError(msgId, arg);
    }

}


