//<start id="ejb_HelloWorld" /> 
package com.habuma.ejb.session;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class HelloWorldBean implements SessionBean {
  public void ejbActivate() {  <co id="co_ejb_ejbActivate"/>
  }

  public void ejbPassivate() {
  }

  public void ejbRemove() {
  }

  public void setSessionContext(SessionContext ctx) {
  }

  public String sayHello() { <co id="co_ejb_sayHello"/>
    return "Hello World";
  }

  public void ejbCreate() {
  }
}
//<end id="ejb_HelloWorld" />

//<start id="spring_HelloWorld" /> 
package com.habuma.spring;

public class HelloWorldBean {
  public String sayHello() { <co id="co_spring_sayHello"/>
    return "Hello World";
  }
}
//<end id="spring_HelloWorld" />

