package com.ehidiamen.simplewebfluxhello2.handler;

import java.time.Duration;
import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ehidiamen.simplewebfluxhello2.model.Show;
import com.ehidiamen.simplewebfluxhello2.model.ShowEvent;
import com.ehidiamen.simplewebfluxhello2.repository.ReactiveShowRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ShowHandler {

  private final ReactiveShowRepository showRepository;

  public ShowHandler(ReactiveShowRepository showRepository) {

    this.showRepository = showRepository;

  }

  public Mono<ServerResponse> all(ServerRequest serverRequest) {

    Flux<Show> shows = this.showRepository.findAll();

    return ServerResponse.ok().body(shows, Show.class);

  }
  
  public Mono<ServerResponse> byId(ServerRequest serverRequest) {

	  String id = serverRequest.pathVariable("id");

	  Mono<Show> show = this.showRepository.findById(id);

	  return ServerResponse.ok().body(show, Show.class);

	}
  
  public Mono<ServerResponse> events(ServerRequest serverRequest) {
	    String showId = serverRequest.pathVariable("id");
	    Flux<ShowEvent> events = Flux.<ShowEvent>generate(sink -> sink.next(new ShowEvent(showId, new Date()))).delayElements(Duration.ofSeconds(1));
	    return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(events, ShowEvent.class);
  }

}
