package com.concertosoft.pojo;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cred {
	String type="CARD";
	String subType="PIN";
	Cred(){}
	Cred(Data block){datablock = block;}
	Data datablock;
	@XmlElement
	public Data getDatablock() {
		return datablock;
	}

	public void setDatablock(Data datablock) {
		this.datablock = datablock;
	}



	
	
	@XmlAttribute
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
@XmlAttribute
	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}
	
	

	static class Data{
		String code = "codee";
		String ki="00008";
		@XmlAttribute
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		@XmlAttribute
		public String getKi() {
			return ki;
		}
		public void setKi(String ki) {
			this.ki = ki;
		}
	}

}
