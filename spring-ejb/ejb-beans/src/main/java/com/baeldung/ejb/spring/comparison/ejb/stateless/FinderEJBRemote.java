package com.baeldung.ejb.spring.comparison.ejb.stateless;

import javax.ejb.Remote;

@Remote
public interface FinderEJBRemote {

    String search(String keyword);
}
