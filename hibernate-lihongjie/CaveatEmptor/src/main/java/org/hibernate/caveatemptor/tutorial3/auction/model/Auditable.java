package org.hibernate.caveatemptor.tutorial3.auction.model;

/**
 * A marker interface for auditable persistent domain classes.
 *
 * @author Christian Bauer
 */
public interface Auditable {

	public Long getId();
}
