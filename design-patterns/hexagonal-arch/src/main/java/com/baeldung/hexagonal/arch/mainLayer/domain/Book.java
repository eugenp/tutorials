package com.baeldung.hexagonal.arch.mainLayer.domain;

public class Book {
    private String bookName;
	private String bookISBN;
    private String bookAuthor;
    private double bookPrice;
    
    public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookISBN() {
		return bookISBN;
	}
	public void setBookISBN(String bookISBN) {
		this.bookISBN = bookISBN;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public double getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(double bookPrice) {
		this.bookPrice = bookPrice;
	}
	@Override
	public String toString() {
		return "Book [bookName=" + bookName + ", bookISBN=" + bookISBN + ", bookAuthor=" + bookAuthor + ", bookPrice="
				+ bookPrice + "]";
	}
}
