package com.baeldung.netty;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
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

		final FullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
				"/calculate?a=10&b=5");
		httpRequest.headers().add("Operator", "Add");

		EmbeddedChannel channel = new EmbeddedChannel(new HttpMessageHandler(), new CalculatorOperationHandler());

		channel.pipeline().addFirst(new HttpMessageHandler()).addLast(new CalculatorOperationHandler());

		// send HTTP request to server and check that the message is on the inbound pipeline
		assertThat(channel.writeInbound(httpRequest)).isTrue();

		long inboundChannelResponse = channel.readInbound();
		assertThat(inboundChannelResponse).isEqualTo(15);

		// we should have an outbound message in the form of a HTTP response
		assertThat(channel.outboundMessages().size()).isEqualTo(1);
		// Object response = channel.readOutbound();

		FullHttpResponse httpResponse = channel.readOutbound();
		String httpResponseContent = httpResponse.content().toString(Charset.defaultCharset());
		assertThat(httpResponseContent).isEqualTo("15");
	}

	@Test
	public void givenTwoChannelHandlers_testExceptionHandlingInHttpMessageHandler() {

		EmbeddedChannel channel = new EmbeddedChannel(new HttpMessageHandler(), new CalculatorOperationHandler());

		final FullHttpRequest wrongHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
				"/calculate?a=10&b=5");
		wrongHttpRequest.headers().add("Operator", "Add");

		assertThatThrownBy(() -> {
            // send invalid HTTP request to server and expect and error
            channel.pipeline().fireChannelRead(wrongHttpRequest);
            channel.checkException();
        }).isInstanceOf(UnsupportedOperationException.class)
          .hasMessage("HTTP method not supported");

		FullHttpResponse errorHttpResponse = channel.readOutbound();
		String errorHttpResponseContent = errorHttpResponse.content().toString(Charset.defaultCharset());
		assertThat(errorHttpResponseContent).isEqualToIgnoringCase("Operation not defined");
		assertThat(errorHttpResponse.status()).isEqualTo(HttpResponseStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	public void givenTwoChannelHandlers_testExceptionHandlingInCalculatorOperationHandler() {
		EmbeddedChannel channel = new EmbeddedChannel(new HttpMessageHandler(), new CalculatorOperationHandler());

		final FullHttpRequest wrongHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET,
				"/calculate?a=10&b=5");
		wrongHttpRequest.headers().add("Operator", "Invalid_operation");

		// the HttpMessageHandler does not handle the exception and throws it down the pipeline
        assertThatThrownBy(() -> {
            channel.writeInbound(wrongHttpRequest);
        }).isInstanceOf(IllegalArgumentException.class)
          .hasMessage("Operation not defined");

		// the outbound message is a HTTP response with the status code 500
		FullHttpResponse errorHttpResponse = channel.readOutbound();
		String errorHttpResponseContent = errorHttpResponse.content().toString(Charset.defaultCharset());
		assertThat(errorHttpResponseContent).isEqualToIgnoringCase("Operation not defined");
		assertThat(errorHttpResponse.status()).isEqualTo(HttpResponseStatus.INTERNAL_SERVER_ERROR);
	}

}
