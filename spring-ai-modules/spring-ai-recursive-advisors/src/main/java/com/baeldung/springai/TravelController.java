package com.baeldung.springai;

import lombok.RequiredArgsConstructor;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/travel")
@RequiredArgsConstructor
public class TravelController {

    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    private final TravelService travelService;

    @GetMapping(
            value = "/tips",
            produces = MediaType.TEXT_HTML_VALUE
    )
    public String getTips(
            @RequestParam(defaultValue = "Paris")
            String destination
    ) {
        final var markdown =  travelService.getTravelTip(destination);
        return renderer.render(parser.parse(markdown));
    }
}