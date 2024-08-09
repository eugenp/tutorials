package com.baeldung.ddd.hexagonal.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Rental implements Serializable{
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private Book book;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private BigDecimal fine;
    
    public Rental() {}
	public Rental(Long id, Book book, LocalDate rentDate, LocalDate returnDate, BigDecimal fine) {
		super();
		this.id = id;
		this.book = book;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
		this.fine = fine;
	}
	
	public Rental(Book book, LocalDate rentDate, LocalDate returnDate, BigDecimal fine) {
		super();
		this.book = book;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
		this.fine = fine;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public LocalDate getRentDate() {
		return rentDate;
	}
	public void setRentDate(LocalDate rentDate) {
		this.rentDate = rentDate;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	public BigDecimal getFine() {
		return fine;
	}
	public void setFine(BigDecimal fine) {
		this.fine = fine;
	}
    
}
