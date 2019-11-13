package com.concertosoft.pojo;

import java.util.ArrayList;


import java.util.List;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;

import com.concertosoft.service.PayerService;



@XmlRootElement
public class Payer {
	
	@Autowired
	PayerService service;
	
String name="Smitha", address="Tellapur";
int code=10;

Info Info; 
Device device ;
@XmlElement(name = "Tag")
ArrayList tags = new ArrayList();





List detailList = new ArrayList();
Ac.Detail one = new Ac.Detail();

Cred credBlock;
@XmlElement
public Cred getCredBlock() {
	return credBlock;
}

public void setCredBlock(Cred credBlock) {
	this.credBlock = credBlock;
}
@XmlElement
public Ac getAcTag() {
	return acTag;
}

public void setAcTag(Ac acTag) {
	this.acTag = acTag;
}

Ac acTag ;

	public Payer() { 
	Info = new Info(new Info.Identity());										//	initializing the objects;	
	Tag t1 = new Tag("mobile","Samsung");
	Tag t2 = new Tag("Geocode","38729302");
	tags.add(t1);
	tags.add(t2);
	device = new Device(tags);
	detailList.add(one);
	acTag = new Ac(detailList);
	Cred.Data Data = new Cred.Data();
	credBlock = new Cred(Data);
	}
	
	@XmlElement(required = true)
	public Info getInfoTag() {
		return Info;
	}

	public void setInfoTag(Info infoTag) {
		this.Info = infoTag;
	}

	
	enum Type {PERSON, ENTITY}				// need to work on this.
	Double amount =500.00;

	private String currency;
	

	@XmlAttribute   
	 public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute  
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@XmlAttribute  
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@XmlAttribute
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@XmlAttribute
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String curr) {
		this.currency = curr;
	}
	
	

}