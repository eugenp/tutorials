package com.baeldung

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import javax.ws.rs.core.MediaType


@RestController
class SendEmitter(val eventRepository: EventRepository) {

    private var emitter = SseEmitter()

    /**
     * Save and send an SSE to all subscribed clients
     */
    @GetMapping("/saveEvent")
    fun executeExample(@RequestParam("eventName") eventName: String): Flux<Event> {
        // Create new event
        var event = Event(UUID.randomUUID().toString(), eventName)
        // Save event
        var stream = eventRepository.saveAll(Mono.just(event))
        // Send event
        emitter.send(SseEmitter.event().data(event))
        // Return SSE
        return stream
    }

    /**
     * Receive SSEs
     */
    @GetMapping(value = "/receiveChanges")
    fun handle(): SseEmitter {
        // Create new emitter
        this.emitter = SseEmitter()
        // Return SSE
        return emitter
    }
}
