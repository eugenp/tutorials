package hexagonal.architecture.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hexagonal.architecture.domain.SimCardService;

@RestController
@RequestMapping("/sim-cards")
public class SimCardController {

    private final SimCardService simCardService;

    public SimCardController(SimCardService simCardService) {
        this.simCardService = simCardService;
    }

    @PutMapping("/{iccid}/details/{pin}")
    ResponseEntity<Void> validatePin(@PathVariable("iccid") final String iccid,
        @PathVariable("pin") final String pin) {
        simCardService.validatePin(iccid, pin);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
