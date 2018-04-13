package com.baeldung.netty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Test;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class EmbeddedChannelUnitTest {
	
	@Test
	public void givenTwoChannelHandlers_testPipeline() {
		
		final FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/calculate?a=10&b=5");
        httpRequest.headers().add("Operator", "Add");

        EmbeddedChannel channel = new EmbeddedChannel(
                new HttpMessageHandler(), new CalculatorOperationHandler());
        
        channel.pipeline()
                .addFirst(new HttpMessageHandler())
                .addLast(new CalculatorOperationHandler());


        // send HTTP request to server and check that the message is on the inbound pipeline
        assertTrue(channel.writeInbound(httpRequest));

        long inboundChannelResponse = channel.readInbound();
        assertEquals(15, inboundChannelResponse);

        // we should have an outbound message in the form of a HTTP response
        assertEquals(1, channel.outboundMessages().size());
        // Object response = channel.readOutbound();

        FullHttpResponse httpResponse = channel.readOutbound();
        String httpResponseContent = httpResponse.content().toString(Charset.defaultCharset());
        assertTrue("15".equalsIgnoreCase(httpResponseContent));
	}
	
	@Test
	public void givenTwoChannelHandlers_testExceptionHandlingInHttpMessageHandler() {
		
		EmbeddedChannel channel = new EmbeddedChannel(
                new HttpMessageHandler(), new CalculatorOperationHandler());

        final FullHttpRequest wrongHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, "/calculate?a=10&b=5");
        wrongHttpRequest.headers().add("Operator", "Add");

        try {
            // send invalid HTTP request to server and expect and error
            channel.pipeline().fireChannelRead(wrongHttpRequest);
            channel.checkException();
            // channel.writeInbound(wrongHttpRequest);
            Assert.fail();

        } catch (Exception ex) {

            // the HttpMessageHandler does not handle the exception and throws it down the pipeline
            assertTrue(ex instanceof UnsupportedOperationException);
            assertTrue(ex.getMessage().equalsIgnoreCase("HTTP method not supported"));

            FullHttpResponse errorHttpResponse = channel.readOutbound();
            String errorHttpResponseContent = errorHttpResponse.content().toString(Charset.defaultCharset());
            assertTrue("Operation not defined".equalsIgnoreCase(errorHttpResponseContent));
            assertEquals(HttpResponseStatus.INTERNAL_SERVER_ERROR, errorHttpResponse.status());
        }
	}
	
	
	@Test
	public void givenTwoChannelHandlers_testExceptionHandlingInCalculatorOperationHandler() {
		EmbeddedChannel channel = new EmbeddedChannel(
                new HttpMessageHandler(), new CalculatorOperationHandler());

        final FullHttpRequest wrongHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/calculate?a=10&b=5");
        wrongHttpRequest.headers().add("Operator", "Invalid_operation");

        try {
            // send invalid HTTP request to server and expect and error
            channel.writeInbound(wrongHttpRequest);
            Assert.fail();

        } catch (Exception ex) {

            // the HttpMessageHandler does not handle the exception and throws it down the pipeline
            assertTrue(ex instanceof IllegalArgumentException);
            assertTrue(ex.getMessage().equalsIgnoreCase("Operation not defined"));

            // the outbound message is a HTTP response with the status code 500
            FullHttpResponse errorHttpResponse = channel.readOutbound();
            String errorHttpResponseContent = errorHttpResponse.content().toString(Charset.defaultCharset());
            assertTrue("Operation not defined".equalsIgnoreCase(errorHttpResponseContent));
            assertEquals(HttpResponseStatus.INTERNAL_SERVER_ERROR, errorHttpResponse.status());
        }
	}

}
