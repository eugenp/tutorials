#!/usr/bin/env bash
set -euo pipefail

# =========================
# CONFIGURATION
# =========================
APP_COMMAND=""
URL="http://localhost:8080/hello"
URL_LOAD="http://localhost:8080/get-user"
INTERVAL=0.01
DURATION_SEC=30
TPS=300
MAX_CONCURRENCY=300

MEM_SAMPLE_INTERVAL_NS=1000000000  # sample max memory every 1 second
PEAK_RSS=0

# =========================
# SELECT MODE
# =========================
case "${1:-}" in
  aot)
    APP_COMMAND="java -Dspring.aot.enabled=true \
      -Dspring.aot.repositories.enabled=false \
      -jar spring-data-jpa-aot/target/spring-data-jpa-aot-0.0.1-SNAPSHOT.jar"
    ;;
  aot-repo)
    APP_COMMAND="java -Dspring.aot.enabled=true \
      -Dspring.aot.repositories.enabled=true \
      -jar spring-data-jpa-aot-repository/target/spring-data-jpa-aot-repository-0.0.1-SNAPSHOT.jar"
    ;;
  non-aot)
    APP_COMMAND="java -jar spring-data-jpa-not-aot/target/spring-data-jpa-not-aot-0.0.1-SNAPSHOT.jar"
    ;;
  *)
    echo "Usage: $0 {aot|aot-repo|non-aot}"
    exit 1
    ;;
esac

# =========================
# START APPLICATION
# =========================
echo "Starting application..."
start_ns=$(date +%s%N)

bash -c "$APP_COMMAND" &
APP_PID=$!

echo "PID: $APP_PID"
echo "Waiting for service at $URL ..."

# Wait until HTTP 200
while [[ "$(curl -s -o /dev/null -L -w "%{http_code}" "$URL")" != "200" ]]; do
  sleep "$INTERVAL"
done

end_ns=$(date +%s%N)
elapsed_ms=$(((end_ns - start_ns) / 1000000))

startup_mem=$(ps -o rss=,time= -p "$APP_PID")

echo ""
echo "==== STARTUP RESULTS ===="
echo "Startup time: ${elapsed_ms} ms"
echo "Memory/CPU (RSS KB / TIME): $startup_mem"

# =========================
# LOAD TEST SETUP
# =========================
echo ""
echo "==== LOAD TEST ===="
echo "URL: $URL_LOAD"
echo "TPS: $TPS"
echo "Duration: ${DURATION_SEC}s"

START_NS=$(date +%s%N)
END_NS=$((START_NS + DURATION_SEC * 1000000000))
INTERVAL_NS=$((1000000000 / TPS))
next_time=$START_NS

METRICS_FILE=$(mktemp)
MEMORY_BEFORE=$(ps -o rss=,time= -p "$APP_PID")

# =========================
# REQUEST FUNCTION
# =========================
get() {
  local url="$1"
  local start end duration_ms result code time_total

  start=$(date +%s%3N)

  result=$(curl --max-time 2 -s -o /dev/null -w "%{http_code} %{time_total}" "$url")
  code=$(awk '{print $1}' <<< "$result")
  time_total=$(awk '{print $2}' <<< "$result")

  end=$(date +%s%3N)
  duration_ms=$((end - start))

  echo "$code $time_total $duration_ms" >> "$METRICS_FILE"
}

# =========================
# LOAD GENERATION (TPS + CONCURRENCY CONTROL)
# =========================
last_mem_sample=0
while [[ "$(date +%s%N)" -lt "$END_NS" ]]; do
  now=$(date +%s%N)
  if (( now - last_mem_sample >= MEM_SAMPLE_INTERVAL_NS )); then
    current_rss=$(ps -o rss= -p "$APP_PID")

    if [[ "$current_rss" -gt "$PEAK_RSS" ]]; then
      PEAK_RSS=$current_rss
    fi

    last_mem_sample=$now
  fi

  while [[ "$now" -ge "$next_time" ]]; do

    # ---- concurrency cap (THIS is the new part) ----
    while [[ "$(jobs -rp | wc -l)" -ge "$MAX_CONCURRENCY" ]]; do
      sleep 0.001
    done

    get "$URL_LOAD" &

    next_time=$((next_time + INTERVAL_NS))
  done

  sleep 0.001
done

# Allow in-flight requests to finish (bounded)
sleep 1

# =========================
# SHUTDOWN
# =========================
MEMORY_AFTER=$(ps -o rss=,time= -p "$APP_PID")

echo "Stopping application..."
kill "$APP_PID" 2>/dev/null || true
sleep 2

# =========================
# RESULTS
# =========================
echo ""
echo "==== RESULTS ===="

total=$(wc -l < "$METRICS_FILE")
success=$(awk '$1 ~ /^2/ {c++} END {print c+0}' "$METRICS_FILE")
fail=$((total - success))

avg_time=$(awk '{sum+=$2} END {if (NR>0) print sum/NR}' "$METRICS_FILE")
avg_duration=$(awk '{sum+=$3} END {if (NR>0) print sum/NR}' "$METRICS_FILE")

p95=$(awk '{print $2}' "$METRICS_FILE" | sort -n | awk '{a[NR]=$1} END {print a[int(NR*0.95)]}')
max_time=$(awk '{print $2}' "$METRICS_FILE" | sort -n | tail -1)

echo "Total requests: $total"
echo "Success (2xx): $success"
echo "Failed: $fail"

echo "Avg time (curl): ${avg_time}s"
echo "Avg duration (measured): ${avg_duration}ms"
echo "P95: ${p95}s"
echo "Max: ${max_time}s"

echo ""
echo "Max memory utilised: $PEAK_RSS"
echo "Memory/CPU (RSS KB / TIME):"
echo "Before: $MEMORY_BEFORE"
echo "After : $MEMORY_AFTER"

rm -f "$METRICS_FILE"
