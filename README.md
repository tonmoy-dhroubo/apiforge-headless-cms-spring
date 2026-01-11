# apiforge-headless-api
Headless CMS using Spring Boot

## Docker

Run the full stack (Postgres + all services in one container):
```bash
docker compose up --build
```

Gateway: `http://localhost:8080`

## Local (no Docker)

Requires a PostgreSQL instance reachable by the services (defaults: `localhost:5432`, db `devdb`, user `dev`, password `devpass`).

```bash
./db/run_all.sh     # creates/seeds the DB (drops/recreates tables)
./run-all.sh        # builds and runs all services
```
