package com.baeldung.introduction;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;

// Ensure you have @Push annotation in your Application class
@Route("push")
public class PushView extends VerticalLayout {

    public PushView() {
        var output = new Span();

        add(new H1("Server Push"), output);

        Flux.interval(Duration.ofSeconds(1))
                .map(o -> "Server time: " + Instant.now())
                .subscribe(time -> getUI().ifPresent(ui -> ui.access(() -> {
                    output.setText(time);
                })));
    }
}
