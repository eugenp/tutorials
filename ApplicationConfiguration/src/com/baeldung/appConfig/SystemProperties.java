package com.baeldung.appConfig;

public class SystemProperties {

	public static void main(String[] args) {
		String propertyName = "log_dir";
		String defaultLogDir = "/tmp/log";
		String log_dir = System.getProperty(propertyName);
		if (log_dir == null) {
			log_dir = System.getProperty(propertyName, defaultLogDir);
		}
		System.out.println(log_dir);
	}
}
