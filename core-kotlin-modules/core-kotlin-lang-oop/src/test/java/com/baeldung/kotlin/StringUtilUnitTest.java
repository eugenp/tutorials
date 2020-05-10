package com.baeldung.kotlin;

import kotlin.text.StringsKt;
import org.junit.Assert;
import org.junit.Test;

import static com.baeldung.kotlin.Strings.*;


public class StringUtilUnitTest {

    @Test
    public void shouldEscapeXmlTagsInString() {
        String xml = "<a>hi</a>";

        String escapedXml = escapeForXml(xml);

        Assert.assertEquals("&lt;a&gt;hi&lt;/a&gt;", escapedXml);
    }

    @Test
    public void callingBuiltInKotlinExtensionMethod() {
        String name = "john";

        String capitalizedName = StringsKt.capitalize(name);

        Assert.assertEquals("John", capitalizedName);
    }

}
