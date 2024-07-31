#!/bin/bash
java --module-path mods \
     -javaagent:mods/baeldung-agent.jar=com.baeldung.reflected.internal.InternalNonPublicClass,com.baeldung.reflecting.named.Main \
     --module baeldung.reflecting.named/com.baeldung.reflecting.named.Main
