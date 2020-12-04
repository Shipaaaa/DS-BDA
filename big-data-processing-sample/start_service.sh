#!/bin/bash
set -e

line_count=10
logs_file_path=./testData/syslogs.txt

echo "Starting kafka in docker"
docker-compose -f kafka-docker/docker-compose.yml up -d

echo "Started services:"
docker ps --format "{{ .Names }}"

./gradlew build

./generate_syslog_data.sh $line_count > $logs_file_path

export KAFKA_BOOTSTRAP_SERVERS_IP="127.0.0.1:9092"

java -jar ./kafka-producer/build/libs/kafka-producer-1.0-SNAPSHOT.jar $logs_file_path &
java -jar ./ignite-persistence/build/libs/ignite-persistence-1.0-SNAPSHOT.jar &
java -jar ./ignite-compute/build/libs/ignite-compute-1.0-SNAPSHOT.jar &