package com.baeldung.dependency.injectiontypes;

import org.springframework.beans.factory.annotation.Required;

public class RoomService {

	private BedService bedService;

	@Required
	public void setBedService(BedService bedService) {
		this.bedService = bedService;
	}
}
