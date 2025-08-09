package com.baeldung.spring.modulith.cqrs.ticket;

import org.jmolecules.architecture.cqrs.Command;

@Command
record BookTicket(Long movieId, String seat) {

}
