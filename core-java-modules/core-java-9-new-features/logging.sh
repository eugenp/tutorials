# compile logging  module
# javac --module-path mods -d mods/com.baeldung.logging src/modules/com.baeldung.logging/module-info.java src/modules/com.baeldung.logging/com/baeldung/logging/*.java

# compile logging slf4j module
javac --module-path mods -d mods/com.baeldung.logging.slf4j src/modules/com.baeldung.logging.slf4j/module-info.java src/modules/com.baeldung.logging.slf4j/com/baeldung/logging/slf4j/*.java


# compile logging main app module
javac --module-path mods -d mods/com.baeldung.logging.app src/modules/com.baeldung.logging.app/module-info.java src/modules/com.baeldung.logging.app/com/baeldung/logging/app/*.java

# run logging main app
# java --module-path mods -m com.baeldung.logging.app/com.baeldung.logging.app.MainApp

# run looging main app using logback
java --module-path mods -Dlogback.configurationFile=mods/logback.xml -m com.baeldung.logging.app/com.baeldung.logging.app.MainApp
