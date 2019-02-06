package com.ticketapp.core.api.events;

/**
 * @author fabricioepa
 *
 * @param <EVT>
 * @param <OBJ>
 *            The main object to be returned as the content of this response
 */
public class ResponseEvent<EVT extends OutputEvent, OBJ> extends OutputEvent {

	private OBJ object;

	public ResponseEvent() {
	}

	@SuppressWarnings("unchecked")
	public EVT ok() {
		setCode(ResponseCode.OK);
		return (EVT) this;
	}

	@SuppressWarnings("unchecked")
	public EVT notFound() {
		setCode(ResponseCode.OBJECT_NOT_FOUND);
		return (EVT) this;
	}

	public OBJ getObject() {
		return object;
	}

	public void setObject(OBJ object) {
		this.object = object;
	}
}
