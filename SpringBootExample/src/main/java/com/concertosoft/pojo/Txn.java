package com.concertosoft.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;

import com.concertosoft.service.PayerService;

@XmlRootElement
public class Txn {
	

	
	String id ="Random String";
	String note="Eat.Fit Bill";
	String refId= "";
	String refUrl="TxnURL";




	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public String getRefId() {
		return refId;
	}



	public void setRefId(String refId) {
		this.refId = refId;
	}



	public String getRefUrl() {
		return refUrl;
	}



	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
	}



	public String getCustRef() {
		return custRef;
	}



	public void setCustRef(String custRef) {
		this.custRef = custRef;
	}



	public String getTs() {
		return ts;
	}



	public void setTs(String ts) {
		this.ts = ts;
	}

	/*
	 * @XmlElement(name = "Score") List<Score> scores= new ArrayList(); public
	 * List<Score> getScores() { return scores; }
	 * 
	 * 
	 * 
	 * public void setScores(List<Score> scores) { this.scores = scores; }
	 */



	String custRef="";
	String ts ="";
	




	 
	/*
	 * public Txn() {} public Txn(List<Score> scorelist) { scores = scorelist; }
	 */
	 

}


@XmlRootElement
class Score{
	String provider, type, value;
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlAttribute
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		provider = provider;
	}
	
	
}






/*
 * <Txn id="8ENSVVR4QOS7X1UGPY7JGUV444PL9T2C3QM" note="Restaurant Bill "
 * refId="2834473939393" refUrl="http://www.npci.org.in/" custRef="987656432321"
 * ts="2015-02-16T22:02:35+05:30" type="PAY" orgTxnId="">
 * <RiskScores>
<Score provider="psp1" type="TXNRISK" value="00030"/>
</RiskScores>
 * 
 */