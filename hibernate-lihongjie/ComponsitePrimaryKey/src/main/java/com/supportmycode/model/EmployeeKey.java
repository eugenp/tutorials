package com.supportmycode.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EmployeeKey implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3257031815718949175L;
	
	@Column(name="cardnumber")
	Integer cardNumber;
	
	@Column(name="cardtype")
	String cardType;
	
	public EmployeeKey(int cardNumber, String cardType){
		this.cardNumber = cardNumber;
		this.cardType = cardType;
	}
	
	public Integer getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(Integer cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	@Override
	public int hashCode() {
	   final int prime = 31;
	   int result = 1;
	   result = prime * result + ((cardNumber == null) ? 0 : cardNumber.hashCode());
	   result = prime * result + ((cardType == null) ? 0 : cardType.hashCode());
	   return result;
	}

	
	@Override
	public boolean equals(Object obj) {
	   if (this == obj)
	      return true;
	   if (obj == null)
	      return false;
	   if (getClass() != obj.getClass())
	      return false;
	   EmployeeKey other = (EmployeeKey) obj;
	   if (cardNumber == null) {
	      if (other.cardNumber != null)
	         return false;
	      } else if (!cardNumber.equals(other.cardNumber))
	         return false;
	   if (cardType == null) {
	      if (other.cardType != null)
	         return false;
	      } else if (!cardType.equals(other.cardType))
	         return false;
	   return true;	
	  }

}
