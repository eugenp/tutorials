package com.baeldung.jsonargs;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @PostMapping("/process/custom")
    public ResponseEntity process(@JsonArg("firstName") String firstName, @JsonArg("address.city") String city) {
        /* business processing */
        return ResponseEntity.ok()
          .body(String.format("{\"firstName\": %s, \"city\" : %s}", firstName, city));
    }

    @PostMapping("/process")
    public ResponseEntity process(@RequestBody UserDto user) {
        /* business processing */
        return ResponseEntity.ok()
          .body(user.toString());
    }

    @PostMapping("/process/custompojo")
    public ResponseEntity process(@JsonArg("firstName") String firstName, @JsonArg("lastName") String lastName, @JsonArg("address") AddressDto address) {
        /* business processing */
        return ResponseEntity.ok()
          .body(String.format("{\"firstName\": %s, \"lastName\": %s, \"address\" : %s}", firstName, lastName, address));
    }
}