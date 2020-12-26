#!/bin/bash
set -e

echo "Starting elasticsearch and grafana in docker..."
docker-compose -f es-grafana-docker/docker-compose.yml up -d

printf "\n"

echo "Started services:"
docker ps --format "{{ .Names }}"
printf "\n"

start http://localhost:3000
