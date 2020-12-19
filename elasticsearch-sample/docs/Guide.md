# Quick build and deploy manual

## Required tools

* Unix ex. Centos 7 or Mac OS
* git
* jdk 1.8 or later
* docker

## Build

### Clone project

```shell script
git clone https://github.com/Shipaaaa/DS-BDA.git
```

### Run start script

```shell script
./start_service.sh
```

### Or start the service manually

#### Build project

```shell script
./gradlew build
```

#### Start elasticsearch and grafana in docker-compose

```shell script
docker-compose -f es-grafana-docker/docker-compose.yml up
```

#### Run Twitter Streaming API

```shell script
java -jar ./build/libs/elasticsearch-sample-1.0-SNAPSHOT.jar
```

#### Shutdown elasticsearch and grafana in docker-compose after your work

```shell script
docker-compose -f es-grafana-docker/docker-compose.yml down
```
