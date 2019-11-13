package com.concertosoft.pojo;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
class Info{
	
		Identity identityObj; 
		@XmlElement
		public Identity getIdentityObj() {
			return identityObj;
		}
		public void setIdentityObj(Identity identityObj) {
			this.identityObj = identityObj;
		}
		public Info() {}
		
		public Info(Identity obj) {
			identityObj =obj;
		}
		static class Identity{
			 int id=1;
			 @XmlAttribute
			 public int getId() {
				return id;
			}
			public void setId(int id) {
				this.id = id;
			}
			@XmlAttribute
			public String getVerifiedName() {
				return verifiedName;
			}
			public void setVerifiedName(String verifiedName) {
				this.verifiedName = verifiedName;
			}
			String verifiedName = "SV";
		 }
		 
	
	 }