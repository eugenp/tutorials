package com.baeldung.web.log.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

public class RequestLoggingUtil {

    public static String getStringFromInputStream(InputStream is) {
        StringWriter writer = new StringWriter();
        String encoding = "UTF-8";
        try {
            IOUtils.copy(is, writer, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    public static String readPayload(final HttpServletRequest request) throws IOException {
        String payloadData = null;
        ContentCachingRequestWrapper contentCachingRequestWrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (null != contentCachingRequestWrapper) {
            byte[] buf = contentCachingRequestWrapper.getContentAsByteArray();
            if (buf.length > 0) {
                payloadData = new String(buf, 0, buf.length, contentCachingRequestWrapper.getCharacterEncoding());
            }
        }
        return payloadData;
    }

}
