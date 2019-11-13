package com.concertosoft.pojo;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ac {
	
	List<Detail> Detail=new ArrayList<Detail>();
		 String addrType ="CARD";
		 
		 public Ac() {}
		 public Ac(List<Detail> detailObj) {
			 Detail = detailObj;
		 }
		 
		 @XmlAttribute
		 public String getAddrType() {
			return addrType;
		}
		public void setAddrType(String addrType) {
			this.addrType = addrType;
		}
		@XmlElement
		public List<Detail> getDetail() {
			return Detail;
		}
		public void setDetail(List<Detail> desc) {
			this.Detail = desc;
		}

		static class Detail{
			 String name="xmh";
			 @XmlAttribute
			 public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			@XmlAttribute
			public String getValue() {
				return value;
			}
			public void setValue(String value) {
				this.value = value;
			}
			String value="value";
		 }
	 

}
