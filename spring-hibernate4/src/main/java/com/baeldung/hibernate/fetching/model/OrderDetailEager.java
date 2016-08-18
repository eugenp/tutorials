package com.baeldung.hibernate.fetching.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "USER_ORDER")
public class OrderDetailEager implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="ORDER_ID")
	private Long orderId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="USER_ID")
	private UserEager user;
	
	public OrderDetailEager(){	
	}
		
	public OrderDetailEager(Date orderDate, String orderDesc) {
		super();	
	}
	
	public UserEager getUser() {
		return user;
	}
	
	public void setUser(UserEager user) {
		this.user = user;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
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
		OrderDetailEager other = ( OrderDetailEager) obj;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		
		return true;
	}

	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}


