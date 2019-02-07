package com.baeldung.inprogress.hexagonal.channel.defaultchannel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.baeldung.inprogress.hexagonal.channel.defaultchannel")
public class DefaultChannelConfig {
	@Bean
	public DefaultChannelPackageSearchService defaultChannelPackageSearchService()
	{
		return new DefaultChannelPackageSearchService();
	}
}
