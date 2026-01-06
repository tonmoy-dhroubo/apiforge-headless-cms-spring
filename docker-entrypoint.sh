#!/usr/bin/env bash
set -euo pipefail

ROOT="/app"
PID_DIR="$ROOT/.run/pids"
LOG_DIR="$ROOT/.run/logs"
UPLOAD_DIR="$ROOT/uploads"
SERVICES=(api-gateway auth-service content-type-service content-service media-service permission-service)

mkdir -p "$PID_DIR" "$LOG_DIR" "$UPLOAD_DIR"

declare -A service_pids=()

stop_all() {
  set +e
  for s in "${SERVICES[@]}"; do
    pid_file="$PID_DIR/$s.pid"
    if [ -f "$pid_file" ]; then
      pid="$(cat "$pid_file" 2>/dev/null || true)"
      if [ -n "${pid:-}" ] && kill -0 "$pid" 2>/dev/null; then
        kill "$pid" 2>/dev/null || true
      fi
      rm -f "$pid_file" || true
    fi
  done
  wait || true
}

on_term() {
  stop_all
  exit 0
}

trap on_term SIGTERM SIGINT

for s in "${SERVICES[@]}"; do
  log_file="$LOG_DIR/$s.log"
  : >"$log_file"

  java ${JAVA_OPTS:-} -jar "$ROOT/services/$s.jar" >>"$log_file" 2>&1 &
  pid=$!

  echo "$pid" >"$PID_DIR/$s.pid"
  service_pids["$s"]="$pid"
  echo "$s: started (pid $pid)"
done

tail -n 0 -F "$LOG_DIR/"*.log &
tail_pid=$!

set +e
wait -n "${service_pids[@]}"
exit_code=$?
set -e

kill "$tail_pid" 2>/dev/null || true
stop_all
exit "$exit_code"
