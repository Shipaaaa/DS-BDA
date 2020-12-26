# Quick build and deploy manual

## Required tools

* Unix ex. Centos 7 or Mac OS
* git
* jdk 1.8 or later
* docker

## Login to grafana

- login: admin
- password: admin

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

#### Start elasticsearch and grafana in docker-compose

```shell script
docker-compose -f es-grafana-docker/docker-compose.yml up
```

#### Run grafana web ui

```shell script
start http://localhost:3000
```

#### Shutdown elasticsearch and grafana in docker-compose after your work

```shell script
docker-compose -f es-grafana-docker/docker-compose.yml down
```
