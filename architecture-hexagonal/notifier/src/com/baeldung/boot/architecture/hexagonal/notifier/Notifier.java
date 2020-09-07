package com.baeldung.boot.architecture.hexagonal.notifier;

/**
 * @author Thanos Floros (thanosfloros@strong-programmer.com)
 */
public interface Notifier {

    void emailSend();

    void smsSend();


}
