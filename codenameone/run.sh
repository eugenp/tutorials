#!/bin/bash
MVNW="./mvnw"

function simulator {
  
  "$MVNW" "verify" "-Psimulator" "-DskipTests" "-Dcodename1.platform=javase" "-e"
}
function desktop {
  
  "$MVNW" "verify" "-Prun-desktop" "-DskipTests" "-Dcodename1.platform=javase" "-e"
}
function settings {
  
  "$MVNW" "cn1:settings" "-e"
}
function update {
  
  "$MVNW" "cn1:update" "-U" "-e"
}
function help {
  "echo" "-e" "run.sh [COMMAND]"
  "echo" "-e" "Commands:"
  "echo" "-e" "  simulator"
  "echo" "-e" "    Runs app using Codename One Simulator"
  "echo" "-e" "  desktop"
  "echo" "-e" "    Runs app as a desktop app."
  "echo" "-e" "  settings"
  "echo" "-e" "    Opens Codename One settings"
  "echo" "-e" "  update"
  "echo" "-e" "    Update Codename One libraries"
}
CMD=$1

if [ "$CMD" == "" ]; then
  CMD="simulator"
fi
"$CMD" 