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

# Datasource defaults for local dev runs.
# This intentionally sets `SPRING_DATASOURCE_*` to avoid surprises from machine-specific
# `apiforge.datasource.*` overrides (e.g. system properties, other config sources).
DEFAULT_DB_HOST="${APIFORGE_DATASOURCE_HOST:-localhost:5432}"
DEFAULT_DB_NAME="${APIFORGE_DATASOURCE_DB_NAME:-devdb}"
DEFAULT_DB_USERNAME="${APIFORGE_DATASOURCE_USERNAME:-dev}"
DEFAULT_DB_PASSWORD="${APIFORGE_DATASOURCE_PASSWORD:-devpass}"
DEFAULT_DB_PARAMS="${APIFORGE_DATASOURCE_PARAMS:-sslmode=require&channelBinding=require}"
DEFAULT_JDBC_URL="jdbc:postgresql://${DEFAULT_DB_HOST}/${DEFAULT_DB_NAME}?${DEFAULT_DB_PARAMS}"

SPRING_DATASOURCE_URL="${SPRING_DATASOURCE_URL:-$DEFAULT_JDBC_URL}"
SPRING_DATASOURCE_USERNAME="${SPRING_DATASOURCE_USERNAME:-$DEFAULT_DB_USERNAME}"
SPRING_DATASOURCE_PASSWORD="${SPRING_DATASOURCE_PASSWORD:-$DEFAULT_DB_PASSWORD}"
SPRING_DATASOURCE_DRIVER_CLASS_NAME="${SPRING_DATASOURCE_DRIVER_CLASS_NAME:-org.postgresql.Driver}"

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
  SPRING_DATASOURCE_URL="$SPRING_DATASOURCE_URL" \
  SPRING_DATASOURCE_USERNAME="$SPRING_DATASOURCE_USERNAME" \
  SPRING_DATASOURCE_PASSWORD="$SPRING_DATASOURCE_PASSWORD" \
  SPRING_DATASOURCE_DRIVER_CLASS_NAME="$SPRING_DATASOURCE_DRIVER_CLASS_NAME" \
  nohup "$ROOT/mvnw" -q -pl "$s" spring-boot:run >"$log_file" 2>&1 &
  echo $! >"$pid_file"
  echo "$s: started (pid $(cat "$pid_file"))"
done
