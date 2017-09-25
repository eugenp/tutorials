package com.baeldung.differenttypesdi.shared;

public class Processor {

	private Integer currentChannel;

	public Processor(Integer lastChannel) {
		this.setCurrentChannel(lastChannel);
	}

	public Integer getCurrentChannel() {
		return currentChannel;
	}

	public void setCurrentChannel(Integer currentChannel) {
		this.currentChannel = currentChannel;
	}
	
	public void setNextChannel() {
		this.currentChannel++;
	}
	
	public void setPrevChannel() {
		this.currentChannel--;
	}

}
