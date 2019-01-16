package com.baeldung.xmlhtml.helpers;

import com.baeldung.xmlhtml.helpers.jaxb.JAXBHelper;
import com.baeldung.xmlhtml.helpers.jaxp.JAXPHelper;

import static com.baeldung.xmlhtml.Constants.JAXB_FULL_OUT;
import static com.baeldung.xmlhtml.Constants.JAXB_SIMPLE_OUT;

/**
 * @author Adam In Tae Gerard
 *
 * Core executable code in here.
 */

public class XMLRunner {

	public static void doWork() {

		JAXBHelper.simple();
		JAXBHelper.full();

		JAXPHelper.saxParser();
		JAXPHelper.documentBuilder();
	}

}
