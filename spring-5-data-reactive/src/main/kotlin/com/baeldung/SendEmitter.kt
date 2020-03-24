package com.baeldung

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class SendEmitter(val eventRepository: EventRepository) {

    @GetMapping(value = "/save", produces = arrayOf(MediaType.TEXT_EVENT_STREAM_VALUE))
    fun executeExample(@RequestParam("eventName") eventName: String) =
            eventRepository.save(Event(UUID.randomUUID().toString(), eventName)).flux()
}
