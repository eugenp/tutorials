package com.baeldung.microprofile;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.TreeSet;

@ApplicationPath("/library")
public class LibraryApplication extends Application {
}
