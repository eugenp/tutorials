package org.hibernate.caveatemptor.tutorial3.auction.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * An immutable class representing a comment from users.
 * <p>
 * Comments are always made in the context of an auction, that
 * is, a comment has a reference to an Item. The comment has
 * a free text field and a rating (using a typesafe enumeration).
 *
 * @see Item
 * @see User
 * @see Rating
 * @author Christian Bauer
 */
@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable, Comparable {

    @Id @GeneratedValue
    @Column(name = "COMMENT_ID")
    private Long id = null;

    @Version
    @Column(name = "OBJ_VERSION")
    private int version = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "RATING", nullable = false, updatable = false)
	private Rating rating;

    @Column(name = "COMMENT_TEXT", length = 4000, nullable = true)
	private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FROM_USER_ID", nullable = false, updatable = false)
    @org.hibernate.annotations.ForeignKey(name = "FK_FROM_USER_ID")
    private User fromUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ABOUT_ITEM_ID", nullable = false, updatable = false)
    @org.hibernate.annotations.ForeignKey(name = "FK_ABOUT_ITEM_ID")
	private Item item;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED", nullable = false, updatable = false)
	private Date created = new Date();

	/**
	 * No-arg constructor for JavaBean tools
	 */
	public Comment() {}

	/**
	 * Full constructor
	 *
	 * @param rating
	 * @param text
	 * @param fromUser
	 * @param item
	 */
	public Comment(Rating rating, String text, User fromUser, Item item) {
		this.rating = rating;
		this.text = text;
		this.fromUser = fromUser;
		this.item = item;
	}

	// ********************** Accessor Methods ********************** //

	public Long getId() { return id; }
    public int getVersion() { return version; }

	public Rating getRating() { return rating; }

	public String getText() { return text; }
	public void setText(String text) { this.text = text; }

	public User getFromUser() { return fromUser; }

	public Item getItem() { return item; }

	public Date getCreated() { return created; }

	// ********************** Common Methods ********************** //

	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Comment)) return false;

		final Comment comment = (Comment) o;

        if (! (created.getTime() == comment.created.getTime()) ) return false;
		if (!rating.equals(comment.rating)) return false;
		if (text != null ? !text.equals(comment.text) : comment.text != null) return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = rating.hashCode();
		result = 29 * result + (text != null ? text.hashCode() : 0);
		result = 29 * result + created.hashCode();
		return result;
	}

	public String toString() {
		return "Comment ('" + getId() + "'), " +
				"Rating: '" + getRating() + "'";
	}

	public int compareTo(Object o) {
		if (o instanceof Comment) {
            // Don't compare Date objects! Use the time in milliseconds!
            return Long.valueOf(this.getCreated().getTime()).compareTo(
                    Long.valueOf( ((Comment)o).getCreated().getTime())
                   );
		}
		return 0;
	}

	// ********************** Business Methods ********************** //

}
