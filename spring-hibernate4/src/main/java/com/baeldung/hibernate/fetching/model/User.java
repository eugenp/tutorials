package com.baeldung.hibernate.fetching.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class User implements Serializable {
   
	private static final long serialVersionUID = 1L;
	private Long userId;
    private String userName;
    private String firstName;
    private String lastName;
    private Set<OrderDetail> orderDetail = new HashSet<OrderDetail>();

    public User() {

    }

    public User(final Long userId, final String userName, final String firstName, final String lastName) {
	super();
	this.userId = userId;
	this.userName = userName;
	this.firstName = firstName;
	this.lastName = lastName;

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((userId == null) ? 0 : userId.hashCode());
	return result;
    }

    @Override
    public boolean equals(final Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	final User other = (User) obj;
	if (userId == null) {
	    if (other.userId != null)
		return false;
	} else if (!userId.equals(other.userId))
	    return false;
	return true;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(final Long userId) {
	this.userId = userId;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(final String userName) {
	this.userName = userName;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(final String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(final String lastName) {
	this.lastName = lastName;
    }

	public Set<OrderDetail> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(Set<OrderDetail> orderDetail) {
		this.orderDetail = orderDetail;
	}

   

}
