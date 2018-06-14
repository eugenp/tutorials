package com.baeldung.reactive.webflux;

import java.io.Serializable;

public class Event implements Serializable {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1567178480491447436L;
		String id;
		String desc;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		public Event() {}
		
		public Event(String id) {
			super();
			this.id = id;
			this.desc = "Event with id [" + this.id + "] is created" ;
		}
		@Override
		public String toString() {
			return "Event [id=" + id + ", desc=" + desc + "]";
		}
		
		
		
		
}

