package com.concertosoft.pojo;

import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Meta {

	List<Tag> Tags =new ArrayList();

	public Meta() {}
	
	public Meta(List<Tag> tagList) {
		Tags = tagList;
	}
	public List<Tag> getTags() {
		return Tags;
	}

	public void setTags(List<Tag> tags) {
		Tags = tags;
	}
}
