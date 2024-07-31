package com.baeldung.restexpress;

import com.strategicgains.repoexpress.domain.Timestamped;
import com.strategicgains.util.date.DateAdapter;
import com.strategicgains.util.date.HttpHeaderTimestampAdapter;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.pipeline.Postprocessor;

import static io.netty.handler.codec.http.HttpHeaders.Names.LAST_MODIFIED;

/**
 * Assigns the Last-Modified HTTP header on the response for GET responses, if applicable.
 *
 * @author toddf
 * @since May 15, 2012
 */
public class LastModifiedHeaderPostprocessor
        implements Postprocessor {
    DateAdapter fmt = new HttpHeaderTimestampAdapter();

    @Override
    public void process(Request request, Response response) {
        if (!request.isMethodGet()) return;
        if (!response.hasBody()) return;

        Object body = response.getBody();

        if (!response.hasHeader(LAST_MODIFIED) && body.getClass().isAssignableFrom(Timestamped.class)) {
            response.addHeader(LAST_MODIFIED, fmt.format(((Timestamped) body).getUpdatedAt()));
        }
    }
}
