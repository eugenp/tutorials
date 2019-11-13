
  package com.concertosoft.pojo; 
  import java.util.List;
  
  import javax.xml.bind.annotation.XmlAttribute;
  import javax.xml.bind.annotation.XmlElement; 
  import javax.xml.bind.annotation.XmlRootElement;
  
  @XmlRootElement 
  public class Device {
  
  public Device () {} 
  public Device(List<Tag> tags)
  { tag = tags; }
  
  List<Tag> tag;
  
  @XmlElement
  public List<Tag> getTag() 
  { return tag; } 
  public void setTag(List<Tag> details)
  { this.tag = details; }
  
  }
 
