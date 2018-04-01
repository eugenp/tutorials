package org.hibernate.caveatemptor.tutorial4.auction.model;

/**
 * A simple enumeration.
 *
 * @author Christian Bauer
 */
public enum ItemState {

    DRAFT('D'), PENDING('P'), ACTIVE('A');

    private final char state;

	private ItemState(char state) {
		this.state = state;
	}

    public char value() {
        return state;
    }

	public String toString() {
		return Character.toString(state);
	}
}