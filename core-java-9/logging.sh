# compile logging module
javac -d mods/com.baeldung.logging src/modules/com.baeldung.logging/module-info.java src/modules/com.baeldung.logging/com/baeldung/logging/*.java

# compile logging main app module
javac -d mods/com.baeldung.logging.app src/modules/com.baeldung.logging.app/module-info.java src/modules/com.baeldung.logging.app/com/baeldung/logging/app/*.java

# run logging main app
java --module-path mods -m com.baeldung.logging.app/com.baeldung.logging.app.MainApp