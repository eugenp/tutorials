package com.baeldung.ejbmodule;

import javax.ejb.Remote;

@Remote
public interface TextProcessorRemote {

    String processText(String text);
}