package com.concertosoft;

import java.io.StringWriter;

import java.io.Writer;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.concertosoft.pojo.Payer;
@Entity
@Table(name="message_detail")
public class MessageDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int MsgID;
	
	public int getMsgID() {
		return MsgID;
	}

	public void setMsgID(int msgID) {
		MsgID = msgID;
	}

/*	@Column(name = "MessageName")
	private String messageName = "NA";*/
	
	@Column(name="Response")
	private String response;

	@Column(name="Request")
	private String request;

	
	@Column(name="RequestTs")
	private Date requestTs;
	
	@Column(name="ResponseTs")
	private Date responseTs;


/*	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String message) {
		this.messageName = message;
	}
*/
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
			this.request =request;
	}

	public Date getRequestTs() {
		return requestTs;
	}

	public void setRequestTs(Date requestTs) {
		this.requestTs = requestTs;
	}

	public Date getResponseTs() {
		return responseTs;
	}

	public void setResponseTs(Date responseTs) {
		this.responseTs = responseTs;
	}
	

		
}
