package com.baeldung.boot.configurationproperties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

@Configuration
@ConfigurationProperties(prefix = "server")
public class PropertyConversion {

	private DataSize uploadSpeed;

	@DataSizeUnit(DataUnit.GIGABYTES)
	private DataSize downloadSpeed;
		
	private Duration backupDay;
	
	@DurationUnit(ChronoUnit.HOURS)
	private Duration backupHour;
	
	private Credentials credentials;
	
	public Duration getBackupDay() {
		return backupDay;
	}

	public void setBackupDay(Duration backupDay) {
		this.backupDay = backupDay;
	}

	public Duration getBackupHour() {
		return backupHour;
	}

	public void setBackupHour(Duration backupHour) {
		this.backupHour = backupHour;
	}

	public DataSize getUploadSpeed() {
		return uploadSpeed;
	}

	public void setUploadSpeed(DataSize uploadSpeed) {
		this.uploadSpeed = uploadSpeed;
	}

	public DataSize getDownloadSpeed() {
		return downloadSpeed;
	}

	public void setDownloadSpeed(DataSize downloadSpeed) {
		this.downloadSpeed = downloadSpeed;
	}

	public Credentials getCredentials() {
		return credentials;
	}
	
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
}
