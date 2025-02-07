package com.baeldung.mongo.tagging;

import java.util.List;

/**
 * Model for a blog post.
 * 
 * @author Donato Rimenti
 *
 */
public class Post {

	/**
	 * Title of the post. Must be unique.
	 */
	private String title;

	/**
	 * Author of the post.
	 */
	private String author;

	/**
	 * Tags of the post.
	 */
	private List<String> tags;

	/**
	 * Gets the {@link #title}.
	 *
	 * @return the {@link #title}
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the {@link #title}.
	 *
	 * @param title
	 *            the new {@link #title}
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the {@link #author}.
	 *
	 * @return the {@link #author}
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the {@link #author}.
	 *
	 * @param author
	 *            the new {@link #author}
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Gets the {@link #tags}.
	 *
	 * @return the {@link #tags}
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * Sets the {@link #tags}.
	 *
	 * @param tags
	 *            the new {@link #tags}
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Post other = (Post) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Post [title=" + title + ", author=" + author + ", tags=" + tags + "]";
	}

}
