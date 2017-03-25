// package org.baeldung.lagom.helloworld.greeting.impl;
//
// import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
//
// @JsonDeserialize
// public class ReceivedGreetingEventImpl implements GreetingEvent.ReceivedGreetingEvent {
//
// private final Long id;
// private final String fromUser;
//
// public ReceivedGreetingEventImpl(Long id, String fromUser) {
// super();
// this.id = id;
// this.fromUser = fromUser;
// }
//
// @Override
// public Long getId() {
// return id;
// }
//
// @Override
// public String fromUser() {
// return fromUser;
// }
//
// }
