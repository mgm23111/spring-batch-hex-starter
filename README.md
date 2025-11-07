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
- Idempotency via natural key (orderId).
- `@StepScope` reader to inject JobParameters.
- Skip/Retry configured with sane limits.
- Test sample with `JobLauncherTestUtils`.
