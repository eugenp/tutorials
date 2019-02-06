package com.ticketapp.core.api.events;

/**
 * Application Output event from the application as the source
 */
public class OutputEvent {

	private ResponseCode code = ResponseCode.OK;


	public OutputEvent() {
	}

	public ResponseCode getCode() {
		return code;
	}

	public void setCode(ResponseCode code) {
		this.code = code;
	}

}
