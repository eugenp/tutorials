package com.baeldung

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import reactor.core.publisher.Flux
import java.util.UUID


@RestController
class SendEmitter(val eventRepository: EventRepository) {

    private var emitter = SseEmitter()

    /**
     * Save and send an SSE to all subscribed clients
     */
    @GetMapping("/saveEvent")
    fun executeExample(@RequestParam("eventName") eventName: String): Flux<Event> {
        return eventRepository.save(Event(UUID.randomUUID().toString(), eventName))
          .doOnNext { emitter.send(SseEmitter.event().data(it)) }
          .flux()
    }

    /**
     * Receive SSEs
     */
    @GetMapping("/receiveChanges")
    fun handle(): SseEmitter {
        // Create new emitter
        this.emitter = SseEmitter()
        // Return SSE
        return emitter
    }
}
