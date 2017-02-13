package com.baeldung.cachecontrol;

import com.baeldung.cachecontrol.model.TimestampDto;
import com.baeldung.cachecontrol.model.UserDto;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

@Controller
public class ResourceEndpoint {

    @GetMapping(value = "/default/users/{name}")
    public ResponseEntity<UserDto> getUserWithDefaultCaching(@PathVariable String name) {
        return ResponseEntity.ok(new UserDto(name));
    }

    @GetMapping("/users/{name}")
    public ResponseEntity<UserDto> getUser(@PathVariable String name) {
        return ResponseEntity
          .ok()
          .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
          .body(new UserDto(name));
    }

    @GetMapping("/timestamp")
    public ResponseEntity<TimestampDto> getServerTimestamp() {
        return ResponseEntity
          .ok()
          .cacheControl(CacheControl.noStore())
          .body(new TimestampDto(LocalDateTime
            .now()
            .toInstant(ZoneOffset.UTC)
            .toEpochMilli()));
    }

    @GetMapping("/private/users/{name}")
    public ResponseEntity<UserDto> getUserNotCached(@PathVariable String name) {
        return ResponseEntity
          .ok()
          .body(new UserDto(name));
    }
}
