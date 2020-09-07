package com.baeldung.boot.architecture.hexagonal.notifier;

/**
 * @author Thanos Floros (thanosfloros@strong-programmer.com)
 */
public class NotifierImpl implements Notifier {
    @Override
    public void emailSend() {
        System.out.println("Send email here");
    }

    @Override
    public void smsSend() {
        System.out.println("Send SMS here");
    }
}
