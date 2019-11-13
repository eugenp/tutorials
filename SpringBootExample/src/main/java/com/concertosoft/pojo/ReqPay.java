package com.concertosoft.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;

import com.concertosoft.service.PayerService;

@XmlRootElement(name = "upi", namespace = "http://npci.org/upi/schema/")
public class ReqPay {

	@Autowired
	PayerService service;
	Txn Txn;
	Head Head;
	Payer Payer;
	Meta Meta;
@XmlElement
List<Tag> Metatags = new ArrayList();


public ReqPay() { 
Head = new Head();
Txn = new Txn();
Payer = new Payer();
Tag metaTag1 = new Tag("PAYREQSTART","");
Tag metaTag2 = new Tag("PAYREQEND", "");
Metatags.add(metaTag1);
Metatags.add(metaTag2);
Meta = new Meta(Metatags);

}



	@XmlElement
	public Txn getTxn() {
		return Txn;
	}
	public void setTxn(Txn txn) {
		Txn = txn;
	}
	
	@XmlElement
	public Payer getPayer() {
		return Payer;
	}
	public void setPayer(Payer payer) {
		Payer = payer;
	}
	
	@XmlElement
	public Head getHead() {
		return Head;
	}
	public void setHead(Head head) {
		this.Head = head;
	}
	

	

}
