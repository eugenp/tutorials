package com.ticketapp.core.api.events;

/**
 * The update command event
 * 
 * @param <OBJ>
 *            The object with the update data
 * @param <EVT>
 */
public class UpdateEvent<OBJ, EVT> extends CommandEvent {

	private OBJ object;

	public OBJ getObject() {
		return object;
	}

	public void setObject(OBJ object) {
		this.object = object;
	}

	@SuppressWarnings("unchecked")
	public EVT withObject(OBJ transferObject) {
		setObject(transferObject);
		return (EVT) this;
	}

}
