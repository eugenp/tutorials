
package com.baeldung.portsandadaptors.rest;

import com.baeldung.portsandadaptors.core.model.User;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author DeependraTewari
 */
@RestController
@RequestMapping("/user")
public class NewRestController {

    private final HttpFacade httpFacade;
    
    @Autowired
    public NewRestController(HttpFacade facade) {
        httpFacade = facade;
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public Object handleError(HttpServletRequest req, Exception ex) {
        Object errorObject = new Object();
        return errorObject;
    }
    
    @PostMapping
    public ResponseEntity<?> addNew(@RequestBody User u) {
        u = httpFacade.addNew(u);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User u, @PathVariable Long id) {
        u = httpFacade.addNew(u);
        return ResponseEntity.ok(u);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(httpFacade.findByID(id));
    }

    @GetMapping("/{name}")
    public List<User> findByName(@PathVariable String name) {
        return httpFacade.findByName(name);
    }

    @GetMapping("/t/{thandle}")
    public ResponseEntity<?> findByTwitterHandle(@PathVariable String tHandle) {
        return ResponseEntity.ok(httpFacade.findByTwitterHandle(tHandle));
    }
    
}
