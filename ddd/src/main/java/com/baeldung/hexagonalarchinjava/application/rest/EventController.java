package com.baeldung.hexagonalarchinjava.application.rest;

import com.baeldung.hexagonalarchinjava.application.request.ParticipantRequest;
import com.baeldung.hexagonalarchinjava.application.request.CreateEventRequest;
import com.baeldung.hexagonalarchinjava.application.response.CreateEventResponse;
import com.baeldung.hexagonalarchinjava.domain.Event;
import com.baeldung.hexagonalarchinjava.domain.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/events")
public class EventController {

	private final EventService eventService;

	@Autowired
	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CreateEventResponse createEvent(@RequestBody final CreateEventRequest createEventRequest) {
		final Event event = new Event(UUID.randomUUID(), createEventRequest.getEventName(),
				createEventRequest.getOrganiser(),createEventRequest.getEventLocation(),
				createEventRequest.getEventTime(), createEventRequest.getMinAgeRequired());

		final UUID id = eventService.createEvent(event);

		return new CreateEventResponse(id);
	}

	@PostMapping(value = "/{id}/participant", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addParticipant(@PathVariable final UUID id,@RequestBody final ParticipantRequest addProductRequest) {
		eventService.addParticipant(id, addProductRequest.getParticipant());
	}

	@DeleteMapping(value = "/{id}/participant", consumes = MediaType.APPLICATION_JSON_VALUE)
	public void removeParticipant(@PathVariable final UUID id,
			@RequestBody final ParticipantRequest deleteProductRequest) {
		eventService.removeParticipant(id, deleteProductRequest.getParticipant());
	}

	@PutMapping("/{id}")
	public void cancelEvent(@PathVariable final UUID id) {
		eventService.cancelEvent(id);
	}
	
	@PutMapping("/{id}/complete")
	public void completeEvent(@PathVariable final UUID id) {
		eventService.completeEvent(id);
	}

	@DeleteMapping("/{id}")
	public void deleteEvent(@PathVariable final UUID id) {
		eventService.deleteEvent(id);
	}
}
