#!/usr/bin/env bash
set -euo pipefail

APP_COMMAND=""

case "$1" in
  aot)
    APP_COMMAND="java -Dspring.aot.enabled=true \
                     -Dspring.aot.repositories.enabled=false \
                     -agentlib:native-image-agent=config-output-dir=target/native-image-hints \
                     -jar spring-data-jpa-aot/target/spring-data-jpa-aot-0.0.1-SNAPSHOT.jar"
    ;;
  aot-repo)
    APP_COMMAND="java -Dspring.aot.enabled=true \
                      -Dspring.aot.repositories.enabled=true \
                      -agentlib:native-image-agent=config-output-dir=target/native-image-hints \
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
$APP_COMMAND &
APP_PID=$!
echo "Started APP with PID $APP_PID"

# --- 2. Start sampling in the background ---
sudo sample $APP_PID > sample_output.txt &
SAMPLE_PID=$!
echo "Sampling process started (PID $SAMPLE_PID)"
echo "Waiting for service at http://localhost:8080/get-user ..."

# --- 3. Poll the endpoint until it returns HTTP 200 ---
while [ "$(curl -s -o /dev/null -L -w ''%{http_code}'' http://localhost:8080/get-user)" != 200 ]
  do sleep 0.001;
done

# Capture elapsed time (SECONDS has fractional part) and the memory info
end_ns=$(date +%s%N)
elapsed_ms=$(((end_ns - start_ns) / 1000000))
MEMINFO=$(ps -o rss,vsz,pcpu,time -p $APP_PID | tail -1)

## --- 4. Stop sampling and show results ---
#echo "Stopping sample..."
#sudo kill -INT "$SAMPLE_PID"
#wait "$SAMPLE_PID" 2>/dev/null || true
#
## Give sample a moment to flush output
#sleep 2

# --- 5. Clean up ---
echo "Stopping startup process..."
kill "$APP_PID" 2>/dev/null || true

# Give app a moment to shutdown
sleep 3

echo "Done."

THREADS=$(grep -E "Thread_[0-9]+" sample_output.txt | wc -l)
echo "time elapsed $elapsed_ms millis"
echo "Threads: $THREADS"
echo "Memory/CPU (RSS KB / VSZ KB / %CPU / CPU Time): $MEMINFO"
