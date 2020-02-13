package com.baeldung.boot.configurationproperties;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class CustomCredentialsConverter implements Converter<String, Credentials> {

	@Override
	public Credentials convert(String source) {
		String data[] = source.split(",");
		return new Credentials(data[0], data[1]);
	}
}
