package com.baeldung

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*


@RestController
class SendEmitter(val eventRepository: EventRepository) {

    private var emitter = SseEmitter()

    /**
     *
     */
    @GetMapping("/saveEvent")
    fun executeExample(@RequestParam("eventName") eventName: String): Flux<Event> {
        // Create new event
        var event = Event(UUID.randomUUID().toString(), eventName)
        // Send event
        emitter.send(SseEmitter.event().data(event))
        // Return reactive event
        return eventRepository.saveAll(Mono.just(event))
    }

    /**
     *
     */
    @GetMapping(value = "/receiveChanges")
    fun handle(): SseEmitter {
        // Create new emitter
        this.emitter = SseEmitter()
        return emitter
    }
}
