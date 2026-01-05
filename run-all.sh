#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PID_DIR="$ROOT/.run/pids"
LOG_DIR="$ROOT/.run/logs"
SERVICES=(api-gateway auth-service content-service content-type-service media-service permission-service)

mkdir -p "$PID_DIR" "$LOG_DIR"

if [ ! -x "$ROOT/mvnw" ]; then
  chmod +x "$ROOT/mvnw"
fi

if [ "${SKIP_BUILD:-0}" != "1" ]; then
  services_csv="$(IFS=,; echo "${SERVICES[*]}")"
  echo "Building services: $services_csv"
  "$ROOT/mvnw" -q -DskipTests -pl "$services_csv" -am clean package
fi

is_running() {
  local pid_file="$1"
  [ -f "$pid_file" ] || return 1
  local pid
  pid="$(cat "$pid_file")"
  kill -0 "$pid" 2>/dev/null
}

for s in "${SERVICES[@]}"; do
  pid_file="$PID_DIR/$s.pid"
  if is_running "$pid_file"; then
    echo "$s: already running (pid $(cat "$pid_file"))"
    continue
  fi

  log_file="$LOG_DIR/$s.log"
  nohup "$ROOT/mvnw" -q -pl "$s" spring-boot:run >"$log_file" 2>&1 &
  echo $! >"$pid_file"
  echo "$s: started (pid $(cat "$pid_file"))"
done
