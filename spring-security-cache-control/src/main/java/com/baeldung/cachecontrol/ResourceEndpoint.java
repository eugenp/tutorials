package com.baeldung.cachecontrol;


import com.baeldung.cachecontrol.model.TimestampDto;
import com.baeldung.cachecontrol.model.UserDto;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

@Controller
public class ResourceEndpoint {

    @RequestMapping(value = "/users/{name}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(new UserDto(name));
    }


    @RequestMapping(value = "/timestamp", method = RequestMethod.GET)
    public ResponseEntity<TimestampDto> getServerTimestamp() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noStore())
                .cacheControl(CacheControl.noCache())
                .body(new TimestampDto(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()));
    }
}
