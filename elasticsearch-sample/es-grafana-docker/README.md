# elasticsearch-grafana-docker

1. `docker-compose build`
2. `docker-compose up`
3. login to grafana (http://localhost:3000)
    - login: admin
    - password: admin
4. setup new data source if need it: `http://<elastic-container-ip>:9200` (http://elasticsearch:9200)
    - have to use elasticsearch container IP, as the hostname won't work
    - find IP with `docker inspect grafanaelasticsearch_elasticsearch_1 | grep IPAddress`

  

