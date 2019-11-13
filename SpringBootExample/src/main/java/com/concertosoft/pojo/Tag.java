package com.concertosoft.pojo;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
class Tag{
		
		public Tag() {}
		public Tag(String name, String value) {
			name=name;
			value = value;
		}
		
		
		 String name;
			String value;
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
	
	 }

