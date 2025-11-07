SHELL := /bin/bash

.PHONY: up down build test run

up:
	docker compose up -d

down:
	docker compose down -v

build:
	cd batch-app && ./mvnw -q -DskipTests package

test:
	cd batch-app && ./mvnw -q test

run:
	cd batch-app && ./mvnw -q spring-boot:run -Dspring-boot.run.arguments="--spring.batch.job.enabled=true file=sample/orders.csv ts=$$(date +%s)"
