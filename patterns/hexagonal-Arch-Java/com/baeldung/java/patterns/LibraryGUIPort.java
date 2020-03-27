package com.baeldung.java.patterns;

import org.springframework.web.bind.annotation.*;

/**
 *
 * The Interface that acts as a Driver Port of the Hexagonal Architecture
 * @author Ganapathy Kumar
 */

public interface LibraryGUIPort {

    @PostMapping("subscribe")
    public void subscribe(@PathVariable String emailAddress);

    @DeleteMapping("unsubscribe")
    public void unSubscribe(@PathVariable String emailAddress);

}
