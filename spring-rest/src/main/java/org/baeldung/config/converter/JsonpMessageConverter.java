package org.baeldung.config.converter;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.baeldung.web.dto.BaeldungItem;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

public class JsonpMessageConverter extends AbstractHttpMessageConverter<BaeldungItem> {
    public static final MediaType JSONP_MEDIA_TYPE = new MediaType("application", "x-jsonp");
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public JsonpMessageConverter() {
        super(JSONP_MEDIA_TYPE);
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return Object.class.isAssignableFrom(aClass);
    }

    @Override
    protected BaeldungItem readInternal(Class<? extends BaeldungItem> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        final Input input = new Input(httpInputMessage.getBody());
        return objectMapper.readValue(input, BaeldungItem.class);
    }

    @Override
    protected void writeInternal(BaeldungItem baeldungItem, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        final Output output = new Output(httpOutputMessage.getBody());
        objectMapper.writeValue(output, baeldungItem);
        output.flush();
    }

    @Override
    protected MediaType getDefaultContentType(final BaeldungItem object) {
        return JSONP_MEDIA_TYPE;
    }
}
