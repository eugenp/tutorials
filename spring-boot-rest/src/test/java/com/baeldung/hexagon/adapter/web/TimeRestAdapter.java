package baeldung.hexagon.adapter.web;

import baeldung.hexagon.core.TimeService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/time")
public class TimeRestAdapter {

    private final TimeService timeService;

    public TimeRestAdapter(TimeService timeService) {
        this.timeService = timeService;
    }

    @GetMapping
    public String currentTime(@RequestParam(defaultValue = "yyyy-MM-dd") String format) {
        return timeService.currentTime(format);
    }

    @PostMapping("/{date}/{event}")
    public void addEvent(@PathVariable Instant date, @PathVariable String event) {
        timeService.addEvent(date, event);
    }
}
