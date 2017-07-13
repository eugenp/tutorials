package com.baeldung.ejb.stateless;

import javax.ejb.Remote;

@Remote
public interface TextProcessorRemote {

    String processText(String text);
}
