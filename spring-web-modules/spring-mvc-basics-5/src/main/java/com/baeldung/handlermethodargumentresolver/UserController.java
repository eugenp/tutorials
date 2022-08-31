package com.baeldung.handlermethodargumentresolver;

import org.codehaus.groovy.util.StringUtil;
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
        // userService.process(firstName, city)
        return ResponseEntity.ok()
            .body(String.format("{\"firstName\": %s, \"city\" : %s}", firstName, city));
    }

    @PostMapping("/process")
    public ResponseEntity process(@RequestBody UserDto user) {
        //userService.process(user.getFirstName(), user.getAddress().getCity());
        return ResponseEntity.ok()
            .body(user.toString());
    }

    @PostMapping("/create/custom")
    public ResponseEntity process(@JsonArg("$") UserDto user, @JsonArg("address") AddressDto address) {
        // userService.process(firstName, city)
        return ResponseEntity.ok()
            .body(String.format("{\"user\": %s, \"address\" : %s}", user, address));
    }
}