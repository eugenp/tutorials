package com.baeldung.openapi.generators.camelclient.helper;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.openapitools.codegen.templating.mustache.KebabCaseLambda;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

public class JavaConstantLambda implements Mustache.Lambda {

    private KebabCaseLambda kebab = new KebabCaseLambda();
    @Override
    public void execute(Template.Fragment fragment, Writer writer) throws IOException {
        StringWriter sr = new StringWriter();
        kebab.execute(fragment, sr);

        StringBuffer sb = sr.getBuffer();
        for( int i = 0 ; i < sb.length() ; i++ ) {
            char c = sb.charAt(i);

            if ( c == '-') {
                sb.setCharAt(i, '_');
            }
            else if ( !Character.isUpperCase(c)) {
                sb.setCharAt(i, Character.toUpperCase(c));
            }
        }

        writer.write(sb.toString());
    }
}
