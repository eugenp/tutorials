
package com.baeldung.hexagonal;

public class TranslationRequest {
	private String from;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public boolean isBeFormal() {
		return beFormal;
	}
	public void setBeFormal(boolean beFormal) {
		this.beFormal = beFormal;
	}
	public String getSourceText() {
		return sourceText;
	}
	public void setSourceText(String sourceText) {
		this.sourceText = sourceText;
	}
	public String getTranslatedText() {
		return translatedText;
	}
	public void setTranslatedText(String translatedText) {
		this.translatedText = translatedText;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	private String to;
	private boolean beFormal;
	private String sourceText;
	private String translatedText;
	private String user;
}