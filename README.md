# apiforge-headless-api
Headless CMS using Spring Boot

## Docker

Run the full stack (Postgres + all services in one container):
```bash
docker compose up --build
```

Gateway: `http://localhost:7080`

## Local (no Docker)

Requires a PostgreSQL instance reachable by the services (defaults: `localhost:5432`, db `devdb`, user `dev`, password `devpass`).

```bash
./db/run_all.sh     # creates/seeds the DB (drops/recreates tables)
./run-all.sh        # builds and runs all services
```

## Google OAuth2 (free setup)

1. Go to Google Cloud Console: https://console.cloud.google.com/
2. Create a new project (or select an existing one).
3. Configure OAuth consent screen (External is fine for local dev).
4. Create credentials: OAuth Client ID.
5. Add authorized redirect URIs:
   - `http://localhost:7081/api/auth/oauth2/callback/google`
   - `http://localhost:7080/api/auth/oauth2/callback/google`
6. Export env vars before starting services:

```bash
export GOOGLE_CLIENT_ID="your-client-id"
export GOOGLE_CLIENT_SECRET="your-client-secret"
```

Start OAuth2 login:
- `http://localhost:7080/api/auth/oauth2/google` (through gateway)
- or `http://localhost:7081/api/auth/oauth2/google` (direct to auth-service)
