package com.baeldung.templates;

import io.jstach.jstache.JStache;

@JStache(path = "templates/jstachio/jstachio.mustache")
public record JStachioModel(String name) {
}
