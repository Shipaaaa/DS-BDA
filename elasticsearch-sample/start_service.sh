#!/bin/bash
set -e

echo "Starting elasticsearch and grafana in docker..."
docker-compose -f es-grafana-docker/docker-compose.yml up -d

printf "\n"

echo "Started services:"
docker ps --format "{{ .Names }}"
printf "\n"

echo "Building project..."
./gradlew build
printf "\n\n\n"

printf "\nRunning Twitter Streaming API..."
java -jar ./build/libs/elasticsearch-sample-1.0-SNAPSHOT.jar
