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


package validators;


import java.io.IOException;
import java.io.InputStream;

import javax.servlet.jsp.tagext.PageData;
import javax.servlet.jsp.tagext.TagLibraryValidator;
import javax.servlet.jsp.tagext.ValidationMessage;


/**
 * Example tag library validator that simply dumps the XML version of each
 * page to standard output (which will typically be sent to the file
 * <code>$CATALINA_HOME/logs/catalina.out</code>).  To utilize it, simply
 * include a <code>taglib</code> directive for this tag library at the top
 * of your JSP page.
 *
 * @author Craig McClanahan
 */
public class DebugValidator extends TagLibraryValidator {


    // ----------------------------------------------------- Instance Variables


    // --------------------------------------------------------- Public Methods


    /**
     * Validate a JSP page.  This will get invoked once per directive in the
     * JSP page.  This method will return <code>null</code> if the page is
     * valid; otherwise the method should return an array of
     * <code>ValidationMessage</code> objects.  An array of length zero is
     * also interpreted as no errors.
     *
     * @param prefix The value of the prefix argument in this directive
     * @param uri The value of the URI argument in this directive
     * @param page The page data for this page
     */
    @Override
    public ValidationMessage[] validate(String prefix, String uri,
                                        PageData page) {

        System.out.println("---------- Prefix=" + prefix + " URI=" + uri +
                           "----------");

        InputStream is = page.getInputStream();
        while (true) {
            try {
                int ch = is.read();
                if (ch < 0)
                    break;
                System.out.print((char) ch);
            } catch (IOException e) {
                break;
            }
        }
        System.out.println();
        System.out.println("-----------------------------------------------");
        return (null);

    }


}
