#!/usr/bin/env bash
set -euo pipefail

APP_COMMAND=""
URL="http://localhost:8080/get-user"
INTERVAL=0.01   # seconds between checks

case "$1" in
  aot)
    APP_COMMAND="java -Dspring.aot.enabled=true \
                     -Dspring.aot.repositories.enabled=false \
                     -jar spring-data-jpa-aot/target/spring-data-jpa-aot-0.0.1-SNAPSHOT.jar"
    ;;
  aot-repo)
    APP_COMMAND="java -Dspring.aot.enabled=true \
                      -Dspring.aot.repositories.enabled=true \
                      -Dspring.data.repositories.aot.enabled=true \
                      -jar spring-data-jpa-aot-repository/target/spring-data-jpa-aot-repository-0.0.1-SNAPSHOT.jar"
    ;;
  non-aot)
    APP_COMMAND="java -jar spring-data-jpa-not-aot/target/spring-data-jpa-not-aot-0.0.1-SNAPSHOT.jar"
    ;;
  *)
    echo "Error: Unknown mode '$1'. Use 'aot', 'aot-repo' or 'non-aot'."
    exit 1
    ;;
esac

# --- 1. Start your script in the background ---
# Record start time in milliseconds
start_ns=$(date +%s%N)

# --- 2. Start the application ---
$APP_COMMAND &
APP_PID=$!
echo "Started APP with PID: $APP_PID"

# --- 3. Poll the endpoint until it returns HTTP 200 ---
echo "Waiting for service at http://localhost:8080/get-user ..."
while [ "$(curl -s -o /dev/null -L -w ''%{http_code}'' $URL)" != 200 ]
  do sleep $INTERVAL;
done

# Capture elapsed time (SECONDS has fractional part) and the memory info
end_ns=$(date +%s%N)
elapsed_ms=$(((end_ns - start_ns) / 1000000))
MEMINFO=$(ps -o rss=,time= -p "$APP_PID")

# --- 5. Clean up ---
echo "Stopping startup process..."
kill "$APP_PID" 2>/dev/null || true

# Give app a moment to shutdown
sleep 3

echo "Done."
echo "==== RESULTS ===="
echo "time elapsed $elapsed_ms millis"
echo "Process Specific Memory/CPU (RSS KB / CPU Time): $MEMINFO"
