# Batch Orders (Spring Batch Hexagonal Starter)

Starter for a chunk-oriented Spring Batch job with hexagonal architecture + JPA + Postgres.

## Modules
- `batch-app`: Spring Boot app, job definition, ports/adapters, tests.
- `docker-compose.yml`: Postgres for JobRepository and domain tables.

## Run (dev)
```bash
# 1) start infra
docker compose up -d

# 2) build & test
cd batch-app
./mvnw -q -DskipTests=false test

# 3) run job (pass file path)
./mvnw -q spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.enabled=true file=sample/orders.csv ts=$(date +%s)"
```

## Architecture
```
batch-app/
  └─ src/main/java/com/acme/orders/batch
       ├─ app/ (boot, schedule, listeners)
       ├─ domain/ (model + ports)
       ├─ infra/ (adapters: reader/writer/repo)
       └─ jobs/ (job/step/processor config)
```

## Notes
Idempotencia mediante clave natural (orderId).
Reprocesar un registro con el mismo orderId no crea duplicados ni cambia el resultado; la clave natural garantiza un efecto único.

Reader con @StepScope para inyectar JobParameters.
El lector se crea por step y puede recibir parámetros del job (ej. file, fechas) en tiempo de ejecución.

Skip/Retry configurados con límites razonables.
Definimos qué errores se saltan (skip) y cuáles se reintentan (retry), con topes para evitar loops o fallas interminables.

Prueba de ejemplo con JobLauncherTestUtils.
Permite lanzar el job/step en tests y verificar que termine en COMPLETED, cuántos ítems leyó/escribió, etc.
