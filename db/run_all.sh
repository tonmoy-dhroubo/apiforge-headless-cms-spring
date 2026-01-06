#!/usr/bin/env bash
set -euo pipefail

DB_HOST=${DB_HOST:-localhost}
DB_PORT=${DB_PORT:-5432}
DB_NAME=${DB_NAME:-devdb}
DB_USER=${DB_USER:-dev}
DB_PASSWORD=${DB_PASSWORD:-devpass}
DB_SSLMODE=${DB_SSLMODE:-require}
DB_CHANNEL_BINDING=${DB_CHANNEL_BINDING:-require}

export PGPASSWORD="$DB_PASSWORD"
PSQL_ADMIN_CONN="host=$DB_HOST port=$DB_PORT dbname=postgres user=$DB_USER sslmode=$DB_SSLMODE channel_binding=$DB_CHANNEL_BINDING"
PSQL_CONN="host=$DB_HOST port=$DB_PORT dbname=$DB_NAME user=$DB_USER sslmode=$DB_SSLMODE channel_binding=$DB_CHANNEL_BINDING"

DB_EXISTS="$(psql "$PSQL_ADMIN_CONN" -qtAX -c "select 1 from pg_database where datname = '$DB_NAME';" || true)"
if [ "$DB_EXISTS" != "1" ]; then
  psql "$PSQL_ADMIN_CONN" -v ON_ERROR_STOP=1 -c "create database \"$DB_NAME\";"
fi

psql "$PSQL_CONN" -c "select current_database(), current_user;"

psql "$PSQL_CONN" -f "$(dirname "$0")/00_ddl.sql"
psql "$PSQL_CONN" -f "$(dirname "$0")/01_seed_auth.sql"
psql "$PSQL_CONN" -f "$(dirname "$0")/02_seed_media.sql"
psql "$PSQL_CONN" -f "$(dirname "$0")/03_seed_content_types.sql"
psql "$PSQL_CONN" -f "$(dirname "$0")/04_seed_dynamic_content.sql"
psql "$PSQL_CONN" -f "$(dirname "$0")/05_seed_permissions.sql"
psql "$PSQL_CONN" -f "$(dirname "$0")/99_reset_sequences.sql"

psql "$PSQL_CONN" -c "select 'users' as table, count(*) from users union all select 'roles', count(*) from roles union all select 'content_types', count(*) from content_types union all select 'fields', count(*) from fields union all select 'media', count(*) from media union all select 'ct_article', count(*) from ct_article union all select 'ct_product', count(*) from ct_product;"
