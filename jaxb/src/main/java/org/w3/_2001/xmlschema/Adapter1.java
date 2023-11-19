
package org.w3._2001.xmlschema;

import java.util.Calendar;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, Calendar>
{


    public Calendar unmarshal(String value) {
        return (jakarta.xml.bind.DatatypeConverter.parseDateTime(value));
    }

    public String marshal(Calendar value) {
        if (value == null) {
            return null;
        }
        return (jakarta.xml.bind.DatatypeConverter.printDateTime(value));
    }

}
