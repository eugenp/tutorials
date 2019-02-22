package com.baeldung.xmlhtml;

/*
 * @Author Adam InTae Gerard
 */

public class Constants {

    /**
     * File-related constants.
     */

    public static final String XML_FILE_IN = "src/main/resources/xml/in.xml";
    public static final String JAXB_FILE_OUT = "src/main/resources/xml/jaxb.html";
    public static final String JAXP_FILE_OUT = "src/main/resources/xml/jaxp.html";
    public static final String STAX_FILE_OUT = "src/main/resources/xml/stax.html";

    /**
     * Generic exceptions  constants.
     */

    public static final String EXCEPTION_ENCOUNTERED = "Oops! You done's broke'd it! Error: ";

    /**
     * Generic logging constants.
     */

    public static final String LINE_BREAK = System.getProperty("line.separator");
    public static final String WHITE_SPACE = " ";

    /**
     * SAX Parser console constants.
     */

    public static final String DOCUMENT_START = "Document parsing has begun!";
    public static final String DOCUMENT_END = "Document parsing has ended!";
    public static final String ELEMENT_START = "Element parsing has begun!";
    public static final String ELEMENT_END = "Element parsing has ended!";

    /**
     * STAX prefixes.
     */

    public static final String BREAK = "\n";
    public static final String TAB = "\t";
}
