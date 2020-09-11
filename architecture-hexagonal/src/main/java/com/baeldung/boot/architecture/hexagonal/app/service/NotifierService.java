package com.baeldung.boot.architecture.hexagonal.app.service;

/**
 * @author Thanos Floros (thanosfloros@strong-programmer.com)
 */
public interface NotifierService {

    void emailSend();

    void smsSend();
}
