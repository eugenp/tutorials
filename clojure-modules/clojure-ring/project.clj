(defproject baeldung-ring "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring/ring-devel "1.7.1"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler ring.core/simple-handler}
  :repl-options {:init-ns ring.core})
