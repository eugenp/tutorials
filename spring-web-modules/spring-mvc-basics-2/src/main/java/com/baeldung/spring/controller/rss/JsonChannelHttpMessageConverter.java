package com.baeldung.spring.controller.rss;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.WireFeedOutput;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;

public class JsonChannelHttpMessageConverter extends AbstractHttpMessageConverter<Channel> {
    public JsonChannelHttpMessageConverter(){
        super(new MediaType("application", "rss+json"));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return Channel.class.isAssignableFrom(aClass);
    }

    @Override
    protected Channel readInternal(Class<? extends Channel> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(Channel wireFeed, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        WireFeedOutput feedOutput = new WireFeedOutput();

        try {
            String xmlStr = feedOutput.outputString(wireFeed, true);
            JSONObject xmlJSONObj = XML.toJSONObject(xmlStr);
            String jsonPrettyPrintString = xmlJSONObj.toString(4);

            outputMessage.getBody().write(jsonPrettyPrintString.getBytes());
        } catch (JSONException | FeedException e) {
            e.printStackTrace();
        }
    }
}
