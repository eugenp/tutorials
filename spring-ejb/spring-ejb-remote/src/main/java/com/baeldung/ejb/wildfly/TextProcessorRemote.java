package com.baeldung.ejb.wildfly;

import javax.ejb.Remote;

@Remote
public interface TextProcessorRemote {

    String processText(String text);
}