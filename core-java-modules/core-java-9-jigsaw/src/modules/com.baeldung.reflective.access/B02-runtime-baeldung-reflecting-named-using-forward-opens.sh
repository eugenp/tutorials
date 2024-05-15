#!/bin/bash
java --module-path mods \
     --add-opens baeldung.reflected/com.baeldung.reflected.internal=baeldung.intermedium \
     --module baeldung.reflecting.named/com.baeldung.reflecting.named.MainWithForwardOpen
