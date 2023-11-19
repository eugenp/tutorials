# Clojure Ring Examples

This project acts as a set of examples for the Clojure Ring library.

## Runing the examples

The examples can all be run from the Leiningen REPL.

Firstly, start the REPL with `lein repl`. Then the examples can be executed with:

* `(run simple-handler)` - A simple handler that just echos a constant string to the client
* `(run check-ip-handler)` - A handler that echos the clients IP Address back to them
* `(run echo-handler)` - A handler that echos the value of the "input" parameter back
* `(run request-count-handler)` - A handler with a session that tracks how many times this session has requested this handler

In all cases, the handlers can be accessed on http://localhost:3000.

## Relevant Articles
- [Writing Clojure Webapps with Ring](https://www.baeldung.com/clojure-ring)
