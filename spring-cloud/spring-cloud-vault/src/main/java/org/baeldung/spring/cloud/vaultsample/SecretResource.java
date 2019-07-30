/**
 * 
 */
package org.baeldung.spring.cloud.vaultsample;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Philippe
 *
 */
@RestController
public class SecretResource {
    
    @Autowired
    Environment env;
    
    @GetMapping("/secret/{key}")
    public ResponseEntity<String> readSecret(@PathVariable("key") String key) {
        
        String value = env.getProperty(key);
        
        if ( value != null ) {        
            return new ResponseEntity<String>(value, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("not found", HttpStatus.NOT_FOUND);
        }              
    }
}
