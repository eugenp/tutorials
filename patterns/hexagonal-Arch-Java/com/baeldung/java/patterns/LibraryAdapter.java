package com.baeldung.java.patterns;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * the Adapter class that implements the GUI Port which acts
 * as the user interface. this class implements the two operations that we exposed through the Port.
 * @author Ganapathy Kumar
 */

@RestController
@RequestMapping("/library/")
public class LibraryAdapter implements LibraryGUIPort {

    @Autowired
    private LibraryRepositoryAdapter libraryRepositoryAdapter;

    @Override
    public void subscribe(String emaidAddress) {
        libraryRepositoryAdapter.subscribe(emaidAddress);
    }

    @Override
    public void unSubscribe(String emailAddress) {
        libraryRepositoryAdapter.unSubscribe(emailAddress);
    }


}
