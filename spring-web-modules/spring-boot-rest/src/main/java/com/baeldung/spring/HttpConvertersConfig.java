package com.baeldung.spring;

import java.text.SimpleDateFormat;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverters;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.http.converter.xml.JacksonXmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import tools.jackson.core.json.JsonReadFeature;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;
import tools.jackson.dataformat.xml.XmlReadFeature;

@Configuration
class HttpConvertersConfig implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(HttpMessageConverters.ServerBuilder builder) {
		JsonMapper jsonMapper = JsonMapper.builder()
				.findAndAddModules()
				.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
				.enable(JsonReadFeature.ALLOW_SINGLE_QUOTES)
				.defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
				.build();

		XmlMapper xmlMapper = XmlMapper.builder().findAndAddModules()
				.enable(XmlReadFeature.EMPTY_ELEMENT_AS_NULL)
				.defaultDateFormat(new SimpleDateFormat("yyyy-MM-dd")).build();

		builder.jsonMessageConverter(new JacksonJsonHttpMessageConverter(jsonMapper))
				.xmlMessageConverter(new JacksonXmlHttpMessageConverter(xmlMapper));
	}
}
