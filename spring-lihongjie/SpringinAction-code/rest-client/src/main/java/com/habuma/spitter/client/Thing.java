package com.habuma.spitter.client;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Thing {
  private String foo;
  private long bar;
  private boolean baz;
  public String getFoo() {
    return foo;
  }
  public void setFoo(String foo) {
    this.foo = foo;
  }
  public long getBar() {
    return bar;
  }
  public void setBar(long bar) {
    this.bar = bar;
  }
  public boolean isBaz() {
    return baz;
  }
  public void setBaz(boolean baz) {
    this.baz = baz;
  }
}
