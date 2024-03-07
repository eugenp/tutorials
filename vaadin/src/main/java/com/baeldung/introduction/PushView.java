package com.baeldung.introduction;

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

        // Publish server time once a second
        var serverTime = Flux.interval(Duration.ofSeconds(1))
                .map(o -> "Server time: " + Instant.now());


        serverTime.subscribe(time ->
                // ui.access is required to update the UI from a background thread
                getUI().ifPresent(ui ->
                        ui.access(() -> output.setText(time))
                )
        );

        add(output);
    }
}
