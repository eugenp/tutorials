package com.baeldung.boot.configurationproperties;

import java.util.Map;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "server")
public class ServerConfig {

	private String name;
	private Address address;
	private Map<String, String> dirs;
	private Set<String> imgIds;
	
	public static class Address {
		
		private String ip;
		private int port;
		
		public String getIp() {
			return ip;
		}
		
		public void setIp(String ip) {
			this.ip = ip;
		}
		
		public int getPort() {
			return port;
		}
		
		public void setPort(int port) {
			this.port = port;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<String> getImgIds() {
		return imgIds;
	}
	
	public void setImgIds(Set<String> imgIds) {
		this.imgIds = imgIds;
	}

	public Map<String, String> getDirs() {
		return dirs;
	}

	public void setDirs(Map<String, String> dirs) {
		this.dirs = dirs;
	}
}
