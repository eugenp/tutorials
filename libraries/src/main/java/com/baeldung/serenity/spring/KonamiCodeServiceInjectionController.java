package com.baeldung.serenity.spring;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author aiet
 */
@RequestMapping(value = "/konamicode", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class KonamiCodeServiceInjectionController {

    private KonamiCodeService konamiCodeService;

    public KonamiCodeServiceInjectionController(KonamiCodeService konamiCodeService) {
        this.konamiCodeService = konamiCodeService;
    }

    @PutMapping("/stages")
    public void clearStage(@RequestParam String action) {
        if ("clear".equals(action)) {
            konamiCodeService.clearStage();
        }
    }

    @GetMapping("/classic")
    public String classicCode() {
        return konamiCodeService.getClassicCode();
    }

    @GetMapping("/cheatable")
    public boolean cheatCheck(@RequestParam String cheatcode) {
        return konamiCodeService.cheatWith(cheatcode);
    }

    @GetMapping("/stages")
    public int stageLeft() {
        return konamiCodeService.stageLeft();
    }

}
